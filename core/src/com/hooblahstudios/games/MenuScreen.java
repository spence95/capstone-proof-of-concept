package com.hooblahstudios.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import com.badlogicgames.superjumper.World.WorldListener;

/**
 * Created by wlindelo on 10/1/2015.
 */
public class MenuScreen extends ScreenAdapter {

    proofOfConcept game;
    Vector3 touchPoint;

    Menu menu;
    OrthographicCamera guiCam;
    MenuRenderer renderer;

    Rectangle submitBounds;

    GlyphLayout glyphLayout = new GlyphLayout();

    public MenuScreen(proofOfConcept game){
        this.game = game;
        guiCam = new OrthographicCamera(800, 480);
        guiCam.position.set(800 / 2, 480 / 2, 0);
        touchPoint = new Vector3();

        menu = new Menu(game);
        renderer = new MenuRenderer(game.batcher, menu);
        //menu.start();
        menu.splash();
    }

    public void update(float deltaTime){
        updateRunning(deltaTime);
    }

    private void updateReady(){

    }

    private void updateRunning(float deltaTime){
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            menu.touched(touchPoint.x, touchPoint.y);
        }

        menu.update(deltaTime);
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
