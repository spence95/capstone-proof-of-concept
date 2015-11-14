package com.werbenjagermanjensenstudios.charitychamps;

import com.werbenjagermanjensenstudios.charitychamps.boilerplate.GameObject;

/**
 * Created by spence95 on 11/6/2015.
 */
public class SideActionMenu extends GameObject {
    public static String moveAction = "moveAction";
    public static String attackAction = "attackAction";

    private String activeAction;

    public SideActionMenu(float x, float y, float width, float height){
        super(x, y, width, height);
        activeAction = moveAction;
    }

    public void setActiveAction(String actionName){
        activeAction = actionName;
    }

    public String getActionName(){
        return activeAction;
    }
}
