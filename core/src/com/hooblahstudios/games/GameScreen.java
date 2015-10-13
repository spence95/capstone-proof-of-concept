package com.hooblahstudios.games;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hooblahstudios.games.touchCoordinate;
//import com.badlogicgames.superjumper.World.WorldListener;

/**
 * Created by spence95 on 9/4/2015.
 */
public class GameScreen extends ScreenAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    private Viewport vp;
    proofOfConcept game;

    int state;
    Vector3 touchPoint;

    World world;
    OrthographicCamera guiCam;
    //    WorldListener worldListener;
    WorldRenderer renderer;

    Rectangle submitBounds;

    GlyphLayout glyphLayout = new GlyphLayout();

    public GameScreen(proofOfConcept game, World world){
        this.game = game;
        this.world = world;
        state = GAME_READY;
        guiCam = new OrthographicCamera(800, 480);
        vp = new StretchViewport(480, 800, guiCam);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        touchPoint = new Vector3();

        //TODO: Move creation of world to lobbyscreen so we can instantiate players with spawn move
        //TODO: accept world as parameter here
        renderer = new WorldRenderer(game.batcher, world);
    }

    public void update(float deltaTime){
        updateRunning(deltaTime);
    }

    private void updateReady(){

    }

    private void updateRunning(float deltaTime){
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            world.touched(touchPoint.x, touchPoint.y);
        }

        world.update(deltaTime);
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.enableBlending();
//        game.batcher.begin();
//        switch (state) {
//            case GAME_READY:
//                presentReady();
//                break;
//            case GAME_RUNNING:
//                presentRunning();
//                break;
//            case GAME_PAUSED:
//                presentPaused();
//                break;
//            case GAME_LEVEL_END:
//                presentLevelEnd();
//                break;
//            case GAME_OVER:
//                presentGameOver();
//                break;
//        }
//
//        game.batcher.end();
    }

    public void render (float delta) {
        update(delta);
        draw();
    }

}
