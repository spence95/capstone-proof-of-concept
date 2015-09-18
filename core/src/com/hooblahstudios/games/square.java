package com.hooblahstudios.games;

import com.badlogic.gdx.math.Vector2;
import com.hooblahstudios.games.touchCoordinate;

import java.util.ArrayList;

/**
 * Created by spence95 on 9/4/2015.
 */
public class square extends DynamicGameObject{
    boolean isMoving;
    boolean isEnemy;
    boolean isDone;
    int id;
    float stateTime;
    float movementLeft;
    Vector2 destination;
    Vector2 vectorPosition;
    int turnCounter;
    ArrayList<Action> actions;
    ArrayList<Action> savedActions;
    Vector2 velocity;
    Vector2 movement;
    Vector2 dir;

    public final int speed = 120;

    public square(int id, float x, float y, float width, float height, boolean isEnemy) {
        super(x, y, width, height);
        this.id = id;
        this.isMoving = false;
        this.isDone = false;
        this.turnCounter = 0;
        stateTime = 0;
        this.isEnemy = isEnemy;
        actions = new ArrayList<Action>();
        savedActions = new ArrayList<Action>();
        this.vectorPosition = new Vector2(x, y);
        this.destination = new Vector2(x, y);
        this.movementLeft = 7;
    }

    public void addMove(Move mv){
        savedActions.add(mv);
        actions.add(mv);
    }

    public void setToMove(Move mv){
        this.destination = new Vector2(mv.x, mv.y);
    }

    public void update(float deltaTime){
        dir = new Vector2();

        //on touch event set the touch vector then get direction vector
        dir.set(this.destination).sub(this.vectorPosition).nor();
        movement = new Vector2();
        velocity = new Vector2(dir).scl(this.speed);
        movement.set(velocity).scl(deltaTime);

        if (position.dst2(destination) > movement.len2()) {
            position.add(movement);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;
            movementLeft -= deltaTime;
        } else {
            position.set(destination);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;
            this.isMoving = false;
        }
        vectorPosition.set(this.position.x, this.position.y);

        if(movementLeft <= 0 && this.velocity.x == 0 && this.velocity.y == 0){
            this.isDone = true;
        }

        stateTime += deltaTime;
    }

    public void updateSetting(float deltaTime){
        update(deltaTime);
    }

    public void updateRunning(float deltaTime){
        if(!this.isMoving) {
           this.beginMoving();
        }
        if(this.isMoving){
            update(deltaTime);
        }
    }

    public void beginMoving(){
        if(this.turnCounter < this.actions.size()) {
            setToMove((Move) this.actions.get(this.turnCounter));
            this.isMoving = true;
            this.turnCounter++;
        }
    }

    public void resetActions(){
        this.actions = this.savedActions;
        this.turnCounter = 0;
    }
}
