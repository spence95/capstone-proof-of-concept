package com.hooblahstudios.games;

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
    float xToMove;
    float yToMove;
    int turnCounter;
    ArrayList<Action> actions;
    ArrayList<Action> savedActions;

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
       setVelocity();
    }

    public void setVelocity(){
        velocity.set(0, 0);
        velocity.add(speed, speed);
    }

    public void addMove(Move mv){
        savedActions.add(mv);
        actions.add(mv);
    }

    public void setToMove(Move mv){
        this.xToMove = mv.getXToMove(this);
        this.yToMove = mv.getYToMove(this);
    }

    public void update(float deltaTime){
        if((int)this.xToMove != 0){
            if(this.xToMove > 0){
                position.add(velocity.x*deltaTime, 0);
                bounds.x = position.x - bounds.width / 2;
                this.xToMove = this.xToMove - (velocity.x*deltaTime);
            }
            else if(this.xToMove < 0){
                position.add(-velocity.x*deltaTime, 0);
                bounds.x = position.x - bounds.width / 2;
                this.xToMove = this.xToMove + (velocity.x*deltaTime);
            }
        }
        else if((int)this.yToMove != 0){
            if(this.yToMove > 0){
                position.add(0, velocity.y*deltaTime);
                bounds.y = position.y - bounds.height / 2;
                this.yToMove = this.yToMove - (velocity.y*deltaTime);
            }
            else if(this.yToMove < 0){
                position.add(0, -velocity.y*deltaTime);
                bounds.y = position.y - bounds.height / 2;
                this.yToMove = this.yToMove + (velocity.y*deltaTime);
            }
        }
        else{
            this.isMoving = false;
            //assume that two moves takes up all action
            if(turnCounter > 1 && !this.isMoving)
                this.isDone = true;
        }
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

    public void endMove(){
        this.actions.remove(0);
        if(this.actions.size() == 0){
            Move mv = new Move(this.position.x - 390, this.position.y - 240);
            this.actions.add(mv);
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
