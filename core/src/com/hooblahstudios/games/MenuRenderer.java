package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuRenderer {
    Menu menu;
    OrthographicCamera cam;
    SpriteBatch batch;

    public MenuRenderer(SpriteBatch batch, Menu menu) {
        this.menu = menu;
        this.cam = new OrthographicCamera(this.menu.MENU_WIDTH, this.menu.MENU_HEIGHT);
        this.cam.position.set(this.menu.MENU_WIDTH / 2, this.menu.MENU_HEIGHT / 2, 0);
        this.batch = batch;
    }

    public void render () {
        //renderBackground();
        //renderObjects();
        //have it know what to draw here and then just draw it here.
        batch.disableBlending();
        batch.begin();
        batch.draw(menu.menu, cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH,
                this.menu.MENU_HEIGHT);
        for (int i = 0; i < this.menu.menuComponents.size(); i++)
        {
            MenuComponent mC = this.menu.menuComponents.get(i);
            batch.draw(mC.texture, mC.position.x - (mC.bounds.width / 2), mC.position.y - (mC.bounds.width / 2), mC.bounds.width, mC.bounds.height);
        }
        batch.end();
    }

    /*public void renderBackground () {
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.backgroundRegion, cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH,
                this.menu.MENU_HEIGHT);
        batch.end();
    }*/

    /*public void renderObjects () {
        /*renderPlayers();
        renderActionButtons();
        renderDot();
        renderBlocks();
        renderExplosions();
    }*/

    /*private void renderExplosions() {
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
*/

}
