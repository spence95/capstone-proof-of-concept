package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.google.gson.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.hooblahstudios.games.json.ActionJsonTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

public class World {

    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 480;
    public static final float MENU_WIDTH = 392 / 2;
    public static final float MENU_HEIGHT = 127 / 2;
    public static final float Move_menu_width = 122 / 2;
    public static final float Move_menu_height = 127 / 2;
    public static final float Attack_menu_width = 150 / 2;
    public static final float Attack_menu_height = 127 / 2;
    public final float squareWidth = 15;
    public final float squareHeight = 30;
    //origin start points
    //(20, 40)
    //(20, 440)
    //(780, 40)
    //(780, 440)
    public ArrayList<Player> players;
    public ArrayList<Block> blocks;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bullet> bullets;
    boolean hasStarted = false;
    boolean isSetting = true;
    boolean readyToEndRound = true;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;
    public Player currentPlayer;
    public Dot dot;
    public ActionMenu actionMenu;
    public CollisionManager collisionManager;
    public ScreenController screenController;
    proofOfConcept game;

    int turn;
    int matchID;
    public ArrayList<Integer> turnIDs;

    ApiCall api;

    public World(proofOfConcept game, int matchID) {
        this.game = game;
        this.matchID = matchID;
        players = new ArrayList<Player>();
        blocks = new ArrayList<Block>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        actionMenu = new ActionMenu();
        screenController = new ScreenController(game);
        dot = new Dot(-1000, -1000);
        collisionManager = new CollisionManager(this);
        turn = 1;
        api = new ApiCall();
        //place blocks on arena
        placeBlocks();
        turnIDs = new ArrayList<Integer>();
    }
    //mocked out with specific placements for blocks (no pseudo-randomness)
    public void placeBlocks(){
        placeOutsideWalls();
        Block block = new Block(125, 75, 100, 5);
        blocks.add(block);
        block = new Block(78, 100, 5, 50);
        blocks.add(block);

        block = new Block(800-125, 75, 100, 5);
        blocks.add(block);
        block = new Block(800-77, 100, 5, 50);
        blocks.add(block);

        block = new Block(125, 480-75, 100, 5);
        blocks.add(block);
        block = new Block(78, 480 - 100, 5, 50);
        blocks.add(block);

        block = new Block(800-125, 480-75, 100, 5);
        blocks.add(block);
        block = new Block(800-77, 480-100, 5, 50);
        blocks.add(block);

        block = new Block(400, 240, 50, 50);
        blocks.add(block);
    }

    private void placeOutsideWalls(){
        Block block = new Block(400, 0, 800, 10);
        blocks.add(block);
        block = new Block(400, 480, 800, 10);
        blocks.add(block);
        block = new Block(0, 240, 10, WORLD_HEIGHT);
        blocks.add(block);
        block = new Block(WORLD_WIDTH, 240, 10, WORLD_HEIGHT);
        blocks.add(block);
    }

    public void addToTurnIDs(int id){
        turnIDs.add(id);
    }

    public void touched(float x, float y){
        //render actions menu
            //check if click in menu bounds
            if(actionMenu != null) {
                if (actionMenu.menuBounds.contains(x, y) && actionMenu.isShown) {
                    if(actionMenu.isReadyToSubmit){
                        submit();
                        return;
                    }
                    if(!currentPlayer.isDone) {
                        if (x > 439) {
                            moveClicked(lastTouchedX, lastTouchedY);
                        } else {
                            attackClicked(lastTouchedX, lastTouchedY);
                        }
                    }
                } else {
                    bringUpMenu(x, y);
                }
            }
    }

    private void bringUpMenu(float x, float y){
        //only bring up when player isn't moving already
        if(this.currentPlayer.velocity != null) {
            if (this.currentPlayer.velocity.x == 0 && this.currentPlayer.velocity.y == 0) {
                lastTouchedX = x;
                lastTouchedY = y;
                dot.position.x = x;
                dot.position.y = y;
                //if hidden
                if (this.actionMenu.state == 0 && this.actionMenu.position.y < 0) {
                    this.actionMenu.changeState(1);
                    this.actionMenu.isShown = true;
                }
            }
        }
    }

    public void moveClicked(float x, float y){
        hideDot();
        this.currentPlayer.addMove(x, y);
        this.actionMenu.changeState(2);
        this.actionMenu.isShown = false;
    }

    public void attackClicked(float x, float y) {
        Attack at = new Attack(x, y, 9);
        hideDot();
        this.currentPlayer.addAttack(at);
        this.currentPlayer.bullet.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
        this.actionMenu.changeState(2);
        this.actionMenu.isShown = false;
    }

    public void hideDot(){
        this.dot.position.x = 1000;
        this.dot.position.y = 1000;
    }

    public void start(int startingTurnId){
        turn = startingTurnId;
        currentPlayer = new Player(game.getPlayerID(), squareWidth, squareHeight, false);
        //currentPlayer.spawn(spawnX, spawnY);
        Player enemy1 = new Player(-1, squareWidth, squareHeight, true);
//        Player enemy2 = new Player(2, squareWidth, squareHeight, true);
//        Player enemy3 = new Player(3, squareWidth, squareHeight, true);
        players.add(currentPlayer);
        players.add(enemy1);
//        players.add(enemy2);
//        players.add(enemy3);
    }

    public void update(float deltaTime){
     //update collisions
     collisionManager.updateCollisions();

     //update dot
     if(dot != null){
         dot.update(deltaTime);
     }

     //update bullets
     for(int i = 0; i < players.size(); i++){
         players.get(i).bullet.update(deltaTime);
     }

     //update action menu
     if(actionMenu != null){
         actionMenu.update(deltaTime);
     }

     if(isSetting){
            this.currentPlayer.updateSetting(deltaTime);
            if(this.currentPlayer.isDone){
                actionMenu.makeReadyToSubmit();
            }
        }
        else{
         int doneCounter = 0;
         ArrayList<Integer> deadPlayerIds = new ArrayList<Integer>();
         //run all players at once
            for (int i = 0; i < this.players.size(); i++) {
                Player pl = players.get(i);
                if (pl.isDone) {
                    doneCounter++;
                }

                pl.updateRunning(deltaTime);
                //check if all players are done and start a new round
                if (doneCounter >= players.size()) {
                    if(readyToEndRound)
                        newRound(deltaTime);
                }

                //check for dead players and remove them
                if (pl.dead) {
                    deadPlayerIds.add(pl.id);
                    if(pl.id == currentPlayer.id){
                        //oh dang, u just got Rekt
                        getRekt();
                    }
                }
            }
         //safely remove dead players from list
         removeDeadPlayers(deadPlayerIds);
     }

        updateExplosions(deltaTime);
    }

    private void removeDeadPlayers(ArrayList<Integer> deadPlayerIds){
        for(int i = 0; i < deadPlayerIds.size(); i++){
            for(int p = 0; p < players.size(); p++){
                Player pl = players.get(p);
                if(pl.id == deadPlayerIds.get(i)){
                    players.remove(p);
                }
            }
        }
    }

    private void getRekt(){
        screenController.setGameOverScreen();
        turn = 0;
    }

    public void newRound(float deltaTime){
        turn++;
        for(int i = 0; i < players.size(); i++){
            players.get(i).forgetActions();
        }
        currentPlayer.setLastPosition(currentPlayer.position.x, currentPlayer.position.y);
        isSetting = true;
        actionMenu.isReadyToSubmit = false;

    }

    public void updateExplosions(float deltaTime){
        for(int i = 0; i < explosions.size(); i++){
            readyToEndRound = false;
            Explosion exp = explosions.get(i);
            if(!exp.getIsDone()) {
                exp.update(deltaTime);
            } else{
                explosions.remove(i);
            }
        }

        if(explosions.size() < 1){
            readyToEndRound = true;
        }
    }

    public void bulletShoot(){

    }

    public void submit(){
        hideDot();
        this.actionMenu.changeState(2);
        generateTurnJson();
        this.currentPlayer.resetActions();
        this.isSetting = false;
        //reset current player
        currentPlayer.position.x = currentPlayer.xLast;
        currentPlayer.position.y = currentPlayer.yLast;
        getEnemyActions();
        explosions.clear();
    }

    public void generateTurnJson(){
        //TODO: build actions json
        String actionsJson = "{\"objects\":[";
        for(int i = 0; i < currentPlayer.actions.size(); i++) {
            Action ac = currentPlayer.actions.get(i);
            int actiontype = 1;
            if(ac instanceof Spawn){
                actiontype = 0;
            } else if(ac instanceof Attack) {
                actiontype = 2;
            }
            int originx = 0;
            int originy = 0;
            if(i == 0){
                originx = (int)(100 * round(currentPlayer.xLast, 2));
                originy = (int)(100 * round(currentPlayer.yLast, 2));
            }

            if(ac instanceof Move) {
                originx = (int) (100 * round(ac.x, 2));
                originy = (int) (100 * round(ac.y, 2));
            }

            int targetx = (int) (100 * round(ac.x, 2));
            int targety = (int) (100 * round(ac.y, 2));

            int turnId = turnIDs.get(turnIDs.size() - 1);

            String action = "{ \"actionnumber\":" + i + "," +
                    "\"actiontype\":" + actiontype + "," +
                    "\"originx\":" + originx + "," +
                    "\"originy\":" + originy + "," +
                    "\"targetx\":" + targetx + "," +
                    "\"targety\":" + targety + "," +
                    "\"timetaken\":0" + "," +
                    "\"turn\": \"/api/v1/turn/" + turnId + "\"}";

            actionsJson += action;
        }
        actionsJson += "]}";
        //post turn, needs match, player, and turnnumber

        //TODO: post actions to turn id in world
        String patchResults = api.httpPostPutOrPatch("http://45.33.62.187/api/v1/action/?format=json", actionsJson, 0, true, false);
        //TODO: throw up loading sign
        //TODO: if post unsuccessful try again else retrieve enemy actions
        if(patchResults.length() > 1) {
            String url = "http://45.33.62.187/api/v1/turn/?match=" + matchID + "&turnnumber=" + turn + "&format=json";
            String turnResults = api.httpGet(url, 0);
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
                String actionResults = api.httpGet(getActionsUrl, 0);
                JSONObject actionJson = new JSONObject(actionResults);
                JSONArray actionArray = actionJson.getJSONArray("objects");
                for(int a = 0; a < actionArray.length(); a++) {

                    String[] tokens = tempTurnIds.get(i).split("/");
                    int lastPlace = tokens.length - 1;
                    int playerID = Integer.parseInt(tokens[lastPlace]);

                    players.get(index).id = playerID;
                    JSONObject actionsObj = actionArray.getJSONObject(a);
                    ArrayList<Action> actions = new ArrayList<Action>();

                    float originx = actionsObj.getInt("originx")/100;
                    float originy = actionsObj.getInt("originy")/100;
                    float targetx = actionsObj.getInt("targetx")/100;
                    float targety = actionsObj.getInt("targety")/100;
                    int actiontype = actionsObj.getInt("actiontype");
                    if(actiontype == 0){
                        Spawn sp = new Spawn(originx, originy);
                        actions.add(sp);
                    } else if(actiontype == 1){
                        Move mv = new Move(targetx, targety, 0);
                        actions.add(mv);
                    } else if(actiontype == 2){
                        Attack at = new Attack(targetx, targety, 0);
                        actions.add(at);
                    }
                    players.get(index).actions = actions;
                }
                index++;
            }
        }
        //TODO: upon successful retrieval create turn id for next time and store it in world

        String turnPostJson = "{" +
                "\"match\":\"/api/v1/match/" + matchID + "/\"," +
                "\"player\":\"/api/v1/player/" + game.getPlayerID() + "/\"," +
                "\"turnnumber\":" + turn + "}";
        String postResults = api.httpPostPutOrPatch("http://45.33.62.187/api/v1/turn/?format=json", turnPostJson, 0, false, false);
        JSONObject turnIdJsonObj = new JSONObject(postResults);
        int turnId = turnIdJsonObj.getInt("id");
        addToTurnIDs(turnId);
    }

    public void getEnemyActions(){
        //randomize other squares movements
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).isEnemy){
                //reach out to server to get moves
                //mocked data
                Random rand = new Random();
                int x = rand.nextInt((370 - 10) + 1) + 10;
                int y = rand.nextInt((220 - 10) + 1) + 10;

                String action1 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";


                x = rand.nextInt((370 - 10) + 1) + 10;
                y = rand.nextInt((220 - 10) + 1) + 10;

                String action2 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";

                String json = "{\"ajst\":[" + action1 + "," + action2 + " ]}";

                //parse the json
                Gson gson = new Gson();
                EnemyJsonTemplate ejst = gson.fromJson(json, EnemyJsonTemplate.class);
//                this.players.get(i).addMove(ejst.ajst[0].destX, ejst.ajst[0].destY);
//                this.players.get(i).addMove(ejst.ajst[1].destX, ejst.ajst[1].destY);
            }
        }
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}