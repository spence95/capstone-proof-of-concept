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
                    //if px < dx && py < dy
                    //if px < dx py> dy
                    //if px > dx py < dy
                    //if px > dx py > dy
                    float px = player.position.x;
                    float dx = player.destination.x;
                    float py = player.position.y;
                    float dy = player.destination.y;

                    //split up the x bump back and y bump back proportionally based on movement
                    float xDist = Math.abs(px - dx);
                    float yDist = Math.abs(py - dy);
                    double degrees;
                    float bumpbackX;
                    float bumpbackY;
                    float bumpbackTotal = 10;
                    if(dx > px) {
                        degrees = Math.toDegrees(Math.atan(yDist / xDist));
                        bumpbackY = (float)(degrees/90) * bumpbackTotal;
                        bumpbackX = bumpbackTotal - bumpbackY;
                    } else {
                        degrees = Math.toDegrees(Math.atan(xDist / yDist));
                        bumpbackX = (float)(degrees/90) * bumpbackTotal;
                        bumpbackY = bumpbackTotal - bumpbackX;

                    }

                    if(px < dx){
                        if(py < dy){
                            player.position.x = player.position.x - bumpbackX;
                            player.position.y = player.position.y - bumpbackY;
                            player.stop();
                        }
                        else if(py > dy){
                            player.position.x = player.position.x - bumpbackX;
                            player.position.y = player.position.y + bumpbackY;
                            player.stop();
                        }
                    }
                    else if (px > dx){
                        if(py < dy){
                            player.position.x = player.position.x + bumpbackX;
                            player.position.y = player.position.y - bumpbackY;
                            player.stop();
                        }
                        else if(py > dy){
                            player.position.x = player.position.x + bumpbackX;
                            player.position.y = player.position.y + bumpbackY;
                            player.stop();
                        }
                    }

                    //stop the player
                    //reset current move destination to current position instead of original touch
                    if(world.isSetting) {
                        player.actions.get(player.turnCounter).x = player.position.x;
                        player.actions.get(player.turnCounter).y = player.position.y;
                    }

                }
            }
        }
    }
}
