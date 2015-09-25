package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/4/2015.
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture square;
    public static TextureRegion squareRegion;

    public static Texture actions;
    public static TextureRegion menuRegion;
    public static TextureRegion submitRegion;

    public static Texture player;
    public static TextureRegion playerWalkingRegion;
    public static TextureRegion playerStill;
    public static TextureRegion dotRegion;
    public static TextureRegion bulletRegion;
    public static TextureRegion blockRegion;

    public static Animation playerWalking;
    public static Animation dotOscillating;

    public static Texture loadTexture (String file) { return new Texture(Gdx.files.internal(file));}

    public static void load(){
        //textures and texture regions
        background = loadTexture("background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);

        square = loadTexture("square.png");
        squareRegion = new TextureRegion(square, 0, 0, 50, 50);

        actions = loadTexture("actions.png");
        menuRegion = new TextureRegion(actions, 0, 0, 392, 127);
        submitRegion = new TextureRegion(actions, 0, 127, 392, 127);

        //506 by 169
        player = loadTexture("playerBig.png");
        playerWalkingRegion = new TextureRegion(player, 450, 105);
        playerStill = new TextureRegion(player, 50, 105);
        dotRegion = new TextureRegion(player, 0, 105, 506, 64);
        bulletRegion = new TextureRegion(player, 564, 0, 78, 24);
        blockRegion = new TextureRegion(player, 0, 916, 50, 50);

        //animations
        playerWalking = new Animation(.05f,
                new TextureRegion(playerWalkingRegion, 0, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 50, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 100, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 200, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 250, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 300, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 350, 0, 50, 105),
                new TextureRegion(playerWalkingRegion, 400, 0, 50, 105)//,
               // new TextureRegion(playerWalkingRegion, 450, 0, 50, 105)
        );

        dotOscillating = new Animation(.05f,
                new TextureRegion(dotRegion, 0, 0, 46, 66),
                new TextureRegion(dotRegion, 46, 0, 46, 66),
                new TextureRegion(dotRegion, 92, 0, 46, 66),
                new TextureRegion(dotRegion, 138, 0, 46, 66),
                new TextureRegion(dotRegion, 184, 0, 46, 66),
                new TextureRegion(dotRegion, 230, 0, 46, 66),
                new TextureRegion(dotRegion, 276, 0, 46, 66),
                new TextureRegion(dotRegion, 322, 0, 46, 66),
                new TextureRegion(dotRegion, 368, 0, 46, 66),
                new TextureRegion(dotRegion, 414, 0, 46, 66),
                new TextureRegion(dotRegion, 460, 0, 46, 66)
                );
    }
}
