package com.hooblahstudios.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by spence95 on 10/6/2015.
 */
public class GameOverScreen extends ScreenAdapter {
    proofOfConcept game;
    Vector3 touchPoint;

    OrthographicCamera guiCam;
    GameOverRenderer renderer;

    public GameOverScreen(proofOfConcept game){
        this.game = game;
        renderer = new GameOverRenderer(game.batcher);
        guiCam = new OrthographicCamera(800, 480);

        guiCam.position.set(800 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
    }

    public void update(float deltaTime){
        updateRunning(deltaTime);
    }

    private void updateRunning(float deltaTime){
        if (Gdx.input.justTouched()) {
           game.setScreen(new MenuScreen(game));
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.enableBlending();
    }

    public void render (float delta) {
        update(delta);
        draw();
    }
}
