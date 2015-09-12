package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/6/2015.
 */
public class Move extends Action{
    public Move(float x, float y){
        super(x, y);
    }

    public float getXToMove(square sq){
        return this.x - sq.position.x;
    }

    public float getYToMove(square sq){
        return this.y - sq.position.y;
    }
}
