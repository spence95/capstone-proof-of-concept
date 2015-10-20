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
        this.cam.position.set(this.menu.MENU_WIDTH / 2, this.menu.MENU_HEIGHT / 2, 0);//added the cast to int to see if thta messed it up
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
        if (this.menu.isSplash)
        {
            if (this.menu.blinkTimer == 50) {
                this.menu.menuTextFields.get("charityChampsTF").setText("");
            }
            if (this.menu.blinkTimer > 70) {
                this.menu.menuTextFields.get("charityChampsTF").setText("CHARITY CHAMPS!");
                this.menu.blinkTimer = 0;
            }
        }
        if (this.menu.unfocusAll) {
            System.out.println("UNFOCUS ALL");
            stage.unfocusAll();
            this.menu.unfocusAll = false;
        }


        this.menu.blinkTimer++;
        batch.draw(menu.menu, cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH,
                this.menu.MENU_HEIGHT);
        for (int i = 0; i < this.menu.menuComponents.size(); i++)
        {
            MenuComponent mC = this.menu.menuComponents.get(i);
            batch.draw(mC.texture, mC.position.x - (mC.bounds.width / 2), mC.position.y - (mC.bounds.width / 2), mC.bounds.width, mC.bounds.height);
        }
        //for (int j = 0; j < this.menu.menuTextFields.size(); j++)
        for (String key : this.menu.menuTextFields.keySet())
        {
            TextField tF = this.menu.menuTextFields.get(key);
            tF.draw(batch, 1);
            stage.addActor(tF);

        }

        batch.end();
        batch.disableBlending();
    }



}
