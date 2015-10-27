package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture square;
    public static TextureRegion squareRegion;

    public static Texture actions;
    public static TextureRegion menuRegion;
    public static TextureRegion submitRegion;

    public static Texture player;
    public static TextureRegion playerWalkingRegion;
    public static TextureRegion playerStill;
    public static TextureRegion dotRegion;
    public static TextureRegion bulletRegion;
    public static TextureRegion explosionRegion;
    public static TextureRegion blockRegion;
    public static TextureRegion playerFiringRegion;
    public static TextureRegion playerDyingRegion;

    //nu menuz//
    public static Texture menuSplashBlank;
    public static TextureRegion menuSplashBlankRegion;
    public static Texture menuNinePatchBlue;
    public static TextureRegion menuNinePatchBlueRegion;
    public static Texture menuButton;
    public static TextureRegion menuButtonRegion;
    public static Texture menuButtonDark;
    public static TextureRegion menuButtonDarkRegion;
    public static Texture menuButtonGrey;
    public static TextureRegion menuButtonGreyRegion;
    public static Texture menuButtonLight;
    public static TextureRegion menuButtonLightRegion;
    public static Texture menuNinePatchTransparent;
    public static TextureRegion menuNinePatchTransparentRegion;
    public static Texture menuButtonSmall;
    public static TextureRegion menuButtonSmallRegion;
    public static Texture menuButtonSmallDark;
    public static TextureRegion menuButtonSmallDarkRegion;
    public static Texture menuButtonSmallGrey;
    public static TextureRegion menuButtonSmallGreyRegion;
    public static Texture menuButtonSmallLight;
    public static TextureRegion menuButtonSmallLightRegion;

    //end nu menuz


    public static Texture menuNinePatch;
    public static TextureRegion menuNinePatchRegion;
    public static Texture menuCursor;
    public static TextureRegion menuCursorRegion;

    //public static Texture loadingMenu;
    //public static TextureRegion loadingMenuRegion;

    public static Texture getRektBackground;
    public static TextureRegion rektBackgroundRegion;

    public static Texture winScreenBackground;
    public static TextureRegion winScreenBackgroundRegion;

    public static Texture menuSpence;
    public static TextureRegion menuSpenceRegion;

    public static TextField.TextFieldStyle tfs;
    public static TextField.TextFieldStyle tfs12;
    public static TextField.TextFieldStyle tfsBigBlue100;
    public static TextField.TextFieldStyle tfsBigBlue70;
    public static TextField.TextFieldStyle tfsTrans100;
    public static TextField.TextFieldStyle tfsTrans40;
    public static TextField.TextFieldStyle tfsTransWhite100;
    public static TextField.TextFieldStyle tfsTransWhite40;


    public static Animation playerWalking;
    public static Animation dotOscillating;
    public static Animation playerFiring;
    public static Animation playerDying;

    public static Texture loadTexture (String file) { return new Texture(Gdx.files.internal(file));}

    public static void load(){
        //textures and texture regions
        background = loadTexture("background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);

        getRektBackground = loadTexture("getRektBackground.png");
        rektBackgroundRegion = new TextureRegion(getRektBackground, 0, 0, 800, 480);

        winScreenBackground = loadTexture("winScreenBackground.png");
        winScreenBackgroundRegion = new TextureRegion(winScreenBackground, 0, 0, 800, 480);

        square = loadTexture("square.png");
        squareRegion = new TextureRegion(square, 0, 0, 50, 50);

        actions = loadTexture("actions.png");
        menuRegion = new TextureRegion(actions, 0, 0, 392, 127);
        submitRegion = new TextureRegion(actions, 0, 127, 392, 127);

        //506 by 169
        player = loadTexture("playerBig.png");
        playerWalkingRegion = new TextureRegion(player, 450, 105);
        playerStill = new TextureRegion(player, 50, 105);
        dotRegion = new TextureRegion(player, 0, 105, 506, 64);
        bulletRegion = new TextureRegion(player, 611, 0, 30, 33);
        explosionRegion = new TextureRegion(player, 611, 33, 30, 33);
        blockRegion = new TextureRegion(player, 0, 902, 63, 63);
        playerFiringRegion = new TextureRegion(player, 7, 688, 443, 105);
        playerDyingRegion = new TextureRegion(player, 0, 3, 340, 95);



        menuNinePatch = loadTexture("menuNinePatch.png");
        menuNinePatchRegion = new TextureRegion(menuNinePatch, 0, 0, 10, 10);
        menuCursor = loadTexture("menuCursor.png");
        menuCursorRegion = new TextureRegion(menuCursor, 0, 0, 2, 8);

        //loadingMenu = loadTexture("loading.png");
        //loadingMenuRegion = new TextureRegion(loadingMenu, 0, 0, 800, 480);

        menuSpence = loadTexture("menuSpence.png");
        menuSpenceRegion = new TextureRegion(menuSpence, 0, 0, 534, 799);

        //nu menuz

        menuSplashBlank = loadTexture("menuSplashBlank.png");
        menuSplashBlankRegion = new TextureRegion(menuSplashBlank, 0, 0, 800, 480);
        menuNinePatchBlue = loadTexture("menuNinePatchBlue.png");
        menuNinePatchBlueRegion = new TextureRegion(menuNinePatchBlue, 0, 0, 10, 10);
        menuButton = loadTexture("menuButton.png");
        menuButtonRegion = new TextureRegion(menuButton, 0, 0, 350, 150);
        menuButtonDark = loadTexture("menuButtonDark.png");
        menuButtonDarkRegion = new TextureRegion(menuButtonDark, 0, 0, 350, 150);
        menuButtonGrey = loadTexture("menuButtonGrey.png");
        menuButtonGreyRegion = new TextureRegion(menuButtonGrey, 0, 0, 350, 150);
        menuButtonLight = loadTexture("menuButtonLight.png");
        menuButtonLightRegion = new TextureRegion(menuButtonLight, 0, 0, 350, 150);
        menuNinePatchTransparent = loadTexture("menuNinePatchTransparent.png");
        menuNinePatchTransparentRegion = new TextureRegion(menuNinePatchTransparent, 0, 0, 10, 10);
        menuButtonSmall = loadTexture("menuButtonSmall.png");
        menuButtonSmallRegion = new TextureRegion(menuButtonSmall, 0, 0, 175, 75);
        menuButtonSmallDark = loadTexture("menuButtonSmallDark.png");
        menuButtonSmallDarkRegion = new TextureRegion(menuButtonSmallDark, 0, 0, 175, 75);
        menuButtonSmallGrey = loadTexture("menuButtonSmallGrey.png");
        menuButtonSmallGreyRegion = new TextureRegion(menuButtonSmallGrey, 0, 0, 175, 75);
        menuButtonSmallLight = loadTexture("menuButtonSmallLight.png");
        menuButtonSmallLightRegion = new TextureRegion(menuButtonSmallLight, 0, 0, 175, 75);
        //end nu menuz

        //animations
        playerWalking = new Animation(.05f,
                new TextureRegion(playerWalkingRegion, 0, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 50, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 100, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 200, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 250, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 300, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 350, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 400, 0, 50, 105)//,
               // new TextureRegion(playerWalkingRegion, 450, 0, 50, 105)
        );

        playerDying = new Animation(.05f,
                new TextureRegion(playerDyingRegion, 0, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 40, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 80, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 120, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 160, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 200, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 240, 0, 40, 95),
                new TextureRegion(playerDyingRegion, 280, 0, 40, 95)
        );

        playerFiring = new Animation(.01f,
                new TextureRegion(playerFiringRegion,0, 0, 40, 105),
                new TextureRegion(playerFiringRegion,40, 0, 40, 105),
                new TextureRegion(playerFiringRegion,80, 0, 51, 105),
                new TextureRegion(playerFiringRegion,131, 0, 59, 105),
                new TextureRegion(playerFiringRegion,190, 0, 69, 105),
                new TextureRegion(playerFiringRegion,259, 0, 54, 105),
                new TextureRegion(playerFiringRegion,313, 0, 51, 105),
                new TextureRegion(playerFiringRegion,364, 0, 40, 105),
                new TextureRegion(playerFiringRegion,404, 0, 40, 105)
                );

        dotOscillating = new Animation(.05f,
                new TextureRegion(dotRegion, 0, 0, 46, 60),
                new TextureRegion(dotRegion, 46, 0, 46, 60),
                new TextureRegion(dotRegion, 92, 0, 46, 60),
                new TextureRegion(dotRegion, 138, 0, 46, 60),
                new TextureRegion(dotRegion, 184, 0, 46, 60),
                new TextureRegion(dotRegion, 230, 0, 46, 60),
                new TextureRegion(dotRegion, 276, 0, 46, 60),
                new TextureRegion(dotRegion, 322, 0, 46, 60),
                new TextureRegion(dotRegion, 368, 0, 46, 60),
                new TextureRegion(dotRegion, 414, 0, 46, 60),
                new TextureRegion(dotRegion, 460, 0, 46, 60)
                );


        //dank fontz

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8-Bit-Madness.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        //parameter.minFilter = Texture.TextureFilter.Nearest;
        //parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;'\"(!?) +-*/=";
        BitmapFont font36 = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("8-Bit-Madness.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 12;
        parameter2.minFilter = Texture.TextureFilter.Linear;
        parameter2.magFilter = Texture.TextureFilter.Linear;
        parameter2.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;'\"(!?) +-*/=";
        BitmapFont font12 = generator2.generateFont(parameter2);
        generator2.dispose();

        FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("8-Bit-Madness.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 100;
        parameter3.minFilter = Texture.TextureFilter.Linear;
        parameter3.magFilter = Texture.TextureFilter.Linear;
        parameter3.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;'\"(!?) +-*/=";
        BitmapFont font100 = generator3.generateFont(parameter3);
        generator3.dispose();

        FreeTypeFontGenerator generator4 = new FreeTypeFontGenerator(Gdx.files.internal("8-Bit-Madness.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter4.size = 70;
        parameter4.minFilter = Texture.TextureFilter.Linear;
        parameter4.magFilter = Texture.TextureFilter.Linear;
        parameter4.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;'\"(!?) +-*/=";
        BitmapFont font70 = generator4.generateFont(parameter4);
        generator4.dispose();

        FreeTypeFontGenerator generator5 = new FreeTypeFontGenerator(Gdx.files.internal("8-Bit-Madness.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter5.size = 40;
        parameter5.minFilter = Texture.TextureFilter.Linear;
        parameter5.magFilter = Texture.TextureFilter.Linear;
        parameter5.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.:,;'\"(!?) +-*/=";
        BitmapFont font40 = generator5.generateFont(parameter5);
        generator5.dispose();

        Skin nuSkin = new Skin();
        NinePatch nP = new NinePatch(Assets.menuNinePatchRegion);
        nuSkin.add("background", nP);
        nuSkin.add("cursor", Assets.menuCursor);

        Skin nuSkinBlue = new Skin();
        NinePatch nPBlue = new NinePatch(Assets.menuNinePatchBlueRegion);
        nuSkinBlue.add("background", nPBlue);
        nuSkinBlue.add("cursor", Assets.menuCursor);

        Skin nuSkinTrans = new Skin();
        NinePatch nPTrans = new NinePatch(Assets.menuNinePatchTransparentRegion);
        nuSkinTrans.add("background", nPTrans);
        nuSkinTrans.add("cursor", Assets.menuCursor);

        //end fontz

        //make textfieldstyle

        tfs = new TextField.TextFieldStyle();
        tfs.font = font36;
        tfs.fontColor = Color.valueOf("fa7a82");
        tfs.background = nuSkin.getDrawable("background");
        tfs.cursor = nuSkin.getDrawable("cursor");
        tfs.cursor.setMinWidth(2);
        tfs.selection = nuSkin.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsBigBlue100 = new TextField.TextFieldStyle();
        tfsBigBlue100.font = font100;
        tfsBigBlue100.fontColor = Color.valueOf("fa7a82");
        tfsBigBlue100.background = nuSkinBlue.getDrawable("background");
        tfsBigBlue100.cursor = nuSkinBlue.getDrawable("cursor");
        tfsBigBlue100.cursor.setMinWidth(2);
        tfsBigBlue100.selection = nuSkinBlue.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsTrans100 = new TextField.TextFieldStyle();
        tfsTrans100.font = font100;
        tfsTrans100.fontColor = Color.valueOf("fa7a82");
        tfsTrans100.background = nuSkinTrans.getDrawable("background");
        tfsTrans100.cursor = nuSkinTrans.getDrawable("cursor");
        tfsTrans100.cursor.setMinWidth(2);
        tfsTrans100.selection = nuSkinTrans.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsTrans40 = new TextField.TextFieldStyle();
        tfsTrans40.font = font40;
        tfsTrans40.fontColor = Color.valueOf("fa7a82");
        tfsTrans40.background = nuSkinTrans.getDrawable("background");
        tfsTrans40.cursor = nuSkinTrans.getDrawable("cursor");
        tfsTrans40.cursor.setMinWidth(2);
        tfsTrans40.selection = nuSkinTrans.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsTransWhite100 = new TextField.TextFieldStyle();
        tfsTransWhite100.font = font100;
        tfsTransWhite100.fontColor = Color.WHITE;
        tfsTransWhite100.background = nuSkinTrans.getDrawable("background");
        tfsTransWhite100.cursor = nuSkinTrans.getDrawable("cursor");
        tfsTransWhite100.cursor.setMinWidth(2);
        tfsTransWhite100.selection = nuSkinTrans.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsTransWhite40 = new TextField.TextFieldStyle();
        tfsTransWhite40.font = font40;
        tfsTransWhite40.fontColor = Color.WHITE;
        tfsTransWhite40.background = nuSkinTrans.getDrawable("background");
        tfsTransWhite40.cursor = nuSkinTrans.getDrawable("cursor");
        tfsTransWhite40.cursor.setMinWidth(2);
        tfsTransWhite40.selection = nuSkinTrans.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfsBigBlue70 = new TextField.TextFieldStyle();
        tfsBigBlue70.font = font70;
        tfsBigBlue70.fontColor = Color.valueOf("fa7a82");
        tfsBigBlue70.background = nuSkinBlue.getDrawable("background");
        tfsBigBlue70.cursor = nuSkinBlue.getDrawable("cursor");
        tfsBigBlue70.cursor.setMinWidth(2);
        tfsBigBlue70.selection = nuSkinBlue.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        tfs12 = new TextField.TextFieldStyle();
        tfs12.font = font12;
        tfs12.fontColor = Color.BLACK;
        tfs12.background = nuSkin.getDrawable("background");
        tfs12.cursor = nuSkin.getDrawable("cursor");
        tfs12.cursor.setMinWidth(2);
        tfs12.selection = nuSkin.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

        //end textfieldstyle


    }
}
