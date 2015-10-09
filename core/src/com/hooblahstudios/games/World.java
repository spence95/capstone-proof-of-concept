package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.google.gson.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;
import com.hooblahstudios.games.json.ActionJsonTemplate;

public class World {

    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 480;
    public static final float MENU_WIDTH = 392 / 2;
    public static final float MENU_HEIGHT = 127 / 2;
    public static final float Move_menu_width = 122 / 2;
    public static final float Move_menu_height = 127 / 2;
    public static final float Attack_menu_width = 150 / 2;
    public static final float Attack_menu_height = 127 / 2;
    public final float squareWidth = 15;
    public final float squareHeight = 31;
    public ArrayList<Player> players;
    public ArrayList<Block> blocks;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bullet> bullets;
    boolean hasStarted = false;
    boolean isSetting = true;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;
    public Player currentPlayer;
    public Dot dot;
    public ActionMenu actionMenu;
    public CollisionManager collisionManager;
    public ScreenController screenController;

    int turn;

    ApiCall api;

    public World(proofOfConcept game) {
        players = new ArrayList<Player>();
        blocks = new ArrayList<Block>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        actionMenu = new ActionMenu();
        screenController = new ScreenController(game);
        dot = new Dot(-1000, -1000);
        collisionManager = new CollisionManager(this);
        turn = 1;
        api = new ApiCall();
        //place blocks on arena
        placeBlocks();
    }
    //mocked out with specific placements for blocks (no pseudo-randomness)
    public void placeBlocks(){
        placeOutsideWalls();
        Block block = new Block(125, 75, 100, 5);
        blocks.add(block);
        block = new Block(78, 100, 5, 50);
        blocks.add(block);

        block = new Block(800-125, 75, 100, 5);
        blocks.add(block);
        block = new Block(800-77, 100, 5, 50);
        blocks.add(block);

        block = new Block(125, 480-75, 100, 5);
        blocks.add(block);
        block = new Block(78, 480 - 100, 5, 50);
        blocks.add(block);

        block = new Block(800-125, 480-75, 100, 5);
        blocks.add(block);
        block = new Block(800-77, 480-100, 5, 50);
        blocks.add(block);

        block = new Block(400, 240, 50, 50);
        blocks.add(block);
    }

    private void placeOutsideWalls(){
        Block block = new Block(400, 0, 800, 10);
        blocks.add(block);
        block = new Block(400, 480, 800, 10);
        blocks.add(block);
        block = new Block(0, 240, 10, WORLD_HEIGHT);
        blocks.add(block);
        block = new Block(WORLD_WIDTH, 240, 10, WORLD_HEIGHT);
        blocks.add(block);
    }

    public void touched(float x, float y){
        //render actions menu
            //check if click in menu bounds
            if(actionMenu != null) {
                if (actionMenu.menuBounds.contains(x, y) && actionMenu.isShown) {
                    if(actionMenu.isReadyToSubmit){
                        submit();
                        return;
                    }
                    if(!currentPlayer.isDone) {
                        if (x > 439) {
                            moveClicked(lastTouchedX, lastTouchedY);
                        } else {
                            attackClicked(lastTouchedX, lastTouchedY);
                        }
                    }
                } else {
                    bringUpMenu(x, y);
                }
            }
    }

    private void bringUpMenu(float x, float y){
        //only bring up when player isn't moving already
        if(this.currentPlayer.velocity != null) {
            if (this.currentPlayer.velocity.x == 0 && this.currentPlayer.velocity.y == 0) {
                lastTouchedX = x;
                lastTouchedY = y;
                dot.position.x = x;
                dot.position.y = y;
                //if hidden
                if (this.actionMenu.state == 0 && this.actionMenu.position.y < 0) {
                    this.actionMenu.changeState(1);
                    this.actionMenu.isShown = true;
                }
            }
        }
    }

    public void moveClicked(float x, float y){
        hideDot();
        this.currentPlayer.addMove(x, y);
        this.actionMenu.changeState(2);
        this.actionMenu.isShown = false;
    }

    public void attackClicked(float x, float y) {
        Attack at = new Attack(x, y, 9);
        hideDot();
        this.currentPlayer.addAttack(at);
        this.currentPlayer.bullet.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
        this.actionMenu.changeState(2);
        this.actionMenu.isShown = false;
    }

    public void hideDot(){
        this.dot.position.x = 1000;
        this.dot.position.y = 1000;
    }

    public void start(){
        currentPlayer = new Player(0, 100, 100, squareWidth, squareHeight, false);
        Player enemy1 = new Player(1, WORLD_WIDTH - 100, WORLD_HEIGHT - 100, squareWidth, squareHeight, true);
        Player enemy2 = new Player(2, 100, WORLD_HEIGHT - 100, squareWidth, squareHeight, true);
        Player enemy3 = new Player(3, WORLD_WIDTH - 100, 100, squareWidth, squareHeight, true);
        players.add(currentPlayer);
        players.add(enemy1);
        players.add(enemy2);
        players.add(enemy3);
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
            this.currentPlayer.updateSetting(deltaTime);
            if(this.currentPlayer.isDone){
                actionMenu.makeReadyToSubmit();
            }
        }
        else{
         int doneCounter = 0;
         ArrayList<Integer> deadPlayerIds = new ArrayList<Integer>();
         //run all players at once
            for (int i = 0; i < this.players.size(); i++) {
                Player pl = players.get(i);
                if (pl.isDone) {
                    doneCounter++;
                }

                pl.updateRunning(deltaTime);
                //check if all players are done and start a new round
                if (doneCounter >= players.size()) {
                    newRound(deltaTime);
                }

                //check for dead players and remove them
                if (pl.dead) {
                    deadPlayerIds.add(pl.id);
                    if(pl.id == currentPlayer.id){
                        //oh dang, u just got Rekt
                        getRekt();
                    }
                }
            }
         //safely remove dead players from list
         removeDeadPlayers(deadPlayerIds);
     }

        updateExplosions(deltaTime);
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

    private void getRekt(){
        screenController.setGameOverScreen();
        turn = 0;
    }

    public void newRound(float deltaTime){
        turn++;
        for(int i = 0; i < players.size(); i++){
            players.get(i).forgetActions();
        }
        currentPlayer.setLastPosition(currentPlayer.position.x, currentPlayer.position.y);
        isSetting = true;
        actionMenu.isReadyToSubmit = false;

    }

    public void updateExplosions(float deltaTime){
        for(int i = 0; i < explosions.size(); i++){
            Explosion exp = explosions.get(i);
            if(!exp.getIsDone()) {
                exp.update(deltaTime);
            } else{
                explosions.remove(i);
            }
        }
    }

    public void bulletShoot(){

    }

    public void submit(){
        hideDot();
        this.actionMenu.changeState(2);
        generateTurnJson();
        this.currentPlayer.resetActions();
        this.isSetting = false;
        //reset current player
        currentPlayer.position.x = currentPlayer.xLast;
        currentPlayer.position.y = currentPlayer.yLast;
        getEnemyActions();
    }

    public void generateTurnJson(){
        int xLast = 0;
        int yLast = 0;
        String URL = "http://45.33.62.187/api/v1/turn/?format=json";
        /*
         {
        id: 1
        match: "/api/v1/match/1/"
        resource_uri: "/api/v1/turn/1/"
        turnnumber: 0
        }
         */
        String Body = "" +
                "{" +
                " \"match\": \"/api/v1/match/1/" +
                " \"turnnumber\": " + turn +
                "}";


        System.out.println(Body);

        String results = api.httpPostOrPatch(URL, Body, 0, false);

        ArrayList<ActionJsonTemplate> ajtList = new ArrayList<ActionJsonTemplate>();
        for(int i = 0; i < currentPlayer.actions.size(); i++){
            Action ac = currentPlayer.actions.get(i);
            ActionJsonTemplate ajt = new ActionJsonTemplate();
            int actionType = 0;
            if(ac instanceof Attack)
                actionType = 1;
            ajt.setActionMeta(i, actionType);
            if(i == 0){
                xLast = (int)(100 * round(currentPlayer.xLast, 2));
                yLast = (int)(100 * round(currentPlayer.yLast, 2));
                ajt.setOrigin(xLast, yLast);
            } else {
                ajt.setOrigin(xLast, yLast);
            }
            if(ac instanceof Move) {
                xLast = (int) (100 * round(ac.x, 2));
                yLast = (int) (100 * round(ac.y, 2));
            }
            //TODO: determine player's id from login storage
            ajt.setPlayer("/api/v1/player/2/");
            ajt.setTarget((int)(100 * round(ac.x, 2)), (int)(100 * round(ac.y, 2)));
            ajt.setTimeTaken(0);
            ajt.setTurn("/api/v1/turn/" + turn + "/");
            ajtList.add(ajt);
        }
        Gson gson = new Gson();
        String ajtJson = gson.toJson(ajtList);
        ajtJson = "{\"objects\": " + ajtJson + "}";
        results = api.httpPostOrPatch("http://45.33.62.187/api/v1/action/?format=json", ajtJson, 0, true);
    }

    public void getEnemyActions(){
        //randomize other squares movements
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).isEnemy){
                //reach out to server to get moves
                //mocked data
                Random rand = new Random();
                int x = rand.nextInt((370 - 10) + 1) + 10;
                int y = rand.nextInt((220 - 10) + 1) + 10;

                String action1 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";


                x = rand.nextInt((370 - 10) + 1) + 10;
                y = rand.nextInt((220 - 10) + 1) + 10;

                String action2 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";

                String json = "{\"ajst\":[" + action1 + "," + action2 + " ]}";

                //parse the json
                Gson gson = new Gson();
                EnemyJsonTemplate ejst = gson.fromJson(json, EnemyJsonTemplate.class);
//                this.players.get(i).addMove(ejst.ajst[0].destX, ejst.ajst[0].destY);
//                this.players.get(i).addMove(ejst.ajst[1].destX, ejst.ajst[1].destY);
            }
        }
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}