package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/29/2015.
 */
public class Explosion extends GameObject{
    private boolean isDone;
    private float rate;
    private static final float width = 50;
    private static final float height = 50;
    //assume exploding on creation
    public Explosion(float x, float y){
        super(x, y, Bullet.width, Bullet.height);
        isDone = false;
        rate = 45;
    }

    public void update(float deltaTime){
        if (bounds.width < width && bounds.height < height) {
            bounds.width += deltaTime * rate;
            bounds.height += deltaTime * rate;
            rate = rate + (rate * deltaTime);
        } else {
            isDone = true;
        }
    }

    public boolean getIsDone(){
        return isDone;
    }
}
