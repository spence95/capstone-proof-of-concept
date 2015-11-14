package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

/**
 * Created by spence95 on 11/8/2015.
 */
public class HeartPowerup extends Powerup {
    public HeartPowerup(float x, float y, float width, float height){
        super(x, y, width, height, false);
    }

    public void absorbed(Player player){
        player.health +=  1;
        isDone = true;
    }
}
