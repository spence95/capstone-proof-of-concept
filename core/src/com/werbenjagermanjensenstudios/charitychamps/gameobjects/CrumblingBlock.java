package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

/**
 * Created by spence95 on 11/15/2015.
 */
public class CrumblingBlock extends Block {
    public int health;
    public CrumblingBlock(float x, float y, float width, float height){
        super(x, y, width, height);
        health = 1;
    }
}
