package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.google.gson.*;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Rectangle;

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
    public ArrayList<square> squares;
    boolean hasStarted = false;
    boolean isSetting = true;
    float lastTouchedX;
    float lastTouchedY;
    Rectangle menuBounds;
    public square currentPlayer;
    public Dot dot;
    public ActionMenu actionMenu;

    public World() {
        squares = new ArrayList<square>();
        actionMenu = new ActionMenu();
        dot = new Dot(-1000, -1000);
    }

    public void touched(float x, float y){
        //render actions menu
            //check if click in menu bounds
            if(actionMenu != null) {
                if (actionMenu.menuBounds.contains(x, y)) {
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
                }
            }
        }
    }

    public void moveClicked(float x, float y){
        Move mv = new Move(x, y);
        hideDot();
        this.currentPlayer.addMove(mv);
        this.currentPlayer.beginMoving();
        this.actionMenu.changeState(2);
    }

    public void attackClicked(float x, float y) {
        Attack at = new Attack(x, y);
        hideDot();
        this.currentPlayer.addAttack(at);
        this.currentPlayer.bullet.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
        this.actionMenu.changeState(2);
    }

    public void hideDot(){
        this.dot.position.x = 1000;
        this.dot.position.y = 1000;
    }

    public void start(){
        currentPlayer = new square(0, 1, 1, squareWidth, squareHeight, false);
        square sq1 = new square(1, WORLD_WIDTH - squareWidth, WORLD_HEIGHT - squareHeight, squareWidth, squareHeight, true);
        square sq2 = new square(2, 1, WORLD_HEIGHT - squareHeight, squareWidth, squareHeight, true);
        square sq3 = new square(3, WORLD_WIDTH - squareWidth, squareHeight, squareWidth, squareHeight, true);
        squares.add(currentPlayer);
        squares.add(sq1);
        squares.add(sq2);
        squares.add(sq3);
    }

    public void update(float deltaTime){

     //update dot
     if(dot != null){
         dot.update(deltaTime);
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
            //run all squares at once
            for (int i = 0; i < this.squares.size(); i++) {
                this.squares.get(i).updateRunning(deltaTime);
            }
        }
    }

    public void submit(){
        hideDot();
        this.actionMenu.changeState(2);
        this.currentPlayer.resetActions();
        this.isSetting = false;
        //reset current player
        currentPlayer.position.x = 1;
        currentPlayer.position.y = 1;
        getEnemyActions();
    }

    public void getEnemyActions(){
        //randomize other squares movements
        for(int i = 0; i < this.squares.size(); i++){
            if(this.squares.get(i).isEnemy){
                //reach out to server to get moves
                //mocked data
                Random rand = new Random();
                int x = rand.nextInt((390 - 10) + 1) + 10;
                int y = rand.nextInt((240 - 10) + 1) + 10;

                String action1 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";


                x = rand.nextInt((390 - 10) + 1) + 10;
                y = rand.nextInt((240 - 10) + 1) + 10;

                String action2 =  "{  \"type\": \"Move\",  \"originX\":100, \"originY\":100, \"destX\":" + Float.toString(x) + ", \"destY\":" + Float.toString(y) + " }";

                String json = "{\"ajst\":[" + action1 + "," + action2 + " ]}";

                //parse the json
                Gson gson = new Gson();
                EnemyJsonTemplate ejst = gson.fromJson(json, EnemyJsonTemplate.class);
                Move mv = new Move(ejst.ajst[0].destX, ejst.ajst[0].destY);
                this.squares.get(i).addMove(mv);
                mv = new Move(ejst.ajst[1].destX, ejst.ajst[1].destY);
                this.squares.get(i).addMove(mv);
            }
        }
    }
}