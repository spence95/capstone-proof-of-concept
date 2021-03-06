package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.werbenjagermanjensenstudios.charitychamps.actions.Action;
import com.werbenjagermanjensenstudios.charitychamps.actions.Attack;
import com.werbenjagermanjensenstudios.charitychamps.actions.Move;
import com.werbenjagermanjensenstudios.charitychamps.actions.Spawn;
import com.werbenjagermanjensenstudios.charitychamps.World;
import com.werbenjagermanjensenstudios.charitychamps.boilerplate.DynamicGameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by spence95 on 9/4/2015.
 */
public class Player extends DynamicGameObject {
    public boolean isMoving;
    public boolean isEnemy;
    public boolean isDone;
    public boolean isWaiting;
    public boolean isFiring;
    public boolean dead;
    public int id;
    public int currentTurnId;
    public float stateTime;
    private float secondsWaiting;
    public Vector2 destination;
    Vector2 vectorPosition;
    public int turnCounter;
    public ArrayList<Action> actions;
    ArrayList<Action> savedActions;
    float attackSecondsWaiting;
    public PlayerClass playerclass;

    //this list is used in running mode so the world can poll the player to get new bullets to its list
    public ArrayList<Bullet> bulletsShot;

    public Vector2 velocity;
    Vector2 movement;
    Vector2 dir;
    public float xLast;
    public float yLast;
    public float side;

    public int health;
    public boolean isImmune;
    public float immuneCounter;
    public static float immuneCounterLimit = 2f;

    public static int time = 10;

    public final int speed = 180;

    public Player(int id, float width, float height, boolean isEnemy, PlayerClass playerClass) {
        super(-1000, -1000, width, height);
        this.currentTurnId = -100;
        this.id = id;
        this.isMoving = false;
        this.isDone = false;
        this.isFiring = false;
        dead = false;
        this.turnCounter = 0;
        this.secondsWaiting = 0;
        this.playerclass = playerClass;
        stateTime = 0;
        this.isEnemy = isEnemy;
        actions = new ArrayList<Action>();
        savedActions = new ArrayList<Action>();
        side = 1;
        health = 2;
        isImmune = false;
        immuneCounter = 0;
        attackSecondsWaiting = 0;
        bulletsShot = new ArrayList<Bullet>();
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
        //this.turnCounter++;
    }

    public Move getLastMove(){
        for(int i = actions.size() - 1; i > 0; i--){
            Action ac = actions.get(i);
            if(ac instanceof Move){
                return (Move)ac;
            }
        }
        return null;
    }

    public void setLastMove(Move mv){
        for(int i = actions.size() - 1; i > 0; i--){
            Action ac = actions.get(i);
            if(ac instanceof Move){
                actions.get(i).x = mv.x;
                actions.get(i).y = mv.y;
            }
        }
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


    public void updateSetting(float deltaTime){
        update(deltaTime, false);
    }

    public void updateRunning(float deltaTime){
        secondsWaiting += deltaTime;
        if(this.actions.size() > turnCounter) {
                if (actions.get(turnCounter) instanceof Spawn){
                    if(!isDone){
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
                    if(attackSecondsWaiting > 0){
                        attackSecondsWaiting -= deltaTime;
                    }
                    if(attackSecondsWaiting <= 0) {
                        //if bullet is rocket
                        if(playerclass.bulletType == 1) {
                            Rocket bu = new Rocket(position.x, position.y, 200, id);
                            bu.runningModeDestinationX = this.actions.get(turnCounter).x;
                            bu.runningModeDestinationY = this.actions.get(turnCounter).y;
                            bulletsShot.add(bu);
                        }
                        else if(playerclass.bulletType == 2) {
                            Bomb bomb = new Bomb(this.position.x, this.position.y, 200, this.id);
                            bomb.runningModeDestinationX = this.actions.get(turnCounter).x;
                            bomb.runningModeDestinationY = this.actions.get(turnCounter).y;
                            bulletsShot.add(bomb);
                        }
                        else {
                            Bullet bu = new Bullet(position.x, position.y, 200, id);
                            bu.runningModeDestinationX = this.actions.get(turnCounter).x;
                            bu.runningModeDestinationY = this.actions.get(turnCounter).y;
                            bulletsShot.add(bu);
                        }
                        attackSecondsWaiting = this.actions.get(turnCounter).secondsToWait;
                        this.turnCounter++;
                    }
                    stateTime += deltaTime;
                }
        } else {
            isDone = true;
        }
    }

    public void resetActions(){
        this.turnCounter = 0;
        this.stateTime = 0;
        this.isDone = false;
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
