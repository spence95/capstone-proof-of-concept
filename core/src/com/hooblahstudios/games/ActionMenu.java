package com.hooblahstudios.games;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by spence95 on 9/16/2015.
 */
public class ActionMenu extends DynamicGameObject {
    public final static float width = World.WORLD_WIDTH / 3;
    public final static float height = World.WORLD_HEIGHT / 4;
    float stateTime;
    Rectangle menuBounds;
    //0 = hidden/shown, 1 = moving up, 2 = moving down
    int state;
    int speed = 15;
    boolean isReadyToSubmit;

    public ActionMenu(){
        super((World.WORLD_WIDTH / 2) - ((World.MENU_WIDTH/2)/2), -World.MENU_HEIGHT, World.MENU_WIDTH / 2, World.MENU_HEIGHT / 2);
        this.stateTime = 0;
        this.state = 0;
        this.velocity.set(0, 0);
        this.setMenuBounds();
        this.isReadyToSubmit = false;
    }

    public void update(float deltaTime){
        switch(this.state){
            case 0:
                this.velocity.set(0,0);
                break;
            case 1:
                if(this.position.y < -10) {
                    System.out.println(this.position.y);
                    this.velocity.add(0, speed);
                }
                else{
                    changeState(0);
                }
                break;
            case 2:
                if(this.position.y > -this.height){
                    this.velocity.add(0, -speed);
                }
                else{
                    changeState(0);
                }
                break;
        }
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;
        stateTime += deltaTime;
    }

    public void changeState(int newState){
        this.state = newState;
    }

    public void setMenuBounds(){
        this.menuBounds = new Rectangle();
        menuBounds = new Rectangle((World.WORLD_WIDTH / 2) - ((this.width/2)/2), 0, this.width / 2, this.height / 2);
    }
}
