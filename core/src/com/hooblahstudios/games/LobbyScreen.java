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
    String postResults;

    public LobbyScreen(proofOfConcept game){
        this.game = game;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        api = new ApiCall();
        //call out to server once
        postResults = putReady();
    }

    public String putReady(){
        //url is: http://45.33.62.187/api/v1/matchmaking/(id)/?format=json
        String URL = "http://45.33.62.187/api/v1/matchmaking/?player=" + game.getPlayerID() + "&waiting=True&format=json";
        String Body = "{" +
                "\"player\": \"/api/v1/player/" + game.getPlayerID() + "/\"," +
                "\"waiting\": \"True\"}";
        return api.httpPostOrPatch(URL, Body, 0, false);
        //return "SUCCESSFUL POST";
    }

    public String getMatchmaking(){
        
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
        //call out every x seconds to see if match made
        if(postResults != "SUCCESSFUL POST"){
            postResults = postReady();
            return;
        }
        //server will have to tell us what the start origin is
        String URL = "http://45.33.62.187/api/v1/matchmaking/?player=" + game.getPlayerID()
                + "&waiting=TRUE&format=json";
        String matchUpdate = api.httpGet(URL, 0);
        //results look like this:


        JSONObject json = new JSONObject(matchUpdate);
        JSONArray matchArray = json.getJSONArray("objects");
        JSONObject matchObj = matchArray.getJSONObject(0);
        System.out.println(matchObj.toString());
        String match = matchObj.getString("match");
        if (match.length() >= 2)         // if word is at least two characters long
        {
            int matchID = (int)match.length() - 2;    // access the second from last character
            if(match != "null"){
                game.setScreen(new GameScreen(game, matchID));
            } else {
                update(delta);
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
