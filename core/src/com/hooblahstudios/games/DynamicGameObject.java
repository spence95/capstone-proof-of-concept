package com.hooblahstudios.games;

/**
 * Created by spence95 on 9/5/2015.
 */
    import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;

    public DynamicGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}

