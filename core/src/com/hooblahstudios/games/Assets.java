package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

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
    public static Texture menuSignup;
    public static TextureRegion menuSignupRegion;
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
        menuSignup = loadTexture("menuSignup.png");
        menuSignupRegion = new TextureRegion(menuSignup, 0, 0, 100, 50);

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
    }
}
