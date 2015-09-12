package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
public class WorldRenderer {
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;

    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(this.world.WORLD_WIDTH, this.world.WORLD_HEIGHT);
        this.cam.position.set(this.world.WORLD_WIDTH / 2, this.world.WORLD_HEIGHT / 2, 0);
        this.batch = batch;
    }

    public void render () {
        renderBackground();
        renderObjects();
    }

    public void renderBackground () {
        batch.disableBlending();
        batch.begin();
//        batch.draw(Assets.backgroundRegion, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
//                FRUSTUM_HEIGHT);
        batch.draw(Assets.backgroundRegion, cam.position.x - this.world.WORLD_WIDTH / 2, cam.position.y - this.world.WORLD_HEIGHT / 2, this.world.WORLD_WIDTH,
                this.world.WORLD_HEIGHT);
        batch.end();
    }

    public void renderObjects () {
        renderSquares();
    }

    private void renderSquares(){
        batch.begin();
        for(int i = 0; i < world.squares.size(); i++){
            batch.draw(Assets.squareRegion, world.squares.get(i).position.x, world.squares.get(i).position.y, 10, 10);
        }
        batch.end();
    }
}
