package com.werbenjagermanjensenstudios.charitychamps.actions;

/**
 * Created by spence95 on 9/6/2015.
 */
public class Action {
    public int sequenceNum;
    public float x;
    public float y;
    public float secondsToWait;
    public Action(float x, float y, float secondsToWait){
        this.x = x;
        this.y = y;
        this.secondsToWait = secondsToWait;
        //set upon retrieval of data from database
        this.sequenceNum = -1;
    }

    public void setSequenceNum(int sequenceNum){
        this.sequenceNum = sequenceNum;
    }

}
