package com.werbenjagermanjensenstudios.charitychamps;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MenuRenderer {
    Menu menu;
    OrthographicCamera cam;
    SpriteBatch batch;
    Stage stage;
    StretchViewport viewport;
    Table container;
    //Stage scrollerStage;

    public MenuRenderer(SpriteBatch batch, Menu menu) {
        this.menu = menu;
        this.cam = new OrthographicCamera(this.menu.MENU_WIDTH, this.menu.MENU_HEIGHT);
        this.cam.position.set(this.menu.MENU_WIDTH / 2, this.menu.MENU_HEIGHT / 2, 0);//added the cast to int to see if thta messed it up
        this.batch = batch;
        viewport = new StretchViewport(800, 480, cam);
        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);



    }

    public void addCharityScrollPaneToStage()
    {
        //troll stuff to enable the scrollpane

        //stage = new Stage(viewport, batch);
        //Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        container = new Table();
        stage.addActor(container);
        //container.setFillParent(true);
        container.setBackground(new TextureRegionDrawable(Assets.menuSplashBlankRegion));
        //container.setBounds(0, 100, 800, 100);
        container.setBounds(0, 100, 800, 125);

        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(Assets.menuSplashBlankRegion));

        // table.debug();

        final ScrollPane scroll = new ScrollPane(table, skin);

        InputListener stopTouchDown = new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                event.stop();
                return false;
            }
        };
        //pad does the pad on the absolute left and absolute right. (start and end of the list)
        //space does weird. best not to mess with this? added a pad but also a weird thing where it only filled about the left 60% of the scream at value40
        //table.pad(10).defaults().expandX().space(4);
        table.pad(10);
        //table.setBounds(100, 100, 400, 200);
        table.row();
        for (int i = 0; i < 20; i++) {

            Image img = new Image(Assets.menuCharity1Region);
            table.add(img);
            img.addListener(new ClickListener() {
                public void clicked (InputEvent event, float x, float y) {
                    System.out.println("click " + x + ", " + y);
                }
            });

        }
        table.row();

        scroll.setFlickScroll(true);
        scroll.setupFadeScrollBars(0, 0);
        scroll.setupOverscroll(0, 0, 0);
        scroll.setScrollingDisabled(false, true);
        //container.add(scroll).expand().fill().colspan(4);
        container.add(scroll);
        container.row();//.space(10).padBottom(10);

        //container.setBounds(100, 100, 400, 200);

        //end that troll stuff
    }

    public void render () {
        //renderBackground();
        //renderObjects();
        //have it know what to draw here and then just draw it here.


        if (this.menu.shouldAddScrollPane)
        {
            if (this.menu.shouldClear) {//it should clear because it changed screens and we want to remove the actors from the stage
                System.out.println("did this once");
                stage.clear();
                this.menu.shouldClear = false;
                this.addCharityScrollPaneToStage();

            }

        }


        batch.enableBlending();

        batch.begin();
        if (this.menu.shouldClear) {//it should clear because it changed screens and we want to remove the actors from the stage
            stage.clear();
            this.menu.shouldClear = false;
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
            stage.unfocusAll();
            this.menu.unfocusAll = false;
        }

        //batch.draw(menu.menu, cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH,
        //        this.menu.MENU_HEIGHT);//draw this first since its the bottom
        Image lol2 = new Image(menu.menu);
        lol2.setBounds(cam.position.x - this.menu.MENU_WIDTH / 2, cam.position.y - this.menu.MENU_HEIGHT / 2, this.menu.MENU_WIDTH, this.menu.MENU_HEIGHT);
        lol2.draw(batch, 1);

        if (this.menu.shouldAddScrollPane)
        {


            /*

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            scrollerStage.act(Gdx.graphics.getDeltaTime());
            scrollerStage.draw();
             */
            //stage.draw();
            //System.out.println("About to call draw container with shouldaddscrollpane: " + this.menu.shouldAddScrollPane + " and shouldClear: " + this.menu.shouldClear);
            container.draw(batch, 1);
        }


        this.menu.blinkTimer++;

        for (int i = 0; i < this.menu.menuComponents.size(); i++)
        {
            MenuComponent mC = this.menu.menuComponents.get(i);
            //batch.draw(mC.texture, mC.position.x - (mC.bounds.width / 2), mC.position.y - (mC.bounds.width / 2), mC.bounds.width, mC.bounds.height);
            Image lols = new Image(mC.texture);
            lols.setBounds(mC.position.x - (mC.bounds.width / 2), mC.position.y - (mC.bounds.width / 2), mC.bounds.width, mC.bounds.height);
            lols.draw(batch, 1);
            //System.out.println("drawing menuComponent: " + i);
        }
        //for (int j = 0; j < this.menu.menuTextFields.size(); j++)
        for (String key : this.menu.menuTextFields.keySet())
        {
            TextField tF = this.menu.menuTextFields.get(key);
            tF.draw(batch, 1);
            stage.addActor(tF);

        }

        stage.act();//this is added for the scrollpane to work correctly
        //stage.draw();

        batch.end();
        batch.disableBlending();



    }



}
