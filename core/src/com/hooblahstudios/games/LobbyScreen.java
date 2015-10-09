package com.hooblahstudios.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by spence95 on 10/8/2015.
 */
public class LobbyScreen extends ScreenAdapter {
    proofOfConcept game;
    OrthographicCamera guiCam;
    public LobbyScreen(proofOfConcept game){
        this.game = game;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderer.render();

        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
    }

    public void update(float delta){

    }

    public void render (float delta) {
        update(delta);
        draw();
    }
}
