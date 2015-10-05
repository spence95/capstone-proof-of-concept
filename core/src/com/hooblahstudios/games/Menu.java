package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Menu {

    //wilsons stuff
    public static final float MENU_WIDTH = 800;
    public static final float MENU_HEIGHT = 480;
    public ArrayList<MenuComponent> menuComponents;
    public TextureRegion menu;
    //lets say 1 = splash screen, 2 = main menu, 3 = options
    public int menuNumber;
    proofOfConcept game;


    public final float squareWidth = 15;
    public final float squareHeight = 31;
    boolean hasStarted = false;
    boolean isSetting = true;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;



    public Menu(proofOfConcept game) {
        //defaults to splash, since that should be first
        menu = Assets.splashRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuNumber = 1;
        this.game = game;
    }


    public void touched(float x, float y){

        if (menuNumber == 1)
        {
            this.mainMenu();
            //game.setScreen(new MainMenuScreen(game));
        }
        else if (menuNumber == 2)
        {
            if (menuComponents.get(0).bounds.contains(x, y))
            {
                game.setScreen(new GameScreen(game));
            }
            else if (menuComponents.get(1).bounds.contains(x, y))
            {
                this.options();
                //game.setScreen(new OptionsScreen(game));
            }

        }
        else if (menuNumber == 3)
        {
            if (menuComponents.get(0).bounds.contains(x, y))
            {
                this.mainMenu();
            }
        }
        //render actions menu
            //check if click in menu bounds
            /*if(actionMenu != null) {
                if (actionMenu.menuBounds.contains(x, y) && actionMenu.isShown) {
                    if(actionMenu.isReadyToSubmit){
                        submit();
                        return;
                    }
                    if(x > 439) {
                        moveClicked(lastTouchedX, lastTouchedY);
                    } else {
                        attackClicked(lastTouchedX, lastTouchedY);
                    }

                } else {
                    bringUpMenu(x, y);
                }
            }*/

        //looks like the pertinent ones are lastTouchedX and lastTouchedY
    }


    public void splash(){
        menu = Assets.splashRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuNumber = 1;
    }

    public void mainMenu(){
        menu = Assets.mainMenuRegion;
        menuComponents = new ArrayList<MenuComponent>();

        menuComponents.add(0, new MenuComponent(200, 200, 100, 50, Assets.menuPlayRegion));
        menuComponents.add(1, new MenuComponent(200, 300, 100, 50, Assets.menuOptionsRegion));
        menuNumber = 2;
    }

    public void options()
    {
        menuNumber = 3;
        menu = Assets.optionsRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuComponents.add(0, new MenuComponent(600, 50, 100, 50, Assets.menuReturnRegion));
        menuComponents.add(1, new MenuComponent(200, 200, 100, 50, Assets.menuOptionsPlayerRegion));
        menuComponents.add(2, new MenuComponent(200, 300, 100, 50, Assets.menuOptionsNotificationRegion));
    }

    public void update(float deltaTime){

    }


}