package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.werbenjagermanjensenstudios.charitychamps.boilerplate.GameObject;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Bullet;

/**
 * Created by spence95 on 9/29/2015.
 */
public class Explosion extends GameObject {
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
        float widthCheck = bounds.width + deltaTime * rate;
        float heightCheck = bounds.height + deltaTime * rate;
        if (widthCheck < width && heightCheck < height) {
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
