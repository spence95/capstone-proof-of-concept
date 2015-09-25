package com.hooblahstudios.games;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/19/2015.
 */
public class Bullet extends DynamicGameObject {
    public final static float width = 20;
    public final static float height = 5;
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

    public void shoot(float x, float y, float originX, float originY){
//        if(y > this.position.y){
//            float adj = Math.abs(this.position.x - x);
//            float opp = Math.abs(this.position.y - y);
//            rotation = (float)Math.tan(opp/adj) * 57.3f;
//            //rotation = rotation + 180f;
//        } else {
//
//        }
        position.set(originX, originY);
        destination.set(x, y);
        isShot = true;
    }

    public void update(float deltaTime){
        if(isShot) {
            dir = new Vector2();
            //on touch event set the touch vector then get direction vector
            dir.set(this.destination).nor();
            movement = new Vector2();
            velocity = new Vector2(dir).scl(this.speed);
            movement.set(velocity).scl(deltaTime);

            position.add(movement);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;

            speed = speed + (speed*deltaTime);
            System.out.println(this.position.x + " " + this.position.y);
        }
    }

    public void reset(){
        isShot = false;
        this.position.set(-100, -100);
        this.speed = 200;
    }
}
