package com.hooblahstudios.games;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/24/2015.
 */
public class MenuComponent {
    public final Vector2 position;
    public final Rectangle bounds;
    public final TextureRegion texture;

    public MenuComponent (float x, float y, float width, float height, TextureRegion texture) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.texture = texture;
    }
}
