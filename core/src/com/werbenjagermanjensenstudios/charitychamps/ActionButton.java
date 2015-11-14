package com.werbenjagermanjensenstudios.charitychamps;

import com.werbenjagermanjensenstudios.charitychamps.boilerplate.GameObject;

/**
 * Created by spence95 on 11/6/2015.
 */
public class ActionButton extends GameObject {
    public static String moveName = "Move";
    public static String attackName = "Attack";

    private String name;
    public boolean isActive;

    public ActionButton(String name, boolean isActive, float x, float y, float width, float height){
        super(x, y, width, height);
        this.name = name;
        this.isActive = isActive;
    }

    public void toggleActive(){
        isActive = !isActive;
    }

    public void setActiveAction(String actionName){
        name = actionName;
    }

    public String getActionName(){
        return name;
    }
}
