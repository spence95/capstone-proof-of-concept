package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;




public class Menu {

    //wilsons stuff
    public static final float MENU_WIDTH = 800;
    public static final float MENU_HEIGHT = 480;
    public ArrayList<MenuComponent> menuComponents;
    public ArrayList<TextField> menuTextFields;
    public TextureRegion menu;
    //lets say 1 = splash screen, 2 = main menu, 3 = options
    public int menuNumber;
    proofOfConcept game;
    ApiCall apiCall;
<<<<<<< HEAD
=======

    public static final int MENU_SPLASH = 1;
    public static final int MENU_SIGNIN = 2;
    public static final int MENU_SIGNUP = 3;
    public static final int MENU_WELCOME = 4;
    public static final int MENU_MAINMENU = 5;
    public static final int MENU_OPTIONS = 6;
>>>>>>> proofOfConcept/WilsonSigninAndPosting

    public ArrayList<String> httpReturns;

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
        menuTextFields = new ArrayList<TextField>();
        httpReturns = new ArrayList<String>();
        menuNumber = this.MENU_SPLASH;
        this.game = game;
        apiCall = new ApiCall();
    }

    public static String sha256(String base) { //this is almost certainly a really bad idea. eff it
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }




    public void touched(float x, float y){

        if (menuNumber == this.MENU_SPLASH)//splash
        {
            this.signIn();
        }
        else if (menuNumber == this.MENU_SIGNIN)//sign in
        {
            if (menuComponents.get(2).bounds.contains(x, y))//submit
            {
                System.out.println("username: " + menuTextFields.get(0).getText());
                System.out.println(sha256(menuTextFields.get(0).getText()));
                System.out.println("password: " + menuTextFields.get(1).getText());
                System.out.println(sha256(menuTextFields.get(1).getText()));

                String getPlayer = apiCall.httpGet("http://45.33.62.187/api/v1/player/?username_hash=" + sha256(menuTextFields.get(0).getText()) + "&password_hash=" + sha256(menuTextFields.get(1).getText()) + "&format=json", httpReturns.size());

                if (getPlayer.equalsIgnoreCase("FAILED") || getPlayer.equalsIgnoreCase("CANCELLED") || getPlayer.equalsIgnoreCase("EMPTY"))
                {
                    menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
                }
                else {
                    System.out.println(getPlayer);
                    JSONObject json = new JSONObject(getPlayer);
                    JSONArray playerArray = json.getJSONArray("objects");
                    System.out.println(playerArray.toString());
                    JSONObject player0 = playerArray.getJSONObject(0);
                    System.out.println(player0.toString());
                    String username = player0.getString("username");
                    int wins = player0.getInt("wins");
                    int losses = player0.getInt("losses");
                    String charityURL = player0.getString("charity");
                    System.out.println(username);
                    System.out.println(wins);
                    System.out.println(losses);
                    System.out.println(charityURL);

                    String getCharity = apiCall.httpGet("http://45.33.62.187" + charityURL + "?format=json", httpReturns.size());
                    if (getCharity.equalsIgnoreCase("FAILED") || getCharity.equalsIgnoreCase("CANCELLED") || getCharity.equalsIgnoreCase("EMPTY"))
                    {
                        menuComponents.add(new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion)); //should be 3 unless 3 is already there, then it will be 4
                    }
                    else {
                        System.out.println(getCharity);
                        JSONObject jsonCharity = new JSONObject(getCharity);
                        String charityName = jsonCharity.getString("name");
                        int charityIcon = jsonCharity.getInt("icon");
                        System.out.println(charityName);
                        System.out.println(charityIcon);

                        this.welcome(username, wins, losses, charityName, charityIcon);

                    }

                }


            }
            else if (menuComponents.get(3).bounds.contains(x, y))
            {
                this.signUp();
            }
        }
        else if (menuNumber == this.MENU_WELCOME)//welcome
        {
            this.mainMenu();
        }
        else if (menuNumber == this.MENU_SIGNUP)//signup
        {

            if (menuComponents.get(4).bounds.contains(x, y))//submit
            {
                System.out.println("username: " + menuTextFields.get(0).getText());
                System.out.println(sha256(menuTextFields.get(0).getText()));
                System.out.println("password: " + menuTextFields.get(1).getText());
                System.out.println(sha256(menuTextFields.get(1).getText()));
                System.out.println("email: " + menuTextFields.get(2).getText());
                System.out.println(sha256(menuTextFields.get(2).getText()));
                System.out.println("charity: " + menuTextFields.get(3).getText());
                System.out.println(sha256(menuTextFields.get(3).getText()));

                //String getPlayer = httpGet("http://45.33.62.187/api/v1/player/?username_hash=" + sha256(menuTextFields.get(0).getText()) + "&password_hash=" + sha256(menuTextFields.get(1).getText()) + "&format=json", httpReturns.size());

                String URL = "http://45.33.62.187/api/v1/player/?format=json";
                String Body = "" +
                        "{" +
                        "   \"charity\": \"/api/v1/charity/" + menuTextFields.get(3).getText() + "/\"," +
                        "   \"email\": \"" + menuTextFields.get(2).getText() + "\"," +
                        "   \"username\": \"" + menuTextFields.get(0).getText() + "\"," +
                        "   \"username_hash\": \"" + sha256(menuTextFields.get(0).getText()) + "\"," +
                        "   \"password_hash\": \"" + sha256(menuTextFields.get(1).getText()) + "\"," +
                        "   \"wins\": 0," +
                        "   \"losses\": 0" +
                        "}";

                System.out.println(Body);

                String results = apiCall.httpPost(URL, Body, httpReturns.size());
                System.out.println(results);



                String getCharity = apiCall.httpGet("http://45.33.62.187/api/v1/charity/" + menuTextFields.get(3).getText() + "/?format=json", httpReturns.size());
                if (getCharity.equalsIgnoreCase("FAILED") || getCharity.equalsIgnoreCase("CANCELLED") || getCharity.equalsIgnoreCase("EMPTY"))
                {
                    menuComponents.add(new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion)); //should be 3 unless 3 is already there, then it will be 4
                }
                else {
                    System.out.println(getCharity);
                    JSONObject jsonCharity = new JSONObject(getCharity);
                    String charityName = jsonCharity.getString("name");
                    int charityIcon = jsonCharity.getInt("icon");
                    System.out.println(charityName);
                    System.out.println(charityIcon);

                    this.welcome(menuTextFields.get(0).getText(), 0, 0, charityName, charityIcon);

                }


            }
            else if (menuComponents.get(5).bounds.contains(x, y))//return
            {
                this.splash();
            }


        }
        else if (menuNumber == this.MENU_MAINMENU)//main menu
        {
            if (menuComponents.get(0).bounds.contains(x, y))
            {
                //apiCall.httpPost()
                game.setScreen(new GameScreen(game));
            }
            else if (menuComponents.get(1).bounds.contains(x, y))
            {
                this.options();

            }

        }
        else if (menuNumber == this.MENU_OPTIONS)//options
        {
            if (menuComponents.get(0).bounds.contains(x, y))//return button
            {
                this.mainMenu();
            }
        }
    }




    public void splash(){
        menu = Assets.splashRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();
        menuNumber = this.MENU_SPLASH;
    }

    public void signIn(){
        menu = Assets.menuSigninRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();

        menuComponents.add(0, new MenuComponent(200, 300, 100, 50, Assets.menuUsernameRegion));
        menuComponents.add(1, new MenuComponent(200, 200, 100, 50, Assets.menuPasswordRegion));
        menuComponents.add(2, new MenuComponent(600, 50, 100, 50, Assets.menuSubmitRegion));
        menuComponents.add(3, new MenuComponent(400, 50, 100, 50, Assets.menuSignupRegion));

        Skin usernameSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
        TextField usernameTextField = new TextField("", usernameSkin);
        usernameTextField.setPosition(300, 300);
        usernameTextField.setWidth(400);
        usernameTextField.setFocusTraversal(false);
        Skin passwordSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
        TextField passwordTextField = new TextField("", passwordSkin);
        passwordTextField.setPosition(300, 200);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('a');
        passwordTextField.setWidth(400);
        passwordTextField.setFocusTraversal(false);
        menuTextFields.add(0, usernameTextField);
        menuTextFields.add(1, passwordTextField);

        menuNumber = this.MENU_SIGNIN;
    }

    public void signUp(){
        menu = Assets.menuSignupScreenRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();

        menuComponents.add(0, new MenuComponent(200, 400, 100, 50, Assets.menuUsernameRegion));
        menuComponents.add(1, new MenuComponent(200, 300, 100, 50, Assets.menuPasswordRegion));
        menuComponents.add(2, new MenuComponent(200, 200, 100, 50, Assets.menuEmailRegion));
        menuComponents.add(3, new MenuComponent(200, 100, 100, 50, Assets.menuCharityRegion));
        menuComponents.add(4, new MenuComponent(600, 50, 100, 50, Assets.menuSubmitRegion));
        menuComponents.add(5, new MenuComponent(400, 50, 100, 50, Assets.menuReturnRegion));

        Skin usernameSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
        TextField usernameTextField = new TextField("", usernameSkin);
        usernameTextField.setPosition(300, 400);
        usernameTextField.setWidth(400);
        usernameTextField.setFocusTraversal(false);


        TextField passwordTextField = new TextField("", usernameSkin);
        passwordTextField.setPosition(300, 300);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('a');
        passwordTextField.setWidth(400);
        passwordTextField.setFocusTraversal(false);

        TextField emailTextField = new TextField("", usernameSkin);
        emailTextField.setPosition(300, 200);
        emailTextField.setWidth(400);
        emailTextField.setFocusTraversal(false);

        TextField charityTextField = new TextField("", usernameSkin);
        charityTextField.setPosition(300, 100);
        charityTextField.setWidth(400);
        charityTextField.setFocusTraversal(false);

        menuTextFields.add(0, usernameTextField);
        menuTextFields.add(1, passwordTextField);
        menuTextFields.add(2, emailTextField);
        menuTextFields.add(3, charityTextField);

        menuNumber = this.MENU_SIGNUP;
    }

    public void welcome(String username, int wins, int losses, String charityName, int charityIcon)
    {
        menu = Assets.menuWelcomeRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();

        Skin usernameSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
        TextField usernameTextField = new TextField(username, usernameSkin);
        usernameTextField.setPosition(300, 400);
        usernameTextField.setWidth(400);
        usernameTextField.setFocusTraversal(false);
        usernameTextField.setDisabled(true);

        TextField winsTextField = new TextField(String.valueOf(wins), usernameSkin);
        winsTextField.setPosition(300, 300);
        winsTextField.setWidth(400);
        winsTextField.setFocusTraversal(false);
        winsTextField.setDisabled(true);

        TextField lossesTextField = new TextField(String.valueOf(losses), usernameSkin);
        lossesTextField.setPosition(300, 200);
        lossesTextField.setWidth(400);
        lossesTextField.setFocusTraversal(false);
        lossesTextField.setDisabled(true);

        TextField charityTextField = new TextField(charityName, usernameSkin);
        charityTextField.setPosition(300, 100);
        charityTextField.setWidth(400);
        charityTextField.setFocusTraversal(false);
        charityTextField.setDisabled(true);

        menuTextFields.add(0, usernameTextField);
        menuTextFields.add(1, winsTextField);
        menuTextFields.add(2, lossesTextField);
        menuTextFields.add(3, charityTextField);

        menuNumber = this.MENU_WELCOME;
    }

    public void mainMenu(){
        menu = Assets.mainMenuRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();

        menuComponents.add(0, new MenuComponent(200, 300, 100, 50, Assets.menuPlayRegion));
        menuComponents.add(1, new MenuComponent(200, 200, 100, 50, Assets.menuOptionsRegion));
        menuNumber = this.MENU_MAINMENU;
    }

    public void options()
    {
        menuNumber = this.MENU_OPTIONS;
        menu = Assets.optionsRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new ArrayList<TextField>();
        menuComponents.add(0, new MenuComponent(600, 50, 100, 50, Assets.menuReturnRegion));
        menuComponents.add(1, new MenuComponent(200, 200, 100, 50, Assets.menuOptionsPlayerRegion));
        menuComponents.add(2, new MenuComponent(200, 300, 100, 50, Assets.menuOptionsNotificationRegion));
    }

    public void update(float deltaTime){

    }


}