package com.hooblahstudios.games;

/**
 * Created by spence95 on 10/6/2015.
 *
 * READTHIS v
 *
 * The model hands off to this anytime in wants to interact with the screens(view)
 */
public class ScreenController {
    proofOfConcept game;
    GameScreen gs;

    public ScreenController(proofOfConcept game, GameScreen gs){
        this.gs = gs;
        this.game = game;
    }

    public void setGameOverScreen(){
        game.setScreen(new GameOverScreen(game));
    }

    public void setRetrievingScreen(World world) {game.setScreen(new RetrievingScreen(game, world, this, gs.renderer));}

    public void resetGameScreen() {
        game.setScreen(gs);
    }
}
