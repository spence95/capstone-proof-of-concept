package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuRenderer {
    Menu menu;
    OrthographicCamera cam;
    SpriteBatch batch;
    Stage stage;
    StretchViewport viewport;

    public MenuRenderer(SpriteBatch batch, Menu menu) {
        this.menu = menu;
        this.cam = new OrthographicCamera(this.menu.MENU_WIDTH, this.menu.MENU_HEIGHT);
        this.cam.position.set(this.menu.MENU_WIDTH / 2, this.menu.MENU_HEIGHT / 2, 0);
        this.batch = batch;
        viewport = new StretchViewport(800, 480, cam);
        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);
    }

    public void render () {
        //renderBackground();
        //renderObjects();
        //have it know what to draw here and then just draw it here.
        batch.enableBlending();
        batch.begin();
        if (this.menu.shouldClear) {//it should clear because it changed screens and we want to remove the actors from the stage
            stage.clear();
            this.menu.shouldClear = false;
            System.out.println("should clear was true, so stage was cleared and shouldClear was set to false");
        }
        batch.draw(menu.menu, cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH,
                this.menu.MENU_HEIGHT);
        for (int i = 0; i < this.menu.menuComponents.size(); i++)
        {
            MenuComponent mC = this.menu.menuComponents.get(i);
            batch.draw(mC.texture, mC.position.x - (mC.bounds.width / 2), mC.position.y - (mC.bounds.width / 2), mC.bounds.width, mC.bounds.height);
        }
        for (int j = 0; j < this.menu.menuTextFields.size(); j++)
        {
            TextField tF = this.menu.menuTextFields.get(j);
            tF.draw(batch, 1);
            stage.addActor(tF);

        }

        batch.end();
        batch.disableBlending();
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
