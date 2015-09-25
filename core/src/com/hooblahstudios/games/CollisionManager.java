package com.hooblahstudios.games;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by spence95 on 9/24/2015.
 */
public class CollisionManager {
    World world;

    public CollisionManager(World world){
        this.world = world;
    }

    public void updateCollisions(){
        updateBlockCollisions();
    }

    public void updateBlockCollisions(){
        for(int playerIndex = 0; playerIndex < world.players.size(); playerIndex++){
            Player player = world.players.get(playerIndex);
            for(int blockIndex = 0; blockIndex < world.blocks.size(); blockIndex++){
                Block block = world.blocks.get(blockIndex);
                if(player.bounds.overlaps(block.bounds)){
                    System.out.println("COLLIDING!!!");

                    //bump player back a bit in opposite direction
                    player.position.x = player.position.x - .2f;
                    player.position.y = player.position.y - .2f;
                    player.stop();

                }
            }
        }
    }
}
