package com.hooblahstudios.games;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by spence95 on 10/6/2015.
 */
public class GameOverRenderer {
    SpriteBatch batch;
    OrthographicCamera cam;


    public GameOverRenderer(SpriteBatch batch){
        this.batch = batch;
        this.cam = new OrthographicCamera(World.WORLD_WIDTH, World.WORLD_HEIGHT);
        this.cam.position.set(World.WORLD_WIDTH / 2, World.WORLD_HEIGHT / 2, 0);
    }

    public void renderLoss(){
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.rektBackgroundRegion,cam.position.x - World.WORLD_WIDTH / 2, cam.position.y - World.WORLD_HEIGHT / 2, World.WORLD_WIDTH,
                World.WORLD_HEIGHT);
        batch.end();

    }

    public void renderWin(){
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.winScreenBackgroundRegion,cam.position.x - World.WORLD_WIDTH / 2, cam.position.y - World.WORLD_HEIGHT / 2, World.WORLD_WIDTH,
                World.WORLD_HEIGHT);
        batch.end();
    }
}
