package com.werbenjagermanjensenstudios.charitychamps;

import com.werbenjagermanjensenstudios.charitychamps.actions.Action;
import com.werbenjagermanjensenstudios.charitychamps.actions.Attack;
import com.werbenjagermanjensenstudios.charitychamps.actions.Move;
import com.werbenjagermanjensenstudios.charitychamps.actions.Spawn;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by spence95 on 10/26/2015.
 */
public class WorldJSONHandler {

    public static void postNewTurn(World world){
        world.turnNumber++;
        String turnPostJson = "{" +
                "\"match\":\"/api/v1/match/" + world.matchID + "/\"," +
                "\"player\":\"/api/v1/player/" + world.game.getPlayerID() + "/\"," +
                "\"turnnumber\":" + world.turnNumber + "}";
        String postResults = world.api.httpPostPutOrPatch("http://45.33.62.187/api/v1/turn/?format=json", turnPostJson, 0, false, false);
        JSONObject turnIdJsonObj = new JSONObject(postResults);
        int turnId = turnIdJsonObj.getInt("id");
        world.addToTurnIDs(turnId);
        world.currentPlayer.currentTurnId = turnId;
    }

    public static void runPlayers(World world){
        String url = "http://45.33.62.187/api/v1/turn/?match=" + world.matchID + "&turnnumber=" + world.turnNumber + "&format=json";
        String turnResults = world.api.httpGet(url, 0);
        JSONObject turnJson = new JSONObject(turnResults);
        JSONArray turnArray = turnJson.getJSONArray("objects");
        JSONObject turnObj = turnArray.getJSONObject(0);
//            int[] tempTurnIds = new int[4];
        HashMap<Integer, String> tempTurnIds = new HashMap<Integer, String>();
        tempTurnIds.put(turnObj.getInt("id"), turnObj.getString("player"));
        turnObj = turnArray.getJSONObject(1);
        tempTurnIds.put(turnObj.getInt("id"), turnObj.getString("player"));
//            turnObj = turnArray.getJSONObject(2);
//            tempTurnIds[2] = turnObj.getInt("id");
//            turnObj = turnArray.getJSONObject(3);
//            tempTurnIds[3] = turnObj.getInt("id");
        int index = 0;
        for (int i : tempTurnIds.keySet()) {
            String getActionsUrl = "http://45.33.62.187/api/v1/action/?turn=" + i + "&format=json";
            String actionResults = "";
            world.api.httpGetAndRunPlayers(getActionsUrl, tempTurnIds.get(i), index);
            index++;
        }
        postNewTurn(world);
        world.isSetting = false;
    }

    public static void SetPlayersForRunning(String actionResults, String playerUrl, int index, World world){
        JSONObject actionJson = new JSONObject(actionResults);
        JSONArray actionArray = actionJson.getJSONArray("objects");
        ArrayList<Action> actions = new ArrayList<Action>();

        String[] tokens = playerUrl.split("/");
        int lastPlace = tokens.length - 1;
        int playerID = Integer.parseInt(tokens[lastPlace]);

        Player pl = world.players.get(index);


        for(int a = 0; a < actionArray.length(); a++) {


            //if first time through
            if(world.turnNumber == 0) {
                if (playerID != world.game.getPlayerID()) {
                    pl.id = playerID;
                } else {
                    pl.id = world.game.getPlayerID();
                }
            }
            //else
            else {
                pl = world.getPlayerById(playerID);
            }



            JSONObject actionsObj = actionArray.getJSONObject(a);

            float originx = (float)actionsObj.getInt("originx")/100;
            float originy = (float)actionsObj.getInt("originy")/100;
            float targetx = (float)actionsObj.getInt("targetx")/100;
            float targety = (float)actionsObj.getInt("targety")/100;
            int actiontype = actionsObj.getInt("actiontype");
            int actionSeqNum = actionsObj.getInt("actionnumber");
            float timetaken = (float)actionsObj.getInt("timetaken")/100000;
            System.out.println("Receiving Time taken: " + actionsObj.getInt("timetaken") + " to " + timetaken);
            if(actiontype == 0){
                Spawn sp = new Spawn(originx, originy);
                sp.setSequenceNum(actionSeqNum);
                actions.add(sp);
                pl.spawn(originx, originy);
            } else if(actiontype == 1){
                Move mv = new Move(targetx, targety, timetaken);
                mv.setSequenceNum(actionSeqNum);
                actions.add(mv);
            } else if(actiontype == 2){
                Attack at = new Attack(targetx, targety, timetaken);
                at.setSequenceNum(actionSeqNum);
                actions.add(at);
            }
        }
        pl.actions = actions;
        pl.orderActions();
    }}
