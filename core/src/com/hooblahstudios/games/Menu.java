package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
    public HashMap<String, TextField> menuTextFields;
    public TextureRegion menu;
    //lets say 1 = splash screen, 2 = main menu, 3 = options
    public int menuNumber;
    proofOfConcept game;
    ApiCall apiCall;

    public static final int MENU_SPLASH = 1;
    public static final int MENU_SIGNIN = 2;
    public static final int MENU_SIGNUP = 3;
    public static final int MENU_WELCOME = 4;
    public static final int MENU_MAINMENU = 5;
    public static final int MENU_OPTIONS = 6;

    public ArrayList<String> httpReturns;

    public final float squareWidth = 15;
    public final float squareHeight = 31;
    boolean hasStarted = false;
    boolean isSetting = true;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;

    Boolean shouldClear;
    public int blinkTimer;//this is used in menuRendered to process the blinking Charity Champs menu
    Boolean isSplash;
    Boolean unfocusAll;



    public Menu(proofOfConcept game) {
        //defaults to splash, since that should be first
        menu = Assets.splashRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        httpReturns = new ArrayList<String>();
        menuNumber = this.MENU_SPLASH;
        this.game = game;
        apiCall = new ApiCall();
        blinkTimer = 0;
        shouldClear = false;
        isSplash = true;
        unfocusAll = false;
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


    public void processSignIn(String username, String password)
    {

        String url = "http://45.33.62.187/api/v1/player/?username_hash=" + sha256(username) + "&password_hash=" + sha256(password) + "&format=json";
        String getPlayer = apiCall.httpGet(url, httpReturns.size());

        if (getPlayer.equalsIgnoreCase("FAILED") || getPlayer.equalsIgnoreCase("CANCELLED") || getPlayer.equalsIgnoreCase("EMPTY"))
        {
            menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
        }
        else {
            JSONObject json = new JSONObject(getPlayer);
            JSONArray playerArray = json.getJSONArray("objects");
            JSONObject player0 = playerArray.getJSONObject(0);
            int wins = player0.getInt("wins");
            int losses = player0.getInt("losses");
            String charityURL = player0.getString("charity");

            //store players id during this playing session in proofOfConcept (best place I can think of at the moment)
            int id = player0.getInt("id");
            game.setPlayerID(id);

            String getCharity = apiCall.httpGet("http://45.33.62.187" + charityURL + "?format=json", httpReturns.size());
            if (getCharity.equalsIgnoreCase("FAILED") || getCharity.equalsIgnoreCase("CANCELLED") || getCharity.equalsIgnoreCase("EMPTY"))
            {
                menuComponents.add(new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion)); //should be 3 unless 3 is already there, then it will be 4
            }
            else {
                JSONObject jsonCharity = new JSONObject(getCharity);
                String charityName = jsonCharity.getString("name");
                int charityIcon = jsonCharity.getInt("icon");
                this.welcome(username, wins, losses, charityName, charityIcon);
            }
        }
    }

    public void processSignUp(String username, String password, String email, String charity)
    {
        String URL = "http://45.33.62.187/api/v1/player/?format=json";
        String Body = "" +
                "{" +
                "   \"charity\": \"/api/v1/charity/" + charity + "/\"," +
                "   \"email\": \"" + email + "\"," +
                "   \"username\": \"" + username + "\"," +
                "   \"username_hash\": \"" + sha256(username) + "\"," +
                "   \"password_hash\": \"" + sha256(password) + "\"," +
                "   \"wins\": 0," +
                "   \"losses\": 0" +
                "}";


        String results = apiCall.httpPostPutOrPatch(URL, Body, httpReturns.size(), false, false);//makes the first call to insert player to table


        //below gets the userID from another query, then posts that to the matchmaking table
        String url = "http://45.33.62.187/api/v1/player/?username_hash=" + sha256(username) + "&password_hash=" + sha256(password) + "&format=json";
        String getPlayer = apiCall.httpGet(url, httpReturns.size());

        if (getPlayer.equalsIgnoreCase("FAILED") || getPlayer.equalsIgnoreCase("CANCELLED") || getPlayer.equalsIgnoreCase("EMPTY"))
        {
            menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
        }
        else {
            JSONObject json = new JSONObject(getPlayer);
            JSONArray playerArray = json.getJSONArray("objects");
            JSONObject player0 = playerArray.getJSONObject(0);
            int id = player0.getInt("id");

            //post to matchmaking
            URL = "http://45.33.62.187/api/v1/matchmaking/?format=json";
            Body = "" +
                    "{" +
                    "   \"player\": \"/api/v1/player/" + id + "/\"," +
                    "   \"match\": null," +
                    "   \"waiting\": false" +
                    "}";

            results = apiCall.httpPostPutOrPatch(URL, Body, httpReturns.size(), false, false);
            //end post to matchmaking

        }
        //end get playerID
    }


    public void touched(float x, float y){

        if (menuNumber == this.MENU_SPLASH)//splash
        {
            this.isSplash = false;
            this.signIn();

        }
        else if (menuNumber == this.MENU_SIGNIN)//sign in
        {

            if (menuComponents.get(0).bounds.contains(x, y))//LETS GO
            {
                menuComponents.get(0).texture = Assets.menuLetsGoDarkRegion;
                this.processSignIn(menuTextFields.get("usernameTF").getText(), menuTextFields.get("passwordTF").getText());
            }
            else if (menuComponents.get(1).bounds.contains(x, y))//SIGN UP
            {
                menuComponents.get(1).texture = Assets.menuSignUpDarkRegion;
                this.signUp();
            }
            else if (menuComponents.get(2).bounds.contains(x, y))//shortcut to sign in as spence95
            {
                this.processSignIn("spence95", "abc");
            }

            /*if (menuComponents.get(2).bounds.contains(x, y))//submit
            {

                this.processSignIn(menuTextFields.get(0).getText(), menuTextFields.get(1).getText());

            }
            else if (menuComponents.get(3).bounds.contains(x, y))
            {
                this.signUp();
            }
            else if (menuComponents.get(4).bounds.contains(x, y))//shortcut to sign in as spence95
            {
                this.processSignIn("spence95", "abc");
            }*/
        }
        else if (menuNumber == this.MENU_WELCOME)//welcome
        {
            this.mainMenu();
        }
        else if (menuNumber == this.MENU_SIGNUP)//signup
        {

            if (menuComponents.get(4).bounds.contains(x, y))//submit
            {
                //this.processSignUp(menuTextFields.get(0).getText(), menuTextFields.get(1).getText(), menuTextFields.get(2).getText(), menuTextFields.get(3).getText());

                //this.processSignIn(menuTextFields.get(0).getText(), menuTextFields.get(1).getText());
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
                //goes to lobby (loading), lobby goes to game when ready
                
                game.setScreen(new LobbyScreen(game));
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
        shouldClear = true;
        menu = Assets.menuSplashCharityChampsRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        menuNumber = this.MENU_SPLASH;
        blinkTimer = 0;
        this.isSplash = true;
    }

    public void signIn(){
        shouldClear = true;
        menu = Assets.menuLoginRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        menuComponents.add(0, new MenuComponent(600, 200, 350, 150, Assets.menuLetsGoRegion));
        menuComponents.add(1, new MenuComponent(200, 200, 350, 150, Assets.menuSignUpRegion));

        //menuComponents.add(0, new MenuComponent(100, 300, 100, 50, Assets.menuUsernameRegion));
        //menuComponents.add(1, new MenuComponent(500, 300, 100, 50, Assets.menuPasswordRegion));
        //menuComponents.add(2, new MenuComponent(600, 50, 100, 50, Assets.menuSubmitRegion));
        //menuComponents.add(3, new MenuComponent(400, 50, 100, 50, Assets.menuSignupRegion));
        menuComponents.add(2, new MenuComponent(45, 45, 178, 267, Assets.menuSpenceRegion));


        final TextField usernameTextField = new TextField("USERNAME", Assets.tfs);
        usernameTextField.setPosition(50, 270);
        usernameTextField.setWidth(300);
        usernameTextField.setHeight(50);
        usernameTextField.setFocusTraversal(false);
        usernameTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (usernameTextField.getText().equals("USERNAME"))
                    usernameTextField.setText("");

            }
        });
        usernameTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });


        final TextField passwordTextField = new TextField("PASSWORD", Assets.tfs);
        passwordTextField.setPosition(450, 270);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setWidth(300);
        passwordTextField.setHeight(50);
        passwordTextField.setFocusTraversal(false);
        passwordTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (passwordTextField.getText().equals("PASSWORD"))
                    passwordTextField.setText("");

            }
        });
        passwordTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')){
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });

        menuTextFields.put("usernameTF", usernameTextField);
        menuTextFields.put("passwordTF", passwordTextField);


        menuNumber = this.MENU_SIGNIN;
    }

    public void signUp(){
        shouldClear = true;
        menu = Assets.menuSignupScreenRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        menuComponents.add(0, new MenuComponent(200, 400, 100, 50, Assets.menuUsernameRegion));
        menuComponents.add(1, new MenuComponent(200, 300, 100, 50, Assets.menuPasswordRegion));
        menuComponents.add(2, new MenuComponent(200, 200, 100, 50, Assets.menuEmailRegion));
        menuComponents.add(3, new MenuComponent(200, 100, 100, 50, Assets.menuCharityRegion));
        menuComponents.add(4, new MenuComponent(600, 50, 100, 50, Assets.menuSubmitRegion));
        menuComponents.add(5, new MenuComponent(400, 50, 100, 50, Assets.menuReturnRegion));




        TextField usernameTextField = new TextField("", Assets.tfs);
        usernameTextField.setPosition(300, 350);
        usernameTextField.setWidth(400);
        usernameTextField.setHeight(50);
        usernameTextField.setMaxLength(16);//usernames can be 16 characters long
        usernameTextField.setFocusTraversal(false);


        TextField passwordTextField = new TextField("", Assets.tfs);
        passwordTextField.setPosition(300, 250);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setWidth(400);
        passwordTextField.setHeight(50);
        passwordTextField.setFocusTraversal(false);

        TextField emailTextField = new TextField("", Assets.tfs);
        emailTextField.setPosition(300, 150);
        emailTextField.setWidth(400);
        emailTextField.setHeight(50);
        emailTextField.setFocusTraversal(false);

        TextField charityTextField = new TextField("", Assets.tfs);
        charityTextField.setPosition(300, 50);
        charityTextField.setWidth(400);
        charityTextField.setHeight(50);
        charityTextField.setFocusTraversal(false);

        menuTextFields.put("usernameTF", usernameTextField);
        menuTextFields.put("passwordTF", passwordTextField);
        menuTextFields.put("emailTF", emailTextField);
        menuTextFields.put("charityTF", charityTextField);

        menuNumber = this.MENU_SIGNUP;
    }

    public void welcome(String username, int wins, int losses, String charityName, int charityIcon)
    {
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        TextField usernameTextField = new TextField(("WELCOME " + username.toUpperCase() + "!"), Assets.tfsBigBlue70);
        usernameTextField.setPosition(50, 250);
        usernameTextField.setWidth(700);
        usernameTextField.setHeight(50);
        usernameTextField.setFocusTraversal(false);
        usernameTextField.setDisabled(true);

        menuTextFields.put("usernameTF", usernameTextField);

        menuNumber = this.MENU_WELCOME;
    }

    public void mainMenu(){
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        menuComponents.add(0, new MenuComponent(250, 380, 350, 150, Assets.menuButtonRegion));//play button
        menuComponents.add(1, new MenuComponent(250, 200, 350, 150, Assets.menuButtonRegion));//options button

        TextField playTextField = new TextField(("PLAY!"), Assets.tfsTransWhite100);
        playTextField.setPosition(90, 255);
        playTextField.setWidth(325);//to be centered well make the width about 325
        playTextField.setHeight(50);
        playTextField.setAlignment(Align.center);
        playTextField.setFocusTraversal(false);
        playTextField.setDisabled(true);

        TextField optionsTextField = new TextField(("OPTIONS"), Assets.tfsTransWhite100);
        optionsTextField.setPosition(90, 75);
        optionsTextField.setWidth(350);
        optionsTextField.setHeight(50);
        optionsTextField.setFocusTraversal(false);
        optionsTextField.setDisabled(true);

        TextField charityChampsTextField = new TextField(("CHARITY CHAMPS"), Assets.tfsTrans100);
        charityChampsTextField.setPosition(100, 390);
        charityChampsTextField.setWidth(700);
        charityChampsTextField.setHeight(50);
        charityChampsTextField.setFocusTraversal(false);
        charityChampsTextField.setDisabled(true);

        menuTextFields.put("playTF", playTextField);
        menuTextFields.put("optionsTF", optionsTextField);
        menuTextFields.put("charityChampsTF", charityChampsTextField);

        menuNumber = this.MENU_MAINMENU;
    }

    public void options()
    {
        shouldClear = true;
        menuNumber = this.MENU_OPTIONS;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        menuComponents.add(0, new MenuComponent(600, 200, 350, 150, Assets.menuButtonRegion));//return
        menuComponents.add(1, new MenuComponent(250, 380, 350, 150, Assets.menuButtonRegion));//player
        menuComponents.add(2, new MenuComponent(250, 200, 350, 150, Assets.menuButtonRegion));//notifications

        TextField playTextField = new TextField(("PLAYER"), Assets.tfsTransWhite100);
        playTextField.setPosition(90, 255);
        playTextField.setWidth(325);//to be centered well make the width about 325
        playTextField.setHeight(50);
        playTextField.setAlignment(Align.center);
        playTextField.setFocusTraversal(false);
        playTextField.setDisabled(true);

        TextField optionsTextField = new TextField(("AUDIO"), Assets.tfsTransWhite100);
        optionsTextField.setPosition(90, 75);
        optionsTextField.setWidth(325);
        optionsTextField.setHeight(50);
        optionsTextField.setAlignment(Align.center);
        optionsTextField.setFocusTraversal(false);
        optionsTextField.setDisabled(true);

        TextField returnTextField = new TextField(("RETURN"), Assets.tfsTransWhite100);
        returnTextField.setPosition(440, 75);
        returnTextField.setWidth(325);//to be centered well make the width about 325
        returnTextField.setHeight(50);
        returnTextField.setAlignment(Align.center);
        returnTextField.setFocusTraversal(false);
        returnTextField.setDisabled(true);

        TextField charityChampsTextField = new TextField(("OPTIONS"), Assets.tfsTrans100);
        charityChampsTextField.setPosition(100, 390);
        charityChampsTextField.setWidth(700);
        charityChampsTextField.setHeight(50);
        charityChampsTextField.setFocusTraversal(false);
        charityChampsTextField.setDisabled(true);

        menuTextFields.put("playTF", playTextField);
        menuTextFields.put("optionsTF", optionsTextField);
        menuTextFields.put("returnTF", returnTextField);
        menuTextFields.put("charityChampsTF", charityChampsTextField);

    }

    public void update(float deltaTime){

    }


}