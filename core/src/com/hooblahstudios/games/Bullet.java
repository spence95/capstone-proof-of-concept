package com.hooblahstudios.games;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/19/2015.
 */
public class Bullet extends DynamicGameObject {
    public final static float width = 10;
    public final static float height = 10;
    Vector2 destination;
    Vector2 dir;
    Vector2 movement;
    Vector2 vectorPosition;
    Vector2 velocity;
    float speed = 200;
    float rotation;
    boolean isShot;

    public Bullet(float x, float y, float speed){
        super(x, y, width, height);
        this.speed = speed;
        isShot = false;
        rotation = 0;
        destination = new Vector2(x, y);

    }

    public void explode(){
        //first stop the bullet
        isShot = false;
        velocity.x = 0;
        velocity.y = 0;
        this.destination.set(position.x, position.y);

        //then expand the bullet at a fast pace
        reset();
    }

    public void shoot(float x, float y, float originX, float originY){
        System.out.println("Dest: " + x + " " + y);
        System.out.println("Current: " + originX + " " + originY);
        position.set(originX, originY);
        //use trig to set destination real far out
        while(x < 800 || y < 480) {
            x = x + (x - originX);
            y = y + (y - originY);
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
           // if (position.dst2(destination) > movement.len2()) {
                position.add(movement);
                bounds.x = position.x - bounds.width / 2;
                bounds.y = position.y - bounds.height / 2;
//            } else {
//                position.set(destination);
//                bounds.x = position.x - bounds.width / 2;
//                bounds.y = position.y - bounds.height / 2;
//            }

            speed = speed + (speed*deltaTime);
        }

    }

    public void reset(){
        isShot = false;
        this.position.set(-100, -100);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;
        this.speed = 200;
    }
}
