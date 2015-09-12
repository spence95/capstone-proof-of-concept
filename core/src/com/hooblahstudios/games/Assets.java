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

    public static Texture submit;
    public static TextureRegion submitRegion;

    public static Texture loadTexture (String file) { return new Texture(Gdx.files.internal(file));}

    public static void load(){
        background = loadTexture("background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);

        square = loadTexture("square.png");
        squareRegion = new TextureRegion(square, 0, 0, 50, 50);

        submit = loadTexture("submit.png");
        submitRegion = new TextureRegion(submit, 0, 0, 100, 50);
    }
}
