package com.werbenjagermanjensenstudios.charitychamps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class proofOfConcept extends Game {
    public SpriteBatch batcher;
    private int playerID;
    private int charityID;
    private int playerMatchID;

    //Calls out to Assets class to create square
    @Override
    public void create () {
        batcher = new SpriteBatch();
        Assets.load();

        //setScreen(new GameScreen(this));
        //commented out below for development purposes
        setScreen(new MenuScreen(this));
    }

    //Calls out to GameScreen.update to call out to world.update and to listen for touches
    @Override
    public void render () {
        super.render();
    }

    public void setPlayerID(int id){
        playerID = id;
    }

    public int getPlayerID(){
        return playerID;
    }

    public void setCharityID(int id) { charityID = id; }

    public int getCharityID() { return charityID; }

    public void setPlayerMatchID(int id) { playerMatchID = id; }

    public int getPlayerMatchID() { return playerMatchID; }

}
