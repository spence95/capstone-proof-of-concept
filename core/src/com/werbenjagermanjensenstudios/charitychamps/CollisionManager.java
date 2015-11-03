package com.werbenjagermanjensenstudios.charitychamps;

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
        updateExplosions();
    }

    private void updateExplosions() {
        //only kill if in running mode
        if(!world.isSetting) {
            for(int i = 0; i < world.explosions.size(); i++){
            Explosion e = world.explosions.get(i);
            for(int p = 0; p < world.players.size(); p++){
                Player pl = world.players.get(p);
                if(pl.bounds.overlaps(e.bounds)){
                        if(!pl.isImmune) {
                            pl.takeHit(1);
                            pl.isImmune = true;
                        }
                    }
                }
            }
        }
    }

    private void updateBlockCollisions(){
        for(int playerIndex = 0; playerIndex < world.players.size(); playerIndex++){
            Player player = world.players.get(playerIndex);
            for(int blockIndex = 0; blockIndex < world.blocks.size(); blockIndex++){
                Block block = world.blocks.get(blockIndex);
                if(player.bounds.overlaps(block.bounds)){
                    //bump player back a bit in opposite direction
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
                    float bumpbackTotal = 15;
                    if(dx > px) {
                        degrees = Math.toDegrees(Math.atan(yDist / xDist));
                        bumpbackY = (float)(degrees/90) * bumpbackTotal;
                        bumpbackX = bumpbackTotal - bumpbackY;
                    } else {
                        degrees = Math.toDegrees(Math.atan(xDist / yDist));
                        bumpbackX = (float)(degrees/90) * bumpbackTotal;
                        bumpbackY = bumpbackTotal - bumpbackX;
                    }

                    //the actual bumping back of the player
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
                    player.stop();
                    //reset current move destination to current position instead of original touch
                    if(world.isSetting) {
                        player.actions.get(player.turnCounter).x = player.position.x;
                        player.actions.get(player.turnCounter).y = player.position.y;
                    }

                }
            }
            //update that players bullet too
            if(player.bullet.isShot){
                Bullet bullet = player.bullet;
                for(int i = 0; i < world.blocks.size(); i++) {
                    Block block = world.blocks.get(i);
                    if (bullet.bounds.overlaps(block.bounds)) {
                        Explosion exp = new Explosion(bullet.position.x, bullet.position.y);
                        world.explosions.add(exp);
                        bullet.reset();
                    }
                }
                for(int i = 0; i < world.players.size(); i++){
                    Player otherPl = world.players.get(i);
                    if (player != otherPl){
                        if(bullet.bounds.overlaps(otherPl.bounds)){
                            Explosion exp = new Explosion(bullet.position.x, bullet.position.y);
                            world.explosions.add(exp);
                            bullet.reset();
                        }
                    }
                }
            }
        }
    }

}
