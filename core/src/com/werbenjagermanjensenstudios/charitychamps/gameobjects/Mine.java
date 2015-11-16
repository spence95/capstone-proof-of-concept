package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

import com.werbenjagermanjensenstudios.charitychamps.boilerplate.GameObject;

/**
 * Created by spence95 on 11/14/2015.
 */
public class Mine extends GameObject {
    public static final float width = 10;
    public static final float height = 10;
    public boolean exploded;

    public Mine(float x, float y){
        super(x, y, width, height);
        exploded = false;
    }
}
