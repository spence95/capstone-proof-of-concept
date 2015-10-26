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
    public static final int MENU_GAMEOVER = 7;

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
        menu = Assets.menuSplashBlankRegion;
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
            //menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
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
                //menuComponents.add(new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion)); //should be 3 unless 3 is already there, then it will be 4
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
            //menuComponents.add(3, new MenuComponent(300, 400, 100, 50, Assets.menuFailedRegion));
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
                menuComponents.get(0).texture = Assets.menuButtonSmallDarkRegion;
                this.processSignIn(menuTextFields.get("usernameTF").getText(), menuTextFields.get("passwordTF").getText());
            }
            else if (menuComponents.get(1).bounds.contains(x, y))//SIGN UP
            {
                menuComponents.get(1).texture = Assets.menuButtonSmallDarkRegion;
                this.signUp();
            }
            else if (menuComponents.get(2).bounds.contains(x, y))//shortcut to sign in as spence95
            {
                this.processSignIn("spence95", "abc");
            }


        }
        else if (menuNumber == this.MENU_WELCOME)//welcome
        {
            this.mainMenu();
        }
        else if (menuNumber == this.MENU_SIGNUP)//signup
        {

            if (menuComponents.get(0).bounds.contains(x, y))//submit
            {

                this.processSignUp(menuTextFields.get("usernameTF").getText(), menuTextFields.get("passwordTF").getText(), menuTextFields.get("emailTF").getText(), menuTextFields.get("charityTF").getText());

                this.processSignIn(menuTextFields.get("usernameTF").getText(), menuTextFields.get("passwordTF").getText());
            }
            else if (menuComponents.get(1).bounds.contains(x, y))//return
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
        else if (menuNumber == this.MENU_GAMEOVER) //game over
        {
            this.mainMenu();
        }
    }




    public void splash(){
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        menuNumber = this.MENU_SPLASH;
        blinkTimer = 0;

        TextField charityChampsTextField = new TextField(("CHARITY CHAMPS!"), Assets.tfsTrans100);
        charityChampsTextField.setPosition(50, 190);
        charityChampsTextField.setWidth(700);
        charityChampsTextField.setHeight(150);
        charityChampsTextField.setAlignment(Align.center);
        charityChampsTextField.setFocusTraversal(false);
        charityChampsTextField.setDisabled(true);

        menuTextFields.put("charityChampsTF", charityChampsTextField);

        this.isSplash = true;

    }

    public void signIn(){
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        menuComponents.add(0, new MenuComponent(600, 100, 175, 75, Assets.menuButtonSmallRegion));//lets go
        menuComponents.add(1, new MenuComponent(400, 100, 175, 75, Assets.menuButtonSmallRegion));//sign up

        //right now I am using the metric of setting the width to be 175 and height 50 (for textfields)
        //and making them 88 units below the buttons and 73 units below. thats maybe like 1 or 2 units higher than it needs to be, but it looks aight

        menuComponents.add(2, new MenuComponent(45, 45, 178, 267, Assets.menuSpenceRegion));


        TextField logInTextField = new TextField(("LOG IN"), Assets.tfsTrans100);
        logInTextField.setPosition(50, 330);
        logInTextField.setWidth(700);
        logInTextField.setHeight(150);
        logInTextField.setAlignment(Align.center);
        logInTextField.setFocusTraversal(false);
        logInTextField.setDisabled(true);

        TextField letsGoTextField = new TextField(("LETS GO"), Assets.tfsTransWhite40);
        letsGoTextField.setPosition(512, 27);
        letsGoTextField.setWidth(175);//to be centered well make the width about 325
        letsGoTextField.setHeight(50);
        letsGoTextField.setAlignment(Align.center);
        letsGoTextField.setFocusTraversal(false);
        letsGoTextField.setDisabled(true);

        TextField signUpTextField = new TextField(("SIGN UP"), Assets.tfsTransWhite40);
        signUpTextField.setPosition(312, 27);
        signUpTextField.setWidth(175);
        signUpTextField.setHeight(50);
        signUpTextField.setAlignment(Align.center);
        signUpTextField.setFocusTraversal(false);
        signUpTextField.setDisabled(true);


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
        passwordTextField.setPasswordMode(false);
        //passwordTextField.setPasswordCharacter('*');
        passwordTextField.setWidth(300);
        passwordTextField.setHeight(50);
        passwordTextField.setFocusTraversal(false);
        passwordTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (passwordTextField.getText().equals("PASSWORD"))
                    passwordTextField.setPasswordMode(true);
                    passwordTextField.setPasswordCharacter('*');
                    passwordTextField.setText("");

            }
        });
        passwordTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });

        menuTextFields.put("usernameTF", usernameTextField);
        menuTextFields.put("passwordTF", passwordTextField);
        menuTextFields.put("letsGoTF", letsGoTextField);
        menuTextFields.put("signUpTF", signUpTextField);
        menuTextFields.put("loginTF", logInTextField);


        menuNumber = this.MENU_SIGNIN;
    }

    public void signUp(){
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        menuComponents.add(0, new MenuComponent(600, 100, 175, 75, Assets.menuButtonSmallRegion));//submit
        menuComponents.add(1, new MenuComponent(400, 100, 175, 75, Assets.menuButtonSmallRegion));//return

        //right now I am using the metric of setting the width to be 175 and height 50 (for textfields)
        //and making them 88 units below the buttons and 73 units below. thats maybe like 1 or 2 units higher than it needs to be, but it looks aight

        TextField signUpTextField = new TextField(("SIGN UP!"), Assets.tfsTrans100);
        signUpTextField.setPosition(50, 330);
        signUpTextField.setWidth(700);
        signUpTextField.setHeight(150);
        signUpTextField.setAlignment(Align.center);
        signUpTextField.setFocusTraversal(false);
        signUpTextField.setDisabled(true);

        TextField returnTextField = new TextField(("RETURN"), Assets.tfsTransWhite40);
        returnTextField.setPosition(312, 27);
        returnTextField.setWidth(175);//to be centered well make the width about 325
        returnTextField.setHeight(50);
        returnTextField.setAlignment(Align.center);
        returnTextField.setFocusTraversal(false);
        returnTextField.setDisabled(true);

        TextField submitTextField = new TextField(("SUBMIT"), Assets.tfsTransWhite40);
        submitTextField.setPosition(512, 27);
        submitTextField.setWidth(175);
        submitTextField.setHeight(50);
        submitTextField.setAlignment(Align.center);
        submitTextField.setFocusTraversal(false);
        submitTextField.setDisabled(true);


        final TextField usernameTextField = new TextField("USERNAME", Assets.tfs);
        usernameTextField.setPosition(100, 300);
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
        passwordTextField.setPosition(100, 240);
        passwordTextField.setPasswordMode(false);
        //passwordTextField.setPasswordCharacter('*');
        passwordTextField.setWidth(300);
        passwordTextField.setHeight(50);
        passwordTextField.setFocusTraversal(false);
        passwordTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (passwordTextField.getText().equals("PASSWORD"))
                    passwordTextField.setPasswordMode(true);
                    passwordTextField.setPasswordCharacter('*');
                    passwordTextField.setText("");

            }
        });
        passwordTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });

        final TextField emailTextField = new TextField("EMAIL", Assets.tfs);
        emailTextField.setPosition(450, 300);
        emailTextField.setWidth(300);
        emailTextField.setHeight(50);
        emailTextField.setFocusTraversal(false);
        emailTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (emailTextField.getText().equals("EMAIL"))
                    emailTextField.setText("");

            }
        });
        emailTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });

        final TextField charityTextField = new TextField("CHARITY", Assets.tfs);//obviously want to replace these with a real charity selection
        charityTextField.setPosition(450, 240);
        charityTextField.setWidth(300);
        charityTextField.setHeight(50);
        charityTextField.setFocusTraversal(false);
        charityTextField.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (charityTextField.getText().equals("CHARITY"))
                    charityTextField.setText("");

            }
        });
        charityTextField.setTextFieldListener(new TextField.TextFieldListener() {//if enter is pressed, remove the keyboard
            @Override
            public void keyTyped(TextField textField, char c) {
                if ((c == '\r') || (c == '\n')) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    unfocusAll = true;
                }
            }
        });



        /*TextField usernameTextField = new TextField("", Assets.tfs);
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
        charityTextField.setFocusTraversal(false);*/

        menuTextFields.put("usernameTF", usernameTextField);
        menuTextFields.put("passwordTF", passwordTextField);
        menuTextFields.put("emailTF", emailTextField);
        menuTextFields.put("charityTF", charityTextField);
        menuTextFields.put("returnTF", returnTextField);
        menuTextFields.put("submitTF", submitTextField);
        menuTextFields.put("signUpTF", signUpTextField);


        menuNumber = this.MENU_SIGNUP;
    }

    public void welcome(String username, int wins, int losses, String charityName, int charityIcon)
    {
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();

        TextField usernameTextField = new TextField(("WELCOME " + username.toUpperCase() + "!"), Assets.tfsBigBlue70);
        usernameTextField.setPosition(50, 290);
        usernameTextField.setWidth(700);
        usernameTextField.setHeight(50);
        usernameTextField.setAlignment(Align.center);
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
        menuComponents.add(0, new MenuComponent(200, 350, 175, 75, Assets.menuButtonSmallRegion));//play button
        menuComponents.add(1, new MenuComponent(200, 250, 175, 75, Assets.menuButtonSmallRegion));//options button
        menuComponents.add(2, new MenuComponent(200, 150, 175, 75, Assets.menuButtonSmallRegion));//profile button

        //right now I am using the metric of setting the width to be 175 and height 50 (for textfields)
        //and making them 88 units below the buttons and 73 units below. thats maybe like 1 or 2 units higher than it needs to be, but it looks aight

        TextField playTextField = new TextField(("PLAY!"), Assets.tfsTransWhite40);
        playTextField.setPosition(112, 277);
        playTextField.setWidth(175);//to be centered well make the width about 325
        playTextField.setHeight(50);
        playTextField.setAlignment(Align.center);
        playTextField.setFocusTraversal(false);
        playTextField.setDisabled(true);

        TextField optionsTextField = new TextField(("OPTIONS"), Assets.tfsTransWhite40);
        optionsTextField.setPosition(112, 177);
        optionsTextField.setWidth(175);
        optionsTextField.setHeight(50);
        optionsTextField.setAlignment(Align.center);
        optionsTextField.setFocusTraversal(false);
        optionsTextField.setDisabled(true);

        TextField profileTextField = new TextField(("PROFILE"), Assets.tfsTransWhite40);
        profileTextField.setPosition(112, 77);
        profileTextField.setWidth(175);
        profileTextField.setHeight(50);
        profileTextField.setAlignment(Align.center);
        profileTextField.setFocusTraversal(false);
        profileTextField.setDisabled(true);

        TextField charityChampsTextField = new TextField(("CHARITY CHAMPS"), Assets.tfsTrans100);
        charityChampsTextField.setPosition(50, 390);
        charityChampsTextField.setWidth(700);
        charityChampsTextField.setHeight(50);
        charityChampsTextField.setAlignment(Align.center);
        charityChampsTextField.setFocusTraversal(false);
        charityChampsTextField.setDisabled(true);

        menuTextFields.put("playTF", playTextField);
        menuTextFields.put("optionsTF", optionsTextField);
        menuTextFields.put("profileTF", profileTextField);
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
        menuComponents.add(0, new MenuComponent(600, 100, 175, 75, Assets.menuButtonSmallRegion));//return
        menuComponents.add(1, new MenuComponent(250, 350, 175, 75, Assets.menuButtonSmallRegion));//player
        menuComponents.add(2, new MenuComponent(250, 250, 175, 75, Assets.menuButtonSmallRegion));//audio
        menuComponents.add(3, new MenuComponent(250, 150, 175, 75, Assets.menuButtonSmallRegion));//privacy

        //right now I am using the metric of setting the width to be 175 and height 50 (for textfields)
        //and making them 88 units below the buttons and 73 units below. thats maybe like 1 or 2 units higher than it needs to be, but it looks aight

        TextField playerTextField = new TextField(("PLAYER"), Assets.tfsTransWhite40);
        playerTextField.setPosition(162, 277);
        playerTextField.setWidth(175);//to be centered well make the width about 325
        playerTextField.setHeight(50);
        playerTextField.setAlignment(Align.center);
        playerTextField.setFocusTraversal(false);
        playerTextField.setDisabled(true);

        TextField audioTextField = new TextField(("AUDIO"), Assets.tfsTransWhite40);
        audioTextField.setPosition(162, 177);
        audioTextField.setWidth(175);
        audioTextField.setHeight(50);
        audioTextField.setAlignment(Align.center);
        audioTextField.setFocusTraversal(false);
        audioTextField.setDisabled(true);

        TextField privacyTextField = new TextField(("PRIVACY"), Assets.tfsTransWhite40);
        privacyTextField.setPosition(162, 77);
        privacyTextField.setWidth(175);
        privacyTextField.setHeight(50);
        privacyTextField.setAlignment(Align.center);
        privacyTextField.setFocusTraversal(false);
        privacyTextField.setDisabled(true);

        TextField returnTextField = new TextField(("RETURN"), Assets.tfsTransWhite40);
        returnTextField.setPosition(512, 27);
        returnTextField.setWidth(175);//to be centered well make the width about 325
        returnTextField.setHeight(50);
        returnTextField.setAlignment(Align.center);
        returnTextField.setFocusTraversal(false);
        returnTextField.setDisabled(true);

        TextField charityChampsTextField = new TextField(("OPTIONS"), Assets.tfsTrans100);
        charityChampsTextField.setPosition(50, 390);
        charityChampsTextField.setWidth(700);
        charityChampsTextField.setHeight(50);
        charityChampsTextField.setAlignment(Align.center);
        charityChampsTextField.setFocusTraversal(false);
        charityChampsTextField.setDisabled(true);

        menuTextFields.put("playTF", playerTextField);
        menuTextFields.put("optionsTF", audioTextField);
        menuTextFields.put("privacyTF", privacyTextField);
        menuTextFields.put("returnTF", returnTextField);
        menuTextFields.put("charityChampsTF", charityChampsTextField);

    }

    public void gameOver(boolean won){
        shouldClear = true;
        menu = Assets.menuSplashBlankRegion;
        menuComponents = new ArrayList<MenuComponent>();
        menuTextFields = new HashMap<String, TextField>();
        menuNumber = this.MENU_GAMEOVER;
        this.isSplash = false;

        TextField resultsTextField = new TextField(("YOU LOSE"), Assets.tfsTrans100);
        resultsTextField.setPosition(50, 190);
        resultsTextField.setWidth(700);
        resultsTextField.setHeight(150);
        resultsTextField.setAlignment(Align.center);
        resultsTextField.setFocusTraversal(false);
        resultsTextField.setDisabled(true);

        if (won)
        {
            resultsTextField.setText("YOU WIN!");
        }


        menuTextFields.put("resultsTF", resultsTextField);


    }

    public void update(float deltaTime){

    }


}