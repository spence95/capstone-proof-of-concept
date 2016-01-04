package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 11/27/2015.
 */
public class Bomb extends Bullet {
    public Bomb(float x, float y, float speed, int ownerID){
        super(x, y, speed, ownerID);
    }

    public void shoot(float x, float y, float originX, float originY){
        position.set(originX, originY);


        destination.set(x, y);
        isShot = true;
    }

    public void update(float deltaTime){
        if(isShot) {
            dir = new Vector2();
            //on touch event set the touch vector then get direction vector
            dir.set(this.destination).sub(position).nor();
            movement = new Vector2();
            if (position.dst2(destination) > movement.len2()) {
            position.add(movement);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;
            } else {
                position.set(destination);
                bounds.x = position.x - bounds.width / 2;
                bounds.y = position.y - bounds.height / 2;
            }

            if( speed < topSpeed) {
                speed = speed + (speed * deltaTime);
            }
        }

    }

}
