package com.werbenjagermanjensenstudios.charitychamps;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;
import com.werbenjagermanjensenstudios.charitychamps.actions.Action;
import com.werbenjagermanjensenstudios.charitychamps.actions.Attack;
import com.werbenjagermanjensenstudios.charitychamps.actions.Move;
import com.werbenjagermanjensenstudios.charitychamps.actions.Spawn;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Block;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Bullet;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.CrumblingBlock;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Dot;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Explosion;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.HeartPowerup;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Mine;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Player;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Powerup;

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
    public final float squareHeight = 13.5f;
    //origin start points
    //(20, 40)
    //(20, 440)
    //(780, 40)
    //(780, 440)
    public ArrayList<Player> players;
    public ArrayList<Block> blocks;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bullet> bullets;
    public ArrayList<Mine> mines;
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
    public ActionButton mineActionButton;

    public CollisionManager collisionManager;
    public ScreenController screenController;
    proofOfConcept game;

    public HashMap<Integer, String> playersByID;
    public HashMap<Integer, TextField> playerLabels;

    int turnNumber;
    int matchID;
    public ArrayList<Integer> turnIDs;

    ApiCall api;

    MapManager mm;

    //used to let losers and winners see after match for a second before end game screen
    float afterDeathCounter;

    //used to prevent lagging once actions are received
    float roundWaitingCounter = 0;

    //used to know how long to wait in between bullets
    //when negative one means not needed, set to 0 when needed
    float attackSecondsWaiting = -1;

    public static int roundWaitingAmount = 1;

    TextField messageText;


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
        mines = new ArrayList<Mine>();
        powerups = new ArrayList<Powerup>();
        actionMenu = new ActionMenu();

        moveActionButton = new ActionButton(ActionButton.moveName, true, 20, 100, 40, 40);
        attackActionButton = new ActionButton(ActionButton.attackName, false, 20, 150, 40, 40);

        dot = new Dot(-1000, -1000);
        collisionManager = new CollisionManager(this);
        mm = new MapManager(this, 51/2);
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
        float offset = 51 / 2;
        placeOutsideWalls();

        Block block = new Block(350 + offset, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 250, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 260, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 270, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 280, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 230, 10, 10);
        blocks.add(block);
        block = new Block(350 + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(360 + offset, 280, 10, 10);
        blocks.add(block);
        block = new Block(370 + offset, 280, 10, 10);
        blocks.add(block);
        block = new Block(380 + offset, 280, 10, 10);
        blocks.add(block);


        block = new Block(450 + offset, 250, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 260, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 270, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 280, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 240, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 230, 10, 10);
        blocks.add(block);
        block = new Block(450 + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(440 + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(430 + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(420 + offset, 220, 10, 10);
        blocks.add(block);

        block = new Block(200  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(210  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(220  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(230  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(240  + offset, 220, 10, 10);
        blocks.add(block);

        block = new Block(600  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(590  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(580  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(570  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(560  + offset, 220, 10, 10);
        blocks.add(block);

        block = new Block(40  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(50  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(60  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(70  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(80  + offset, 220, 10, 10);
        blocks.add(block);

        block = new Block(770  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(760  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(750  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(740  + offset, 220, 10, 10);
        blocks.add(block);
        block = new Block(730  + offset, 220, 10, 10);
        blocks.add(block);

        block = new Block(400  + offset, 10, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 20, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 30, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 40, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 50, 10, 10);
        blocks.add(block);

        block = new Block(400  + offset, 470, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 460, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 450, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 440, 10, 10);
        blocks.add(block);
        block = new Block(400  + offset, 430, 10, 10);
        blocks.add(block);

        mm.setNineBlockSquare(200, 300);
        mm.setNineBlockSquare(200, 100);
        mm.setNineBlockSquare(400, 75);
        mm.setNineBlockSquare(500, 367);
        mm.setNineBlockSquare(50, 399);
        mm.setNineBlockSquare(725, 75);
        mm.setNineBlockSquare(675, 210);
        mm.setNineBlockSquare(396, 335);

        mm.setWall(200, 400, 5, true);
        mm.setWall(600, 100, 4, false);
        mm.setWall(350, 100, 7, false);
        mm.setWall(425, 400, 1, true);
        mm.setWall(600, 300, 13, true);
        mm.setWall(150, 175, 13, true);
        mm.setWall(515, 65, 8, true);
        mm.setWall(275, 320, 6, false);

    }

    private void randomizeBlock(float x, float y, float width, float height){
        Block bl;
        int Min = 1;
        int Max = 10;
        float rand = Min + (int)(Math.random() * ((Max - Min) + 1));
        if(rand % 2 == 0){
            bl = new CrumblingBlock(x, y, width, height);
        } else {
            bl = new CrumblingBlock(x, y, width, height);
        }
        blocks.add(bl);
    }

    private void placeOutsideWalls(){
        Block block = new Block((WORLD_WIDTH / 2)+51, 0, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block((WORLD_WIDTH / 2)+51, WORLD_HEIGHT, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block(51, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
        block = new Block(WORLD_WIDTH, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
    }

    private void placePowerups(){
        //HeartPowerup hp = new HeartPowerup(400 + 20, 250, 25, 25);
        //this.powerups.add(hp);
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
        if(isSetting) {
            if (moveActionButton.bounds.contains(x, y)) {
                moveActionButton.toggleActive();
                attackActionButton.toggleActive();
            } else if (attackActionButton.bounds.contains(x, y)) {
                attackActionButton.toggleActive();
                moveActionButton.toggleActive();
            } else if (x <= 51) {
                return;
            } else {
                bringUpMenu(x, y);
                if (!currentPlayer.isDone) {
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
    }

    private void bringUpMenu(float x, float y){
        //only bring up when player isn't moving already
        if(this.currentPlayer.velocity != null) {
            if (this.currentPlayer.velocity.x == 0 && this.currentPlayer.velocity.y == 0) {
                lastTouchedX = x;
                lastTouchedY = y;
                //dot.position.x = x;
                //dot.position.y = y;
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
            attackSecondsWaiting = 0;
        }
    }

    public void attackClicked(float x, float y) {
        //if first bullet in a series
        if(attackSecondsWaiting > .3f || attackSecondsWaiting == -1) {
            if(attackSecondsWaiting == -1){
                attackSecondsWaiting = 0;
            }
            Attack at = new Attack(x, y, attackSecondsWaiting);
            this.currentPlayer.addAttack(at);
            float bulletSpeed = 200;
            Bullet bu = new Bullet(this.currentPlayer.position.x, this.currentPlayer.position.y, bulletSpeed, this.currentPlayer.id);
            bu.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
            bullets.add(bu);
            attackSecondsWaiting = 0;
            System.out.println("attackSecondsWaiting reset");
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
            //playerLabels.put(playerID,usernameTextField);
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
     for(int i = 0; i < bullets.size(); i++){
         //players.get(i).bullet.update(deltaTime);
         bullets.get(i).update(deltaTime);

         //check if bullet out of arena bounds then remove it if it is
         if(bullets.get(i).position.x > 800 || bullets.get(i).position.y > 480 ||
                 bullets.get(i).position.x < 0 || bullets.get(i).position.y < 0){
             bullets.remove(i);
         }
     }

     //update action menu
     if(actionMenu != null){
         actionMenu.update(deltaTime);
     }

     if(isSetting){
         //update seconds waiting for actions
         if(attackActionButton.isActive) {
             System.out.println("attackSecondsWaiting: " + attackSecondsWaiting);
             attackSecondsWaiting += deltaTime;
         }

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
                         pl.immuneCounter += Gdx.graphics.getRawDeltaTime();
                     } else{
                         pl.isImmune = false;
                         pl.immuneCounter = 0;
                     }
                 }

                 if (pl.isDone) {
                     doneCounter++;
                 }

                 pl.updateRunning(deltaTime);

                 for(int b = 0; b < pl.bulletsShot.size(); b++){
                     Bullet bu = pl.bulletsShot.get(b);
                     bu.shoot(bu.runningModeDestinationX, bu.runningModeDestinationY,bu.position.x, bu.position.y);
                     bullets.add(bu);
                     pl.bulletsShot.remove(b);
                 }

                 if(bullets.size() > 0){
                     bulletsAreOut = true;
                 }

                 if(explosions.size() < 1){
                     if(!bulletsAreOut) {
                         readyToEndRound = true;
                     } else {
                         readyToEndRound = false;
                     }
                 } else {
                     readyToEndRound = false;
                 }

                 //check if all players are done and start a new round
                 if (doneCounter >= players.size()) {
                     if (readyToEndRound) {
                             newRound();
                     }
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
     }

        updateExplosions(deltaTime);
        updatePowerups(deltaTime);

        if(isSettingGameOverScreen){
            if(settingGameOverCounter > 2){
                setGameOverScreen(gameWon);
            } else{
                settingGameOverCounter += Gdx.graphics.getRawDeltaTime();
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
            //playerUsernames.add(playerLabels.get(p.id).getText());
        }

        MenuScreen menu = new MenuScreen(game);
        menu.menu.gameOver(won, playerIDs, playerUsernames);
        game.setScreen(menu);

        turnNumber = 0;
    }

    //executed after one round has ran (not setting)
    public void newRound(){

        //check if dead one last time
       if(currentPlayer.dead == true){
           setGameOverScreen(gameWon);
       }

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
        attackSecondsWaiting = 0;
        this.actionMenu.changeState(2);
        generateTurnJson();
        //newRound();
        this.currentPlayer.resetActions();
        //reset current player
        currentPlayer.position.x = currentPlayer.xLast;
        currentPlayer.position.y = currentPlayer.yLast;
        explosions = new ArrayList<Explosion>();
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
            float timetakenFloat = 100000 * ac.secondsToWait;
            int timetaken = (int) (round(timetakenFloat, 5));

            System.out.println("Calculating time taken: " + ac.secondsToWait + " to " + timetaken);

            //int turnId = turnIDs.get(turnIDs.size() - 1);
            int turnId = currentPlayer.currentTurnId;

            String action = "{ \"actionnumber\":" + i + "," +
                    "\"actiontype\":" + actiontype + "," +
                    "\"originx\":" + originx + "," +
                    "\"originy\":" + originy + "," +
                    "\"targetx\":" + targetx + "," +
                    "\"targety\":" + targety + "," +
                    "\"timetaken\":" + timetaken +  "," +
                    "\"turn\": \"/api/v1/turn/" + turnId + "/\"},";

            actionsJson += action;
        }

        System.out.println("TURN ID ______________________________");
        System.out.println(currentPlayer.currentTurnId);

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