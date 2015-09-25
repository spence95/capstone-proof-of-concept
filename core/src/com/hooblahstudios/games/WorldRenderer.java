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
        //renderSquares();
        renderPlayers();
        renderActionButtons();
        renderDot();
    }

    private void renderSquares(){
        batch.begin();
        for(int i = 0; i < world.squares.size(); i++){
            batch.draw(Assets.squareRegion, world.squares.get(i).position.x, world.squares.get(i).position.y, 10, 10);
        }
        batch.end();
    }

    private void renderPlayers(){
        batch.begin();
        for(int i = 0; i < world.squares.size(); i++){
            square sq = world.squares.get(i);
            TextureRegion keyFrame = Assets.playerStill;

            if(sq.isMoving){
                keyFrame = Assets.playerWalking.getKeyFrame(sq.stateTime, Animation.ANIMATION_LOOPING);
            }

            batch.draw(keyFrame, sq.position.x, sq.position.y, world.squareWidth, world.squareHeight);
            batch.draw(Assets.bulletRegion, sq.bullet.position.x, sq.bullet.position.y, sq.bullet.position.x, sq.bullet.position.y,sq.bullet.bounds.width, sq.bullet.bounds.height, 1, 1, sq.bullet.rotation, true);
        }
        batch.end();
//        float side = world.bob.velocity.x < 0 ? -1 : 1;
//        if (side < 0)
//            batch.draw(keyFrame, world.bob.position.x + 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
//        else
//            batch.draw(keyFrame, world.bob.position.x - 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
    }

    public void renderActionButtons(){
        batch.begin();
        if(! world.actionMenu.isReadyToSubmit) {
            batch.draw(Assets.menuRegion, world.actionMenu.position.x - (World.MENU_WIDTH/4), world.actionMenu.position.y, world.MENU_WIDTH, world.MENU_HEIGHT);
        }
        else
            batch.draw(Assets.submitRegion, world.actionMenu.position.x - (World.MENU_WIDTH/4), world.actionMenu.position.y, world.MENU_WIDTH, world.MENU_HEIGHT);
        batch.end();
    }

    public void renderDot(){
        batch.begin();
        TextureRegion keyFrame = Assets.dotOscillating.getKeyFrame(world.dot.stateTime, Animation.ANIMATION_LOOPING);
        batch.draw(keyFrame, world.dot.position.x, world.dot.position.y, world.dot.bounds.width, world.dot.bounds.height);
        batch.end();
    }
}
