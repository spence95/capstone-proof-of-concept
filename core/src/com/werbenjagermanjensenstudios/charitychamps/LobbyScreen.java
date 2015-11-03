package com.werbenjagermanjensenstudios.charitychamps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by spence95 on 10/8/2015.
 */
public class LobbyScreen extends ScreenAdapter {
    proofOfConcept game;
    OrthographicCamera guiCam;
    SpriteBatch batch;
    ApiCall api;
    String putResults;
    World world;
    TextField loadingText;
    int loadingDotTimer;
    boolean inGame;
    boolean nextRound;
    WorldRenderer worldRenderer;
    GameScreen gs;


    public LobbyScreen(proofOfConcept game){
        this.game = game;
        this.inGame = false;
        this.nextRound = false;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        api = new ApiCall();
        //call out to server once
        putResults = putReady();

        loadingDotTimer = 0;

        this.loadingText = new TextField(("LOADING"), Assets.tfsTrans100);
        loadingText.setPosition(250, 190);
        loadingText.setWidth(700);
        loadingText.setHeight(150);
        //loadingText.setAlignment(Align.center);
        loadingText.setFocusTraversal(false);
        loadingText.setDisabled(true);

    }

    public LobbyScreen(proofOfConcept game, World world) {
        this.game = game;
        this.world = world;
        this.gs = new GameScreen(game, world);
        this.nextRound = false;
        this.inGame = true;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        api = new ApiCall();
        worldRenderer = new WorldRenderer(batch, world);

        loadingDotTimer = 0;

        this.loadingText = new TextField(("LOADING"), Assets.tfsTrans100);
        loadingText.setPosition(250, 190);
        loadingText.setWidth(700);
        loadingText.setHeight(150);
        //loadingText.setAlignment(Align.center);
        loadingText.setFocusTraversal(false);
        loadingText.setDisabled(true);
    }

        public String putReady(){
        //url is: http://45.33.62.187/api/v1/matchmaking/(id)/?format=json
        String[] matchmakingId = getMatchmaking(true);
        String URL = "http://45.33.62.187/api/v1/matchmaking/" + matchmakingId[0] + "/?player=" + game.getPlayerID() + "&format=json";
        String Body = "{" +
                "\"player\": \"/api/v1/player/" + game.getPlayerID() + "/\"," +
                "\"waiting\": \"TRUE\"}";
        return api.httpPostPutOrPatch(URL, Body, 0, false, true);
    }

    public String[] getMatchmaking(boolean getMatchmakingId){
        String[] results = new String[2];
        String URL = "http://45.33.62.187/api/v1/matchmaking/?player=" + game.getPlayerID()
                + "&format=json";
        String matchmakingStr = api.httpGet(URL, 0);
        JSONObject matchmakingJson = new JSONObject(matchmakingStr);
        JSONArray matchmakingArray = matchmakingJson.getJSONArray("objects");
        JSONObject matchMakingObj = matchmakingArray.getJSONObject(0);
        results[0] = matchMakingObj.getString("match");
        if(getMatchmakingId){
            results[0] = matchMakingObj.getString("id");
        }
        results[1] = matchMakingObj.getString("waiting");
        return results;
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        render(game.batcher);


        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
    }

    public void update(float delta){
        draw();
        if(inGame == false) {
            String[] matchmakingResults = getMatchmaking(false);
            String match = matchmakingResults[0];
            String waiting = matchmakingResults[1];
                if (waiting == "false") {
                    if (match != "null") {
                        //get match id from string
                        String[] tokens = match.split("/");
                        int lastPlace = tokens.length - 1;
                        int matchID = Integer.parseInt(tokens[lastPlace]);
                        world = new World(game, matchID);
                        world.start();

                        //post an entry into player-match. you can find a lot of the player stuff in here

                        String URL = "http://45.33.62.187/api/v1/playermatch/?format=json";//I think that this should actually be ADDED at the start of the match, and just updated here
                        String Body = "" +
                                "{" +
                                "   \"character\": \"/api/v1/character/" + 1 + "/\"," + //so this is a dumb placeholder while we only have 1 character
                                "   \"charity\": \"/api/v1/charity/" + game.getCharityID() + "/\"," +
                                "   \"match\": \"/api/v1/match/" + matchID + "/\"," +
                                "   \"outcome\": \"" + 1 + "\"," + //we're assuming that 0 is a loss, 1 is a disconnect/unfinished (so default), and 2 is a victory
                                "   \"player\": \"/api/v1/player/" + game.getPlayerID() + "/\"," +//add the current player (each client adds one of these)
                                "   \"skin\": \"/api/v1/skin/" + 1 + "/\"," + //dumb placeholder until we have skin implementation
                                "   \"weapon\": \"/api/v1/weapon/" + 1 + "/\"" + //dumb placeholder until we have weapon implementation
                                "}";

                        System.out.println("going to attemppt to ost with body " + URL + " " + Body);
                        String results = api.httpPostPutOrPatch(URL, Body, 0, false, false);//makes the first call to post playermatch to table. this is updated at gameover
                        System.out.println("Posted the playermatch" + results);

                        JSONObject jsonCharity = new JSONObject(results);
                        int playermatchID = jsonCharity.getInt("id");
                        game.setPlayerMatchID(playermatchID);

                        //get enemy ids
                        String getEnemyIDsURL = "http://45.33.62.187/api/v1/turn/?match=" + matchID + "&format=json";
                        String getEnemyIDs = api.httpGet(getEnemyIDsURL, 0);
                        JSONObject enemyIDsJson = new JSONObject(getEnemyIDs);
                        JSONArray enemyIDsArray = enemyIDsJson.getJSONArray("objects");
                        int currentPlayerTurnID = -1;
                        for (int i = 0; i < enemyIDsArray.length(); i++) {
                            JSONObject playerArr = enemyIDsArray.getJSONObject(i);
                            String playerUrl = playerArr.getString("player");

                            tokens = playerUrl.split("/");
                            lastPlace = tokens.length - 1;
                            int playerID = Integer.parseInt(tokens[lastPlace]);

                            int turnId = playerArr.getInt("id");
                            if (playerID != game.getPlayerID()) {
                                Player pl = world.getNextEnemyPlayerWithoutID();
                                pl.id = playerID;
                                pl.currentTurnId = turnId;
                                //world.getNextEnemyPlayerWithoutID().setCurrentTurnId(turnId);
                            } else {
                                currentPlayerTurnID = turnId;
                                world.currentPlayer.currentTurnId = turnId;
                            }
                            //get spawn actions
                            String getActionURL = "http://45.33.62.187/api/v1/action/?turn=" + turnId + "&actionnumber=0&format=json";
                            String getActionStr = api.httpGet(getActionURL, 0);
                            JSONObject actionJson = new JSONObject(getActionStr);
                            JSONArray actionArray = actionJson.getJSONArray("objects");
                            JSONObject actionObj = actionArray.getJSONObject(0);
                            float xSpawn = actionObj.getInt("originx") / 100;
                            float ySpawn = actionObj.getInt("originy") / 100;
                            Spawn sp = new Spawn(xSpawn, ySpawn);
                            world.getPlayerById(playerID).actions.add(sp);
                        }


                        world.addToTurnIDs(currentPlayerTurnID);
                        world.spawnPlayers();
                        game.setScreen(new GameScreen(game, world));
                    }
            }
        } else {
            isReadyToRun();
            if(nextRound == true){
                    world.runPlayers();
                    world.screenController.resetGameScreen();
            }
        }
    }

    public void render(SpriteBatch batch){
        if(!inGame) {
            batch.begin();
            batch.draw(Assets.menuSplashBlankRegion, guiCam.position.x - 800 / 2, guiCam.position.y - 480 / 2, 800,
                    480);
            batch.end();
        } else{
            gs.renderer.render();
        }
        batch.begin();
        batch.enableBlending();

        loadingText.draw(batch, 1);

        if (loadingDotTimer == 15)
        {
            loadingText.setText("LOADING.");
        }
        else if (loadingDotTimer == 30)
        {
            loadingText.setText("LOADING..");
        }
        else if (loadingDotTimer == 45)
        {
            loadingText.setText("LOADING...");
        }
        else if(loadingDotTimer == 75)
        {
            loadingText.setText("LOADING");
            loadingDotTimer = 0;
        }
        loadingDotTimer++;

        batch.end();
    }

    public void render (float delta) {
        update(delta);
        draw();
    }

    public void isReadyToRun(){
        String putReceivedURL = "http://45.33.62.187/api/v1/turn/" + world.currentPlayer.currentTurnId + "/?format=json";
        String json =  "{" +
                "\"received\":\"true\"" +
                "}" ;
        String receivedResults = api.httpPostPutOrPatch(putReceivedURL, json, 0, false, true);


        int totalReceived = 0;
        for(int i = 0; i < world.players.size(); i++){
            Player pl = world.players.get(i);
            String turnUrl = "http://45.33.62.187/api/v1/turn/" + pl.currentTurnId + "/?format=json";
            String turnResults = api.httpGet(turnUrl, 0);
            JSONObject turn = new JSONObject(turnResults);
            boolean check = turn.getBoolean("received");
            if (check) {
                totalReceived++;
            }
        }



        if(totalReceived == world.players.size()){
            this.nextRound = true;
        }
    }

}
