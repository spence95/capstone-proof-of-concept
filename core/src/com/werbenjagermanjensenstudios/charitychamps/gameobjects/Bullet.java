package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.werbenjagermanjensenstudios.charitychamps.boilerplate.DynamicGameObject;

/**
 * Created by spence95 on 9/19/2015.
 */
public class Bullet extends DynamicGameObject {
    public final static float width = 10;
    public final static float height = 10;
    float speed = 200;
    public final static float topSpeed = 600;
    Vector2 destination;
    Vector2 dir;
    Vector2 movement;
    Vector2 vectorPosition;
    Vector2 velocity;
    public float rotation;
    public boolean isShot;
    public float runningModeDestinationX;
    public float runningModeDestinationY;

    //used to prevent colliding with own bullet on fire
    public int ownerID;

    public Bullet(float x, float y, float speed, int ownerID){
        super(x, y, width, height);
        this.speed = speed;
        isShot = false;
        rotation = 0;
        destination = new Vector2(x, y);
        runningModeDestinationX = -1000;
        runningModeDestinationY = -1000;
        this.ownerID = ownerID;
    }

    public void shoot(float x, float y, float originX, float originY){
        position.set(originX, originY);
        //use proportions to set destination real far out
        if(y >= originY && x >= originX){
            x = ((x - originX)/(y - originY)) * (Math.abs(y - 500)) + x;
            y = 500;
        }
        else if (y < originY && x >= originX) {
            x = (((x - originX)/Math.abs(y - originY)) * (y + 10)) + x;
            y = -10;
        }
        else if (y >= originY && x < originX){
            x = x - (Math.abs(x - originX)/(y - originY)) * (Math.abs(y - 500));
            y = 500;
        }
        else if (y < originY && x < originX){
            x = x - ((Math.abs(x - originX)/Math.abs(y - originY)) * (y + 10));
            y = -10;
        }

        destination.set(x, y);
        isShot = true;
    }

    public void update(float deltaTime){
        if(isShot) {
            dir = new Vector2();
            //on touch event set the touch vector then get direction vector
            dir.set(this.destination).sub(position).nor();
            movement = new Vector2();
            velocity = new Vector2(dir).scl(this.speed);
            movement.set(velocity).scl(deltaTime);
            position.add(movement);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;

            if( speed < topSpeed) {
                speed = speed + (speed * deltaTime);
            }
        }

    }

}
