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

    public ScreenController(proofOfConcept game){
        this.game = game;
    }

    public void setGameOverScreen(){
        game.setScreen(new GameOverScreen(game));
    }
}
