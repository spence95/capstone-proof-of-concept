package com.werbenjagermanjensenstudios.charitychamps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by spence95 on 10/24/2015.
 */
public class RetrievingScreen extends ScreenAdapter {
    proofOfConcept game;
    OrthographicCamera guiCam;
    SpriteBatch batch;
    ApiCall api;
    String putResults;
    World world;
    ScreenController sc;
    WorldRenderer renderer;

    public RetrievingScreen(proofOfConcept game, World world, ScreenController sc, WorldRenderer renderer){
        this.game = game;
        this.world = world;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        api = new ApiCall();
        this.sc = sc;
        this.renderer = renderer;
    }

    public void draw(float delta){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(delta);

        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
    }
    public void update(float delta) {
        //boolean isReady = world.isReadyToRun();
//        if(isReady){
//            world.runPlayers();
//            sc.resetGameScreen();
//        }
    }


    public void render(float delta){
        update(delta);
        draw(delta);
    }

    }
