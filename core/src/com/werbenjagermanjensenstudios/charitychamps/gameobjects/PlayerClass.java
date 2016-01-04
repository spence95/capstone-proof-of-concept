package com.werbenjagermanjensenstudios.charitychamps.gameobjects;

/**
 * Created by spence95 on 12/13/2015.
 * //name of class
 //bullets, bombs, rockets
 //need attack button?
 //rate of fire
 //speed of movement
 //skin jpg name
 ///health
 */
public class PlayerClass {
    String name;

    //0 -> Normal, 1 -> Rocket, 2 -> Bomb
    public int bulletType;

    public boolean needAttackBtn;
    public float rateOfFirePerSecond;
    public float speedOfMvmnt;
    public int health;

    public PlayerClass(String name, int bulletType, boolean needAttackBtn, float rateOfFire, float speedOfMvmnt, int health){
        this.name = name;
        this.bulletType = bulletType;
        this.needAttackBtn = needAttackBtn;
        this.rateOfFirePerSecond = rateOfFire;
        this.speedOfMvmnt = speedOfMvmnt;
        this.health = health;
    }
}
