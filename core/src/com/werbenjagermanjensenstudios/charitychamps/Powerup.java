package com.werbenjagermanjensenstudios.charitychamps;

/**
 * Created by spence95 on 11/8/2015.
 */
public class Powerup extends GameObject{
    boolean isDone;
    public Powerup(float x, float y, float width, float height, boolean isDone){
        super(x, y, width, height);
        this.isDone = isDone;
    }

    public void absorbed(Player player){
        isDone = true;
    }
}
