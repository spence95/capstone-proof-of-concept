package com.hooblahstudios.games;

import com.badlogic.gdx.math.Vector2;
import com.hooblahstudios.games.touchCoordinate;

import java.util.ArrayList;

/**
 * Created by spence95 on 9/4/2015.
 */
public class Player extends DynamicGameObject{
    boolean isMoving;
    boolean isEnemy;
    boolean isDone;
    boolean isWaiting;
    int id;
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

    public final int speed = 120;

    public Player(int id, float x, float y, float width, float height, boolean isEnemy) {
        super(x, y, width, height);
        this.id = id;
        this.isMoving = false;
        this.isDone = false;
        this.turnCounter = 0;
        this.secondsWaiting = 0;
        stateTime = 0;
        this.isEnemy = isEnemy;
        actions = new ArrayList<Action>();
        savedActions = new ArrayList<Action>();
        this.vectorPosition = new Vector2(x, y);
        this.destination = new Vector2(x, y);
        bullet = new Bullet(-100, -100, 200);
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
        bullet.update(deltaTime);
        //ten seconds per turn
        if(stateTime <= 1200) {

            dir = new Vector2();
            //on touch event set the touch vector then get direction vector
            dir.set(this.destination).sub(position).nor();
            movement = new Vector2();
            velocity = new Vector2(dir).scl(speed);
            movement.set(velocity).scl(deltaTime);

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
                if(isRunning){
                    if(actions.size() > turnCounter + 1 && !isWaiting) {
                        secondsWaiting = 0;
                        turnCounter++;
                    } else if(turnCounter + 1 <= actions.size() && !isWaiting){
                        isDone = true;
                    }
                }
            }
        } else {
            isDone = true;
            stop();
        }
        if(!isRunning) {
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
        if(!isDone && this.actions.size() > 0) {
            if (this.actions.get(turnCounter) instanceof Move) {
                Move mv = (Move) this.actions.get(turnCounter);
                if (secondsWaiting >= mv.secondsToWait) {
                    isWaiting = false;
                    this.destination.set(this.actions.get(turnCounter).x, this.actions.get(turnCounter).y);
                } else {
                    isWaiting = true;
                    this.destination.set(position.x, position.y);
                }
                update(deltaTime, true);
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
            }
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
        this.actions = this.savedActions;
        this.turnCounter = 0;
        this.stateTime = 0;
        this.isDone = false;
        bullet.reset();
        resetSecondsWaiting();
    }

    public void stop() {
        velocity.x = 0;
        velocity.y = 0;
        this.isMoving = false;
        this.destination.set(position.x, position.y);
    }
}
