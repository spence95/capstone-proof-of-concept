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
    public static Texture menuSplashCharityChamps;
    public static TextureRegion menuSplashCharityChampsRegion;
    public static Texture menuSplashBlank;
    public static TextureRegion menuSplashBlankRegion;
    public static Texture menuLogin;
    public static TextureRegion menuLoginRegion;
    public static Texture menuLetsGo;
    public static TextureRegion menuLetsGoRegion;
    public static Texture menuLetsGoDark;
    public static TextureRegion menuLetsGoDarkRegion;
    public static Texture menuSignUp;
    public static TextureRegion menuSignUpRegion;
    public static Texture menuSignUpDark;
    public static TextureRegion menuSignUpDarkRegion;
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

    //end nu menuz

    public static Texture splash;
    public static TextureRegion splashRegion;
    public static Texture mainMenu;
    public static TextureRegion mainMenuRegion;
    public static Texture menuPlay;
    public static TextureRegion menuPlayRegion;
    public static Texture menuOptions;
    public static TextureRegion menuOptionsRegion;
    public static Texture Options;
    public static TextureRegion optionsRegion;
    public static Texture menuOptionsPlayer;
    public static TextureRegion menuOptionsPlayerRegion;
    public static Texture menuOptionsNotification;
    public static TextureRegion menuOptionsNotificationRegion;
    public static Texture menuReturn;
    public static TextureRegion menuReturnRegion;
    public static Texture menuSignin;
    public static TextureRegion menuSigninRegion;
    public static Texture menuUsername;
    public static TextureRegion menuUsernameRegion;
    public static Texture menuPassword;
    public static TextureRegion menuPasswordRegion;
    public static Texture menuSubmit;
    public static TextureRegion menuSubmitRegion;
    public static Texture menuFailed;
    public static TextureRegion menuFailedRegion;
    public static Texture menuWelcome;
    public static TextureRegion menuWelcomeRegion;
    //public static Texture menuSignup;
    //public static TextureRegion menuSignupRegion;
    public static Texture menuSignupScreen;
    public static TextureRegion menuSignupScreenRegion;
    public static Texture menuEmail;
    public static TextureRegion menuEmailRegion;
    public static Texture menuCharity;
    public static TextureRegion menuCharityRegion;
    public static Texture menuNinePatch;
    public static TextureRegion menuNinePatchRegion;
    public static Texture menuCursor;
    public static TextureRegion menuCursorRegion;

    public static Texture loadingMenu;
    public static TextureRegion loadingMenuRegion;

    public static Texture getRektBackground;
    public static TextureRegion rektBackgroundRegion;

    public static Texture menuSpence;
    public static TextureRegion menuSpenceRegion;

    public static TextField.TextFieldStyle tfs;
    public static TextField.TextFieldStyle tfs12;
    public static TextField.TextFieldStyle tfsBigBlue100;
    public static TextField.TextFieldStyle tfsBigBlue70;
    public static TextField.TextFieldStyle tfsTrans100;
    public static TextField.TextFieldStyle tfsTransWhite100;


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

        splash = loadTexture("splashscreen.png");
        splashRegion = new TextureRegion(splash, 0, 0, 800, 480);

        mainMenu = loadTexture("mainMenu.png");
        mainMenuRegion = new TextureRegion(mainMenu, 0, 0, 800, 480);

        menuPlay = loadTexture("menuPlay.png");
        menuPlayRegion = new TextureRegion(menuPlay, 0, 0, 100, 50);

        menuOptions = loadTexture("menuOptions.png");
        menuOptionsRegion = new TextureRegion(menuOptions, 0, 0, 100, 50);

        Options = loadTexture("options.png");
        optionsRegion = new TextureRegion(Options, 0, 0, 800, 480);
        menuOptionsPlayer = loadTexture("menuOptionsPlayer.png");
        menuOptionsPlayerRegion = new TextureRegion(menuOptionsPlayer, 0, 0, 100, 50);
        menuOptionsNotification = loadTexture("menuOptionsNotification.png");
        menuOptionsNotificationRegion = new TextureRegion(menuOptionsNotification, 0, 0, 100, 50);

        menuReturn = loadTexture("menuReturn.png");
        menuReturnRegion = new TextureRegion(menuReturn, 0, 0, 100, 50);

        menuSignin = loadTexture("menuSignin.png");
        menuSigninRegion = new TextureRegion(menuSignin, 0, 0, 800, 480);
        menuUsername = loadTexture("menuUsername.png");
        menuUsernameRegion = new TextureRegion(menuUsername, 0, 0, 100, 50);
        menuPassword = loadTexture("menuPassword.png");
        menuPasswordRegion = new TextureRegion(menuPassword, 0, 0, 100, 50);
        menuSubmit = loadTexture("menuSubmit.png");
        menuSubmitRegion = new TextureRegion(menuSubmit, 0, 0, 100, 50);
        menuFailed = loadTexture("menuFailed.png");
        menuFailedRegion = new TextureRegion(menuFailed, 0, 0, 100, 50);
        menuWelcome = loadTexture("menuWelcome.png");
        menuWelcomeRegion = new TextureRegion(menuWelcome, 0, 0, 800, 480);
        //menuSignup = loadTexture("menuSignup.png");
        //menuSignupRegion = new TextureRegion(menuSignup, 0, 0, 100, 50);

        menuEmail = loadTexture("menuEmail.png");
        menuEmailRegion = new TextureRegion(menuEmail, 0, 0, 100, 50);
        menuCharity = loadTexture("menuCharity.png");
        menuCharityRegion = new TextureRegion(menuCharity, 0, 0, 100, 50);
        menuSignupScreen = loadTexture("menuSignupScreen.png");
        menuSignupScreenRegion = new TextureRegion(menuSignupScreen, 0, 0, 800, 480);

        menuNinePatch = loadTexture("menuNinePatch.png");
        menuNinePatchRegion = new TextureRegion(menuNinePatch, 0, 0, 10, 10);
        menuCursor = loadTexture("menuCursor.png");
        menuCursorRegion = new TextureRegion(menuCursor, 0, 0, 2, 8);

        loadingMenu = loadTexture("loading.png");
        loadingMenuRegion = new TextureRegion(loadingMenu, 0, 0, 800, 480);

        menuSpence = loadTexture("menuSpence.png");
        menuSpenceRegion = new TextureRegion(menuSpence, 0, 0, 534, 799);

        //nu menuz
        menuSplashCharityChamps = loadTexture("menuSplashCharityChamps.png");
        menuSplashCharityChampsRegion = new TextureRegion(menuSplashCharityChamps, 0, 0, 800, 480);
        menuSplashBlank = loadTexture("menuSplashBlank.png");
        menuSplashBlankRegion = new TextureRegion(menuSplashBlank, 0, 0, 800, 480);
        menuLogin = loadTexture("menuLogin.png");
        menuLoginRegion = new TextureRegion(menuLogin, 0, 0, 800, 480);
        menuLetsGo = loadTexture("menuLetsGo.png");
        menuLetsGoRegion = new TextureRegion(menuLetsGo, 0, 0, 350, 150);
        menuLetsGoDark = loadTexture("menuLetsGoDark.png");
        menuLetsGoDarkRegion = new TextureRegion(menuLetsGoDark, 0, 0, 350, 150);
        menuSignUp = loadTexture("menuSignUpWorkaround.png");
        menuSignUpRegion = new TextureRegion(menuSignUp, 0, 0, 350, 150);
        menuSignUpDark = loadTexture("menuSignUpDark.png");
        menuSignUpDarkRegion = new TextureRegion(menuSignUpDark, 0, 0, 350, 150);
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

        tfsTransWhite100 = new TextField.TextFieldStyle();
        tfsTransWhite100.font = font100;
        tfsTransWhite100.fontColor = Color.WHITE;
        tfsTransWhite100.background = nuSkinTrans.getDrawable("background");
        tfsTransWhite100.cursor = nuSkinTrans.getDrawable("cursor");
        tfsTransWhite100.cursor.setMinWidth(2);
        tfsTransWhite100.selection = nuSkinTrans.newDrawable("background", 0.5f, 0.5f, 0.5f, 0.5f);

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
