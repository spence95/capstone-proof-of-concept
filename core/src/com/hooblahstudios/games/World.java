package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class World {
    public static final float WORLD_WIDTH = 780;
    public static final float WORLD_HEIGHT = 480;
    public final int squareWidth = 10;
    public final int squareHeight = 10;
    public ArrayList<square> squares;
    boolean hasStarted = false;
    boolean isSetting = true;
    public square currentPlayer;
    public World() {
        squares = new ArrayList<square>();
    }

    public void touched(float x, float y){
        Move mv = new Move(x, y);
        this.currentPlayer.addMove(mv);
        this.currentPlayer.beginMoving();
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
     if(isSetting){
            this.currentPlayer.updateSetting(deltaTime);
            if(this.currentPlayer.isDone){
                this.currentPlayer.resetActions();
                this.isSetting = false;
                //reset current player
                currentPlayer.position.x = 1;
                currentPlayer.position.y = 1;
                getEnemyActions();
            }
        }
        else{
            //run all squares at once
            for (int i = 0; i < this.squares.size(); i++) {
                this.squares.get(i).updateRunning(deltaTime);
            }
        }
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