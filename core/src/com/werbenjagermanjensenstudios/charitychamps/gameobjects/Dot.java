package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.werbenjagermanjensenstudios.charitychamps.boilerplate.DynamicGameObject;

/**
 * Created by spence95 on 9/16/2015.
 */
public class Dot extends DynamicGameObject {
    public float x;
    public float y;
    public float stateTime;
    public Dot(float x, float y){
        super(x, y, 20, 20);
        stateTime = 0;
        this.x = x;
        this.y = y;
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
    }
}
