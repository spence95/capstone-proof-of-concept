package com.hooblahstudios.games;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/16/2015.
 */
public class ActionMenu extends DynamicGameObject {
    public final static float width = World.MENU_WIDTH;
    public final static float height = World.MENU_HEIGHT;
    float stateTime;
    Rectangle menuBounds;
    Rectangle attackBounds;
    //0 = hidden/shown, 1 = moving up, 2 = moving down
    int state;
    int speed = 250;
    boolean isReadyToSubmit;
    boolean isShown;

    Vector2 dir;
    Vector2 destination;
    Vector2 vectorPosition;
    Vector2 movement;
    Vector2 velocity;

    public ActionMenu(){
        super((World.WORLD_WIDTH / 2) - ((World.MENU_WIDTH/2)/2), -World.MENU_HEIGHT, World.MENU_WIDTH / 2, World.MENU_HEIGHT / 2);
        this.stateTime = 0;
        this.state = 0;
        this.isShown = false;
        this.setMenuBounds();
        this.isReadyToSubmit = false;
        this.vectorPosition = new Vector2(this.position.x, this.position.y);
    }

    public void update(float deltaTime){
        switch(this.state){
            case 0:
                //this.velocity.set(0,0);
                this.destination = new Vector2(this.position.x, this.position.y);
                break;
            case 1:
                this.destination = new Vector2(this.position.x, 0);
                break;
            case 2:
                this.destination = new Vector2(this.position.x, -this.height);
                break;
        }
       move(deltaTime);
       stateTime += deltaTime;
    }

    public void move(float deltaTime){
        dir = new Vector2();
        dir.set(this.destination).sub(this.vectorPosition).nor();
        movement = new Vector2();
        velocity = new Vector2(dir).scl(this.speed);
        movement.set(velocity).scl(deltaTime);
        if (position.dst2(destination) > movement.len2()) {
            position.add(movement);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;
        } else {
            position.set(destination);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;
            changeState(0);
        }
        vectorPosition.set(this.position.x, this.position.y);
    }

    public void changeState(int newState){
        this.state = newState;
    }

    public void makeReadyToSubmit(){
        this.isReadyToSubmit = true;
        this.isShown = true;
        this.changeState(1);
    }

    public void setMenuBounds(){
        this.menuBounds = new Rectangle();
        menuBounds = new Rectangle((World.WORLD_WIDTH / 2) - ((World.MENU_WIDTH)/2), 0, World.MENU_WIDTH, World.MENU_HEIGHT);
    }
}
