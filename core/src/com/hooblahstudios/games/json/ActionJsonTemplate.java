package com.hooblahstudios.games.json;

/**
 * Created by spence95 on 10/7/2015.
 * Example JSON below
 *  actionnumber: 1
     actiontype: 1
     activepowerup: null
     id: 2
     originx: 95
     originy: 95
     player: "/api/v1/player/2/"
     resource_uri: "/api/v1/action/2/"
     targetx: 75
     targety: 75
     timetaken: 10
     turn: "/api/v1/turn/1/"
 */
public class ActionJsonTemplate {
    /*

     */
    int actionnumber;
    int actiontype;
    //String activepowerup;
    int originx;
    int originy;
    String player;
    int targetx;
    int targety;
    int timetaken;
    String turn;

    public ActionJsonTemplate(){

    }

    public void setActionMeta(int actionNumber, int actionType){
        this.actionnumber = actionNumber;
        this.actiontype = actionType;
    }

//    public void setActivePowerUp(String activePowerUp){
//        this.activepowerup = activePowerUp;
//    }

    public void setOrigin(int x, int y){
        this.originx = x;
        this.originy = y;
    }

    public void setPlayer(String player){
        this.player = player;
    }

    public void setTarget(int x, int y){
        this.targetx = x;
        this.targety = y;
    }

    public void setTimeTaken(int timeTaken){
        this.timetaken = timeTaken;
    }

    public void setTurn(String turn){
        this.turn = turn;
    }
}
