package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/16/2015.
 */
public class Dot extends DynamicGameObject {
    float x;
    float y;
    float stateTime;
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
