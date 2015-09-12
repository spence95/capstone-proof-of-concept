package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
public class touchCoordinate {
    private static touchCoordinate tc = null;
    float x;
    float y;
    protected touchCoordinate() {

    }
    public static touchCoordinate getInstance() {
        if(tc == null){
            tc = new touchCoordinate();
        }
        return tc;
    }

    public void setCoordinates(float x, float y){
        //translate touch
        this.x = x + 390;
        this.y = y + 240;
    }


}
