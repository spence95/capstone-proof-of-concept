package com.werbenjagermanjensenstudios.charitychamps;

import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Block;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Bullet;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Explosion;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Mine;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Player;

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
        //updateBullets();
        updateExplosions();
        updatePowerups();
        updateMines();
    }

    private void updateBullets(){
        for(int i = 0; i < world.players.size(); i++){
            Player pl = world.players.get(i);
            Bullet bu = pl.bullet;
            for(int j = 0; j < world.players.size(); j++){
                Player opl = world.players.get(j);
                if(pl.id != opl.id){
                    if(opl.bounds.overlaps(bu.bounds)){
                        if(!opl.isImmune){
                            opl.takeHit(1);
                            opl.isImmune = true;
                        }
                    }
                }
            }
        }
    }

    private void updatePowerups() {
        for(int p = 0; p < world.players.size(); p++){
            for(int i = 0; i < world.powerups.size(); i++){
                if(world.players.get(p).bounds.overlaps(world.powerups.get(i).bounds)){
                    if(!world.isSetting) {
                        world.powerups.get(i).absorbed(world.players.get(p));
                    }
                }
            }
        }
    }

    private void updateExplosions() {
        //only kill if in running mode
        if(!world.isSetting) {
//            Explosion dummyEx = new Explosion(-1000, -1000);
//            world.explosions.add(dummyEx);
            for(int i = 0; i < world.explosions.size(); i++){
            Explosion e = world.explosions.get(i);
            for(int p = 0; p < world.players.size(); p++){
                Player pl = world.players.get(p);
                if(pl.bounds.overlaps(e.bounds)){
                        playerHit(pl);
                    }
                }
            }
        }
    }

    private void updateMines() {
        if(!world.isSetting){
            for(int i = 0; i < world.mines.size(); i++){
                Mine m = world.mines.get(i);
                for(int p = 0; p < world.players.size(); p++){
                    Player pl = world.players.get(p);
                    if(pl.bounds.overlaps(m.bounds)){
                        if(m.exploded == false){
                            m.exploded = true;
                            Explosion e = new Explosion(m.position.x, m.position.y);
                            world.explosions.add(e);
                        }
                    }
                }
            }
        }
    }

    private void playerHit(Player pl){
        if(!pl.isImmune) {
            pl.takeHit(1);
            pl.isImmune = true;
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
                    float bumpbackTotal = 25;
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
                   // if(world.isSetting) {
//                        player.actions.get(player.turnCounter).x = player.position.x;
//                        player.actions.get(player.turnCounter).y = player.position.y;
                        player.actions.get(player.actions.size() - 1).x = player.position.x;
                        player.actions.get(player.actions.size() - 1).y = player.position.y;
                   // }

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
