package com.hooblahstudios.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hooblahstudios.games.Assets;

public class proofOfConcept extends Game {
    public SpriteBatch batcher;

    //Calls out to Assets class to create square
	@Override
	public void create () {
        batcher = new SpriteBatch();
        Assets.load();
        setScreen(new GameScreen(this));
    }

    //Calls out to GameScreen.update to call out to world.update and to listen for touches
	@Override
	public void render () {
        super.render();
	}
}
