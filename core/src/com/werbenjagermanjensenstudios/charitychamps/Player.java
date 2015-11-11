package com.werbenjagermanjensenstudios.charitychamps;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by spence95 on 9/4/2015.
 */
public class Player extends DynamicGameObject{
    boolean isMoving;
    boolean isEnemy;
    boolean isDone;
    boolean isWaiting;
    boolean isFiring;
    boolean dead;
    int id;
    int currentTurnId;
    float stateTime;
    private float secondsWaiting;
    Vector2 destination;
    Vector2 vectorPosition;
    int turnCounter;
    ArrayList<Action> actions;
    ArrayList<Action> savedActions;
    Vector2 velocity;
    Vector2 movement;
    Vector2 dir;
    Bullet bullet;
    float xLast;
    float yLast;
    float side;

    int health;
    boolean isImmune;
    float immuneCounter;
    public static float immuneCounterLimit = 2f;

    public static int time = 17;

    public final int speed = 180;

    public Player(int id, float width, float height, boolean isEnemy) {
        super(-1000, -1000, width, height);
        this.currentTurnId = -100;
        this.id = id;
        this.isMoving = false;
        this.isDone = false;
        this.isFiring = false;
        dead = false;
        this.turnCounter = 0;
        this.secondsWaiting = 0;
        stateTime = 0;
        this.isEnemy = isEnemy;
        actions = new ArrayList<Action>();
        savedActions = new ArrayList<Action>();
        bullet = new Bullet(-100, -100, 200);
        side = 1;
        health = 2;
        isImmune = false;
        immuneCounter = 0;
    }

    public void spawn(float x, float y){

        position.x = x;
        position.y = y;
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        //initialization stuff depending on knowing first position
        this.xLast = x;
        this.yLast = y;
        this.vectorPosition = new Vector2(x, y);
        this.destination = new Vector2(x, y);
    }

    public void addMove(float x, float y){
        Move mv = new Move(x, y, secondsWaiting);
        setToMove(mv);
        this.isMoving = true;
        savedActions.add(mv);
        actions.add(mv);
        resetSecondsWaiting();
    }

    //used for enemy's actions grabbed from server
    public void jsonAddMove(float x, float y){
        Move mv = new Move(x, y, secondsWaiting);
        savedActions.add(mv);
        actions.add(mv);
    }

    public void addAttack(Attack at){
        savedActions.add(at);
        actions.add(at);
        this.turnCounter++;
    }

    public void setToMove(Move mv){
        this.destination = new Vector2(mv.x, mv.y);
    }

    public void resetSecondsWaiting(){
        secondsWaiting = 0;
    }

    public void update(float deltaTime, boolean isRunning){
            //ten seconds per turn
            if (stateTime <= time) {
                dir = new Vector2();
                //on touch event set the touch vector then get direction vector
                dir.set(this.destination).sub(position).nor();
                movement = new Vector2();
                velocity = new Vector2(dir).scl(speed);
                movement.set(velocity).scl(deltaTime);

                if (velocity.x < 0) {
                    side = -1;
                } else if (velocity.x > 0) {
                    side = 1;
                }

                if (position.dst2(destination) > movement.len2()) {
                    this.isMoving = true;
                    position.add(movement);
                    bounds.x = position.x - bounds.width / 2;
                    bounds.y = position.y - bounds.height / 2;
                } else {
                    position.set(destination);
                    bounds.x = position.x - bounds.width / 2;
                    bounds.y = position.y - bounds.height / 2;
                    stop();
                    if (isRunning) {
                        if (actions.size() > turnCounter + 1 && !isWaiting) {
                            secondsWaiting = 0;
                            turnCounter++;
                        } else if (turnCounter + 1 <= actions.size() && !isWaiting) {
                            isDone = true;
                        }
                    }
                }
            } else {
                isDone = true;
                stop();
            }
            if (!isRunning) {
                secondsWaiting += deltaTime;
            }
            stateTime += deltaTime;
    }

    public void updateAttack(float deltaTime){
        //set bullet's position and destination
        bullet.shoot(actions.get(turnCounter).x, actions.get(turnCounter).y, position.x, position.y);
        stateTime += deltaTime;
    }

    public void updateSetting(float deltaTime){
        update(deltaTime, false);
    }

    public void updateRunning(float deltaTime){
        secondsWaiting += deltaTime;
        if(this.actions.size() > turnCounter) {
            //temp fix, without it crashes for some reason
            //if(turnCounter < actions.size()) {
                if (actions.get(turnCounter) instanceof Spawn){
                    if(!isDone){
//                        position.set(actions.get(turnCounter).x, actions.get(turnCounter).y);
//                        bounds.x = position.x - bounds.width / 2;
//                        bounds.y = position.y - bounds.height / 2;
                        spawn(actions.get(turnCounter).x,actions.get(turnCounter).y);
                        turnCounter++;
                    }
                }
               else if (this.actions.get(turnCounter) instanceof Move) {
                    if (!isDone) {
                        Move mv = (Move) this.actions.get(turnCounter);
                        if (secondsWaiting >= mv.secondsToWait) {
                            isWaiting = false;
                            this.destination.set(this.actions.get(turnCounter).x, this.actions.get(turnCounter).y);
                        } else {
                            isWaiting = true;
                            this.destination.set(position.x, position.y);
                        }
                        update(deltaTime, true);
                    }
                } else if (this.actions.get(turnCounter) instanceof Attack) {
                    if (bullet.isShot) {

                        //if bullet out of bounds move onto moving again
                        if (bullet.position.y > World.WORLD_HEIGHT || bullet.position.y < 0 || bullet.position.x > World.WORLD_WIDTH || bullet.position.x < 0) {
                            bullet.reset();
                        }
                    } else {
                        updateAttack(deltaTime);
                        this.turnCounter++;
                    }
              //  }
            }
        } else {
            isDone = true;
        }
    }

    public void beginMoving(){
        //if(this.turnCounter < this.actions.size()) {
//                setToMove((Move) this.actions.get(this.turnCounter));
//                this.isMoving = true;
               // this.turnCounter++;
        //}
    }

    public void resetActions(){
        //this.actions = this.savedActions;
        this.turnCounter = 0;
        this.stateTime = 0;
        this.isDone = false;
        //bullet.reset();
        resetSecondsWaiting();
    }

    public void stop() {
        velocity.x = 0;
        velocity.y = 0;
        movement.x = 0;
        movement.y = 0;
        this.isMoving = false;
        this.destination.set(position.x, position.y);
    }

    public void forgetActions() {
        actions = new ArrayList<Action>();
        savedActions = new ArrayList<Action>();
        turnCounter = 0;
        stateTime = 0;
        isDone = false;
        //bullet.reset();
    }

    public void takeHit(int damage){
        this.health -= damage;
        if(this.health < 1){
            die();
        }
    }

    public void die() {
        dead = true;
    }

    public void setLastPosition(float x, float y){
        xLast = x;
        yLast = y;
    }

    public void setCurrentTurnId(int currentTurnId){
        this.currentTurnId = currentTurnId;
    }

    public void orderActions(){
        Collections.sort(actions, new Comparator<Action>() {
            public int compare(Action a1, Action a2) {
                return a1.sequenceNum < a2.sequenceNum ? -1 : 1;
            }
        });
    }

}
