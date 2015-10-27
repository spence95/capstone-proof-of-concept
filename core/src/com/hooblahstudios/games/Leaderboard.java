package com.hooblahstudios.games;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KC on 10/26/2015.
 */
public class Leaderboard {

    public HashMap<Integer, HashMap<String, String>> board;
    public ArrayList<Integer> friendIDs;
    public ArrayList<Integer> topPlayerIDs;
    public HashMap<Integer, String> idUsernameMapping;

    /*
    playerID is the player initializing the request
    friendCount is how many friend's infos to get
    topCount is how many top players' infos to get
     */
    public Leaderboard(int playerID, int friendCount, int topCount)
    {
        board = new HashMap<Integer, HashMap<String, String>>();
        friendIDs = new ArrayList<Integer>();
        topPlayerIDs = new ArrayList<Integer>();
        idUsernameMapping = new HashMap<Integer, String>();

        generateFriendIDs(friendCount, playerID);
        //generateTopPlayerIDs(topCount);//I'll implement this later

        generateLeaderboard(playerID);

    }

    void generateLeaderboard(int playerID)//this is going to assume that friendIDs and topPlayerIDs are are filled out
    {
        ArrayList<Integer> allIDs = new ArrayList<Integer>();
        allIDs.add(playerID);
        allIDs.addAll(friendIDs);
        allIDs.addAll(topPlayerIDs);//now it has all the dank

        generateUsernames(allIDs);



        ApiCall apiCall = new ApiCall();

        for (Integer id : allIDs)//for each id in the big list
        {
            String url = "http://45.33.62.187/api/v1/playermatch/?player=" + id + "&format=json";
            String playerMatchResults = apiCall.httpGet(url, 0);

            if (playerMatchResults.equalsIgnoreCase("FAILED") || playerMatchResults.equalsIgnoreCase("CANCELLED"))
            {
                //menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
            }
            else if (playerMatchResults.equalsIgnoreCase("EMPTY"))//they ahvent playe da match yet
            {
                board.put(id, new HashMap<String, String>());
                board.get(id).put("USERNAME", idUsernameMapping.get(id));
                board.get(id).put("WINS", "" + 0);
                if (friendIDs.contains(id))
                {
                    board.get(id).put("FRIENDS", "TRUE");//not ideal but whatever. really its a collection of objects but java typing is ghey
                }
                else {
                    board.get(id).put("FRIENDS", "FALSE");
                }
            }
            else {
                JSONObject json = new JSONObject(playerMatchResults);
                JSONArray playerMatchArray = json.getJSONArray("objects");
                board.put(id, new HashMap<String, String>());
                int wins = 0;
                for (int i = 0; i < playerMatchArray.length(); i++)
                {
                    JSONObject playerMatchObj = playerMatchArray.getJSONObject(i);
                    int outcome = playerMatchObj.getInt("outcome");

                    if (outcome == 2)
                    {
                        wins++;
                    }
                }
                board.get(id).put("USERNAME", idUsernameMapping.get(id));
                board.get(id).put("WINS", "" + wins);//the "" + wins casts the int to a string
                if (friendIDs.contains(id))
                {
                    board.get(id).put("FRIENDS", "TRUE");//not ideal but whatever. really its a collection of objects but java typing is ghey
                }
                else {
                    board.get(id).put("FRIENDS", "FALSE");
                }
            }
        }
    }

    void generateUsernames(ArrayList<Integer> allIDs)
    {

        ApiCall apiCall = new ApiCall();

        for (Integer i : allIDs)
        {
            String url = "http://45.33.62.187/api/v1/player/" + i + "/?format=json";
            String getPlayer = apiCall.httpGet(url, 0);

            if (getPlayer.equalsIgnoreCase("FAILED") || getPlayer.equalsIgnoreCase("CANCELLED") || getPlayer.equalsIgnoreCase("EMPTY"))
            {
                //menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
            }
            else {
                JSONObject json = new JSONObject(getPlayer);
                String username = json.getString("username");
                idUsernameMapping.put(i, username);
            }
        }
    }

    void generateFriendIDs(int friendCount, int playerID)//generates a list of the top IDs for his friends of size passed
    {

        ApiCall apiCall = new ApiCall();

        String url = "http://45.33.62.187/api/v1/friend/?player=" + playerID + "&format=json";
        String friendResults = apiCall.httpGet(url, 0);

        if (friendResults.equalsIgnoreCase("FAILED") || friendResults.equalsIgnoreCase("CANCELLED") || friendResults.equalsIgnoreCase("EMPTY"))
        {
            //menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
        }
        else {
            JSONObject json = new JSONObject(friendResults);
            JSONArray friendArray = json.getJSONArray("objects");
            for (int i = 0; i < Math.min(friendArray.length(), friendCount); i++)//I use min here so it will get the minimum of friends to get or friends total, so it doesnt overdraw
            {
                JSONObject friendObj = friendArray.getJSONObject(i);
                String friendURL = friendObj.getString("friend");
                String[] tokens = friendURL.split("/");
                int lastPlace = tokens.length - 1;
                int friendID = Integer.parseInt(tokens[lastPlace]);
                friendIDs.add(friendID);
            }//should now have looped through and added all the friends

        }
    }

    /*void generateTopPlayerIDs(int topCount) //generates a list of the top players of size passed
    {

    }*/

}
