package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class WorldRenderer {
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    StretchViewport viewport;

    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(this.world.WORLD_WIDTH, this.world.WORLD_HEIGHT);
        this.cam.position.set(this.world.WORLD_WIDTH / 2, this.world.WORLD_HEIGHT / 2, 0);
        this.batch = batch;
        viewport = new StretchViewport(800, 480, cam);
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
        batch.enableBlending();
        //renderSquares();
        renderPlayers();
        renderLabels();
        renderActionButtons();
        renderDot();
        renderBlocks();
        renderExplosions();
        batch.disableBlending();
    }

    private void renderExplosions() {
        batch.begin();
        for(int i = 0; i < world.explosions.size(); i++){
            Explosion exp = world.explosions.get(i);
            batch.draw(Assets.explosionRegion, exp.position.x - (exp.bounds.width / 2), exp.position.y - (exp.bounds.height / 2), exp.bounds.width, exp.bounds.height);
        }
        batch.end();
    }

    private void renderBlocks() {
        batch.begin();
        for(int i = 0; i < world.blocks.size(); i++) {
            Block bl = world.blocks.get(i);
            batch.draw(Assets.blockRegion, bl.position.x - (bl.bounds.width / 2), bl.position.y - (bl.bounds.height/2), bl.bounds.width, bl.bounds.height);
        }
        batch.end();
    }

    private void renderLabels() {
        batch.begin();
        for (Player p : world.players) {
            try {
                TextField tf = this.world.playerLabels.get((p.id));
                tf.setPosition(p.position.x, p.position.y + 10);

                tf.draw(batch, 1);
            }

            catch (Exception e){
                System.out.println(e);
            }//this will make an exception until the player id in the player thing is correct.

        }
        batch.end();
        /*for (int i : this.world.playerLabels.keySet()) {
            TextField tf = this.world.playerLabels.get(i);
            for (Player p : world.players) {
                System.out.println("checking match on " + p.id + " and " + i);
                if (p.id == i) {
                    System.out.println("Changing positions because " + p.id + " and " + i + " matched");
                    tf.setPosition(p.xLast, p.yLast + 10);
                }
            }

            tf.draw(batch, 1);
        }*/

    }

    private void renderPlayers(){
        batch.begin();
        for(int i = 0; i < world.players.size(); i++){
            Player pl = world.players.get(i);
            TextureRegion keyFrame = Assets.playerStill;

            if(pl.isMoving){
                keyFrame = Assets.playerWalking.getKeyFrame(pl.stateTime, Animation.ANIMATION_LOOPING);
            } else if(pl.isFiring){
                keyFrame = Assets.playerFiring.getKeyFrame(pl.stateTime, Animation.ANIMATION_LOOPING);
            } else if(pl.dead){
                keyFrame = Assets.playerDying.getKeyFrame(pl.stateTime, Animation.ANIMATION_NONLOOPING);
            }
//            float side = 1;
//            if(pl.velocity != null)
//                side = pl.velocity.x < 0 ? -1 : 1;
            batch.draw(keyFrame, pl.position.x - (world.squareWidth / 2), pl.position.y - (world.squareHeight / 2), world.squareWidth * pl.side, world.squareHeight);
            batch.draw(Assets.bulletRegion, pl.bullet.position.x - (pl.bullet.width / 2), pl.bullet.position.y - (pl.bullet.height / 2), pl.bullet.position.x, pl.bullet.position.y,pl.bullet.bounds.width, pl.bullet.bounds.height, pl.bullet.bounds.width/10, pl.bullet.bounds.height/10, pl.bullet.rotation, true);
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
        batch.draw(keyFrame, world.dot.position.x - (world.dot.bounds.width / 2), world.dot.position.y - (world.dot.bounds.height / 2), world.dot.bounds.width, world.dot.bounds.height);
        batch.end();
    }
}
