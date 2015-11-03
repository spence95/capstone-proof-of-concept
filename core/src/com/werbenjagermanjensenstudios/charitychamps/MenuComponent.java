package com.werbenjagermanjensenstudios.charitychamps;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/24/2015.
 */
public class MenuComponent {
    public final Vector2 position;
    public final Rectangle bounds;
    public TextureRegion texture;

    public MenuComponent (float x, float y, float width, float height, TextureRegion texture) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
        this.texture = texture;
    }

    public boolean containsXY (float x, float y)
    {
        float adjustedX = x;
        float adjustedY = y + 50;//I think this should make stuff work. basically the buttons' hitboxes are higher than they are being rendered.
        return bounds.contains(adjustedX, adjustedY);
    }
}
