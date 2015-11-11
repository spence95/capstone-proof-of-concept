package com.werbenjagermanjensenstudios.charitychamps;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;

import org.json.JSONArray;
import org.json.JSONObject;

public class World {

    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 480;
    public static final float MENU_WIDTH = 392 / 2;
    public static final float MENU_HEIGHT = 127 / 2;
    public static final float Move_menu_width = 122 / 2;
    public static final float Move_menu_height = 127 / 2;
    public static final float Attack_menu_width = 150 / 2;
    public static final float Attack_menu_height = 127 / 2;
    public final float squareWidth = 12;
    public final float squareHeight = 24;
    //origin start points
    //(20, 40)
    //(20, 440)
    //(780, 40)
    //(780, 440)
    public ArrayList<Player> players;
    public ArrayList<Block> blocks;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bullet> bullets;
    public ArrayList<Powerup> powerups;
    boolean hasStarted = false;
    boolean isSetting = true;
    boolean readyToEndRound = true;
    boolean isSettingGameOverScreen = false;
    boolean gameWon = false;
    float settingGameOverCounter = 0;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;
    public Player currentPlayer;
    public Dot dot;

    //TODO: Get rid of ActionMenu
    public ActionMenu actionMenu;

    public ActionButton moveActionButton;
    public ActionButton attackActionButton;

    public CollisionManager collisionManager;
    public ScreenController screenController;
    proofOfConcept game;

    public HashMap<Integer, String> playersByID;
    public HashMap<Integer, TextField> playerLabels;

    int turnNumber;
    int matchID;
    public ArrayList<Integer> turnIDs;

    ApiCall api;

    //used to let losers and winners see after match for a second before end game screen
    float afterDeathCounter;

    //used to prevent lagging once actions are received
    float roundWaitingCounter = 0;

    public static int roundWaitingAmount = 1;

    //define callback interface
    interface playerActionRetrievalCallback {

        void setPlayersForRunning(String actionResults, String playerUrl, int index);
    }

    public World(proofOfConcept game, int matchID) {
        this.game = game;
        this.matchID = matchID;
        players = new ArrayList<Player>();
        blocks = new ArrayList<Block>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        powerups = new ArrayList<Powerup>();
        actionMenu = new ActionMenu();

        moveActionButton = new ActionButton(ActionButton.moveName, true, 20, 100, 40, 40);
        attackActionButton = new ActionButton(ActionButton.attackName, false, 20, 150, 40, 40);

        dot = new Dot(-1000, -1000);
        collisionManager = new CollisionManager(this);
        turnNumber = 0;
        afterDeathCounter = 0;
        api = new ApiCall(new playerActionRetrievalCallback() {
            @Override
            public void setPlayersForRunning(String actionResults, String playerUrl, int index) {
                SetPlayersForRunning(actionResults, playerUrl, index);
            }
        });
        //place blocks on arena
        placeBlocks();
        placePowerups();
        turnIDs = new ArrayList<Integer>();
        playersByID = new HashMap<Integer, String>();
        playerLabels = new HashMap<Integer, TextField>();
    }
    //mocked out with specific placements for blocks (no pseudo-randomness)
    public void placeBlocks(){
        placeOutsideWalls();
        Block block = new Block(350 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 250, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 260, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 270, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 230, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(360 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(370 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(380 + 20, 280, 10, 10);
        blocks.add(block);


        block = new Block(450 + 20, 250, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 260, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 270, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 230, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(440 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(430 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(420 + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(200  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(210  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(220  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(230  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(240  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(600  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(590  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(580  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(570  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(560  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(40  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(50  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(60  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(70  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(80  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(770  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(760  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(750  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(740  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(730  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(400  + 20, 10, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 20, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 30, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 40, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 50, 10, 10);
        blocks.add(block);

        block = new Block(400  + 20, 470, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 460, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 450, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 440, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 430, 10, 10);
        blocks.add(block);

    }

    private void placeOutsideWalls(){
        Block block = new Block((WORLD_WIDTH / 2)+48, 0, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block((WORLD_WIDTH / 2)+48, WORLD_HEIGHT, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block(48, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
        block = new Block(WORLD_WIDTH, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
    }

    private void placePowerups(){
        HeartPowerup hp = new HeartPowerup(400 + 20, 250, 25, 25);
        this.powerups.add(hp);
    }

    public void createScreenController(GameScreen gs){
        screenController = new ScreenController(game, gs);
    }

    public void addToTurnIDs(int id){
        turnIDs.add(id);
    }

    public ActionButton getActiveButton(){
        if(moveActionButton.isActive){
            return moveActionButton;
        } else {
            return attackActionButton;
        }
    }

    public void touched(float x, float y){
        if(moveActionButton.bounds.contains(x, y)){
            moveActionButton.toggleActive();
            attackActionButton.toggleActive();
        }
        else if(attackActionButton.bounds.contains(x, y)){
            attackActionButton.toggleActive();
            moveActionButton.toggleActive();
        } else {
            bringUpMenu(x, y);
            if(!currentPlayer.isDone) {
                ActionButton activeButton = getActiveButton();
                System.out.println("ACTIVE BUTTON: ");
                System.out.println(activeButton.getActionName());
                if (activeButton.getActionName() == ActionButton.moveName) {
                    moveClicked(lastTouchedX, lastTouchedY);
                    //moveClicked(x, y);
                } else {
                    attackClicked(lastTouchedX, lastTouchedY);
                    //attackClicked(x, y);
                }
            }
        }
    }

    private void bringUpMenu(float x, float y){
        //only bring up when player isn't moving already
        if(this.currentPlayer.velocity != null) {
            if (this.currentPlayer.velocity.x == 0 && this.currentPlayer.velocity.y == 0) {
                lastTouchedX = x;
                lastTouchedY = y;
//                dot.position.x = x;
//                dot.position.y = y;
                //if hidden
//                if (this.actionMenu.state == 0 && this.actionMenu.position.y < 0) {
//                    this.actionMenu.changeState(1);
//                    this.actionMenu.isShown = true;
//                }
            }
        }
    }

    public void moveClicked(float x, float y){
        //if player is in middle of move update that move's ending to current position
        if(!this.currentPlayer.isMoving) {
//            this.currentPlayer.stop();
//            this.currentPlayer.actions.get(this.currentPlayer.turnCounter).x = this.currentPlayer.position.x;
//            this.currentPlayer.actions.get(this.currentPlayer.turnCounter).y = this.currentPlayer.position.y;

            this.currentPlayer.addMove(x, y);
            this.actionMenu.changeState(2);
            this.actionMenu.isShown = false;
        }
    }

    public void attackClicked(float x, float y) {
        if(this.currentPlayer.bullet.isShot == false) {
            Attack at = new Attack(x, y, 9);
            this.currentPlayer.addAttack(at);
            this.currentPlayer.bullet.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
            this.actionMenu.changeState(2);
            this.actionMenu.isShown = false;
        }
    }

    public void hideDot(){
        this.dot.position.x = 1000;
        this.dot.position.y = 1000;
    }

    public void getPlayerDetails() {
        String URL = "http://li1081-187.members.linode.com/api/v1/turn/?match=" + this.matchID + "&turnnumber=0&format=json";
        String getTurns = api.httpGet(URL, 0);
        //loop through the players to build the playersByID<int, string> hashmap
        //String turnResults = api.httpGet(url, 0);
        JSONObject turnJson = new JSONObject(getTurns);
        JSONArray turnArray = turnJson.getJSONArray("objects");
        ArrayList<Integer> playerIDs = new ArrayList<Integer>();
        for (int i = 0; i < turnArray.length(); i++) {
            JSONObject turnObj = turnArray.getJSONObject(i);

            String[] tokens = turnObj.getString("player").split("/");
            int lastPlace = tokens.length - 1;
            int playerID = Integer.parseInt(tokens[lastPlace]);

            playerIDs.add(playerID);
        }//now it should have all the playerIDs in the temporary ArrayList

        for (int playerID : playerIDs) {
            URL = "http://li1081-187.members.linode.com/api/v1/player/" + playerID + "/?format=json";
            String getPlayer = api.httpGet(URL, 0);
            JSONObject json = new JSONObject(getPlayer);
            String username = json.getString("username");
            playersByID.put(playerID, username);
            TextField usernameTextField = new TextField(username, Assets.tfs12);
            usernameTextField.setPosition(0, 0);
            usernameTextField.setWidth(100);
            usernameTextField.setHeight(30);
            usernameTextField.setFocusTraversal(false);
            usernameTextField.setDisabled(true);
            playerLabels.put(playerID,usernameTextField);
        }

//        for (int i : playersByID.keySet()) {
//            System.out.println(i + ": " + playersByID.get(i));
//        }


    }

    public void start(){
        this.getPlayerDetails();
        currentPlayer = new Player(game.getPlayerID(), squareWidth, squareHeight, false);
        Player enemy1 = new Player(-1, squareWidth, squareHeight, true);
//        Player enemy2 = new Player(2, squareWidth, squareHeight, true);
//        Player enemy3 = new Player(3, squareWidth, squareHeight, true);
        players.add(currentPlayer);
        players.add(enemy1);
//        players.add(enemy2);
//        players.add(enemy3);
    }

    public void spawnPlayers(){
        //TODO: run through players spawn actions
        for(int i = 0; i < players.size(); i++){
            players.get(i).spawn(players.get(i).actions.get(0).x, players.get(i).actions.get(0).y);
            players.get(i).actions.clear();
        }

        //TODO: post new turn
        postNewTurn();
    }

    public Player getNextEnemyPlayerWithoutID(){
        for(int i = 0; i < players.size(); i++){
            Player pl = players.get(i);
            //negative number means not set
            if(pl.id < 0){
                return pl;
            }
        }
        return currentPlayer;
    }

    public void update(float deltaTime){
     //update collisions
     collisionManager.updateCollisions();

     //update dot
     if(dot != null){
         dot.update(deltaTime);
     }

     //update bullets
     for(int i = 0; i < players.size(); i++){
         players.get(i).bullet.update(deltaTime);
     }

     //update action menu
     if(actionMenu != null){
         actionMenu.update(deltaTime);
     }

     if(isSetting){
         for(int i = 0; i < this.players.size(); i++){
             this.players.get(i).isImmune = false;
         }
            this.currentPlayer.updateSetting(deltaTime);

            if(this.currentPlayer.isDone){
                submit();
            }
        }

        else{
         boolean bulletsAreOut = false;
         if(roundWaitingCounter > roundWaitingAmount) {
             int doneCounter = 0;
             ArrayList<Integer> deadPlayerIds = new ArrayList<Integer>();
             //run all players at once
             for (int i = 0; i < this.players.size(); i++) {
                 Player pl = players.get(i);

                 if(pl.isImmune){
                     if(pl.immuneCounter < Player.immuneCounterLimit){
                         pl.immuneCounter += deltaTime;
                     } else{
                         pl.isImmune = false;
                         pl.immuneCounter = 0;
                     }
                 }

                 if (pl.isDone) {
                     doneCounter++;
                 }

                 pl.updateRunning(deltaTime);
                 //check if all players are done and start a new round
                 if (doneCounter >= players.size()) {
                     if (readyToEndRound)
                         newRound();
                 }

                 if(pl.bullet.isShot){
                     bulletsAreOut = true;
                 }

                 //check for dead players and remove them
                 if (pl.dead) {
                     deadPlayerIds.add(pl.id);
                     if (pl.id == currentPlayer.id) {
                         //oh dang, u just got Rekt
                         isSettingGameOverScreen = true;
                         //setGameOverScreen(false);
                     }
                 }

                 //check if player has won
                 if (deadPlayerIds.size() == players.size() - 1) {
                     if (currentPlayer.dead == false) {
                         //oh dang, u just won
                         isSettingGameOverScreen = true;
                         gameWon = true;
                         //setGameOverScreen(true);
                     }
                 }


             }
             //safely remove dead players from list
             removeDeadPlayers(deadPlayerIds);
         } else {
             roundWaitingCounter += deltaTime;
         }
         if(explosions.size() < 1){
             if(!bulletsAreOut) {
                 readyToEndRound = true;
             }
         }
     }

        System.out.println(readyToEndRound);
        updateExplosions(deltaTime);
        updatePowerups(deltaTime);

        if(isSettingGameOverScreen){
            if(settingGameOverCounter > 2){
                setGameOverScreen(gameWon);
            } else{
                settingGameOverCounter += deltaTime;
            }
        }
    }

    private void removeDeadPlayers(ArrayList<Integer> deadPlayerIds){
        for(int i = 0; i < deadPlayerIds.size(); i++){
            for(int p = 0; p < players.size(); p++){
                Player pl = players.get(p);
                if(pl.id == deadPlayerIds.get(i)){
                    players.remove(p);
                }
            }
        }
    }



    private void setGameOverScreen(boolean won){
        //screenController.setGameOverScreen(true);
        ArrayList<Integer> playerIDs = new ArrayList<Integer>();
        ArrayList<String> playerUsernames = new ArrayList<String>();
        for (Player p : players)
        {
            playerIDs.add(p.id);
            playerUsernames.add(playerLabels.get(p.id).getText());
        }

        MenuScreen menu = new MenuScreen(game);
        menu.menu.gameOver(won, playerIDs, playerUsernames);
        game.setScreen(menu);

        turnNumber = 0;
    }

    //executed after one round has ran (not setting)
    public void newRound(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).forgetActions();
        }
        currentPlayer.setLastPosition(currentPlayer.position.x, currentPlayer.position.y);
        isSetting = true;
        actionMenu.isReadyToSubmit = false;
    }

    public void updateExplosions(float deltaTime){
        for(int i = 0; i < explosions.size(); i++){
            readyToEndRound = false;
            Explosion exp = explosions.get(i);
            if(!exp.getIsDone()) {
                exp.update(deltaTime);
            } else{
                explosions.remove(i);
            }
        }


    }

    public void updatePowerups(float deltaTime) {
        for(int i = 0; i < powerups.size(); i++){
            if(powerups.get(i).isDone){
                powerups.remove(i);
            }
        }
    }

    public void bulletShoot(){

    }

    public void submit(){
        hideDot();
        roundWaitingCounter = 0;
        this.actionMenu.changeState(2);
        generateTurnJson();
        //newRound();
        this.currentPlayer.resetActions();
        //reset current player
        currentPlayer.position.x = currentPlayer.xLast;
        currentPlayer.position.y = currentPlayer.yLast;
        explosions.clear();
    }

    public Player getPlayerById(int id){
        for(int p = 0; p < players.size(); p++){
            if(players.get(p).id == id)
                return players.get(p);
        }
        return null;
    }

    public void generateTurnJson(){
        String actionsJson = "{\"objects\":[";
        for(int i = 0; i < currentPlayer.actions.size(); i++) {
            Action ac = currentPlayer.actions.get(i);
            int actiontype = 1;
            if(ac instanceof Spawn){
                actiontype = 0;
            } else if(ac instanceof Attack) {
                actiontype = 2;
            }
            int originx = 0;
            int originy = 0;
            if(i == 0){
                originx = (int)(100 * round(currentPlayer.xLast, 2));
                originy = (int)(100 * round(currentPlayer.yLast, 2));
            }

            if(ac instanceof Move) {
                originx = (int) (100 * round(ac.x, 2));
                originy = (int) (100 * round(ac.y, 2));
            }

            int targetx = (int) (100 * round(ac.x, 2));
            int targety = (int) (100 * round(ac.y, 2));

            //int turnId = turnIDs.get(turnIDs.size() - 1);
            int turnId = currentPlayer.currentTurnId;

            String action = "{ \"actionnumber\":" + i + "," +
                    "\"actiontype\":" + actiontype + "," +
                    "\"originx\":" + originx + "," +
                    "\"originy\":" + originy + "," +
                    "\"targetx\":" + targetx + "," +
                    "\"targety\":" + targety + "," +
                    "\"timetaken\":0" + "," +
                    "\"turn\": \"/api/v1/turn/" + turnId + "/\"},";

            actionsJson += action;
        }
        if(currentPlayer.actions.size() > 0) {
            actionsJson = actionsJson.substring(0, actionsJson.length() - 1);
        }
        actionsJson += "]}";
        //post turn, needs match, player, and turnnumber

        //post actions to turn id in world
        String patchResults = api.httpPostPutOrPatch("http://45.33.62.187/api/v1/action/?format=json", actionsJson, 0, true, false);

        //put received to turn
        String putReceivedURL = "http://45.33.62.187/api/v1/turn/" + currentPlayer.currentTurnId + "/?format=json";
        String json =  "{" +
                "\"received\":\"true\"" +
                "}" ;
        String receivedResults = api.httpPostPutOrPatch(putReceivedURL, json, 0, false, true);
        String url = "http://45.33.62.187/api/v1/turn/?match=" + matchID + "&turnnumber=" + turnNumber + "&format=json";
        String turnResults = api.httpGet(url, 0);
        JSONObject turnJson = new JSONObject(turnResults);
        JSONArray turnArray = turnJson.getJSONArray("objects");
        for(int i = 0; i < turnArray.length(); i++) {
            JSONObject turnObj = turnArray.getJSONObject(i);
//            int[] tempTurnIds = new int[4];
            String playerUrl = turnObj.getString("player");
            String[] tokens = playerUrl.split("/");
            int lastPlace = tokens.length - 1;
            int playerID = Integer.parseInt(tokens[lastPlace]);
            if (playerID != currentPlayer.id) {
                Player pl = getPlayerById(playerID);
                pl.currentTurnId = turnObj.getInt("id");
            }
        }
        game.setScreen(new LobbyScreen(game, this));
    }



    public void runPlayers(){
        WorldJSONHandler.runPlayers(this);
    }

    public void postNewTurn(){
        WorldJSONHandler.postNewTurn(this);
    }

    public void SetPlayersForRunning(String actionResults, String playerUrl, int index){
        WorldJSONHandler.SetPlayersForRunning(actionResults, playerUrl, index, this);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}