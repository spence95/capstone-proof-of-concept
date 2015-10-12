package com.hooblahstudios.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    public LobbyScreen(proofOfConcept game){
        this.game = game;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        api = new ApiCall();
        //call out to server once
        putResults = putReady();
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

        String[] matchmakingResults = getMatchmaking(false);
        String match = matchmakingResults[0];
        String waiting = matchmakingResults[1];
        System.out.println(match);
        update(delta);
        if(waiting == "false") {
            if (match != "null") {
                //get match id from string
                String[] tokens = match.split("/");
                int lastPlace = tokens.length - 1;
                int matchID = Integer.parseInt(tokens[lastPlace]);
                game.setScreen(new GameScreen(game, matchID));
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(Assets.loadingMenuRegion, guiCam.position.x - 800 / 2, guiCam.position.y - 480 / 2, 800,
                480);
        batch.end();
    }

    public void render (float delta) {
        update(delta);
        draw();
    }
}
