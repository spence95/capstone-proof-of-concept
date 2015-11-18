package com.werbenjagermanjensenstudios.charitychamps;

import com.werbenjagermanjensenstudios.charitychamps.gameobjects.Block;
import com.werbenjagermanjensenstudios.charitychamps.gameobjects.CrumblingBlock;

/**
 * Created by spence95 on 11/14/2015.
 */
public class MapManager {
    World world;
    float offset;
    public MapManager(World world, float offset){
        this.world = world;
        this.offset = offset;
    }

    public void setMap(){
        //TODO: Call out to server for map json here

        //mocked data

    }

    public void setNineBlockSquare(float x, float y){
        //9 block square
        Block block = new Block(x + offset, y, 10, 10);
        world.blocks.add(block);
        block = new Block(x + offset, y + 10, 10, 10);
        world.blocks.add(block);
        block = new Block(x + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 10 + offset, y, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 20 + offset, y, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 10 + offset, y + 10, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 10 + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 20 + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new Block(x + 20 + offset, y + 10, 10, 10);
        world.blocks.add(block);
    }

    public void setCrumblingNineBlockSquare(float x, float y){
        //9 block square
        Block block = new CrumblingBlock(x + offset, y, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + offset, y + 10, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 10 + offset, y, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 20 + offset, y, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 10 + offset, y + 10, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 10 + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 20 + offset, y + 20, 10, 10);
        world.blocks.add(block);
        block = new CrumblingBlock(x + 20 + offset, y + 10, 10, 10);
        world.blocks.add(block);
    }

    public void setWall(float x, float y, float width, boolean sideways){
        Block block;
        for(int i = 0; i < width; i++){
            if(sideways) {
                block = new Block(x + offset + i * 10, y, 10, 10);
            } else {
                block = new Block(x + offset, y + i * 10, 10, 10);
            }
            world.blocks.add(block);
        }
    }

    public void setCrumblingWall(float x, float y, float width, boolean sideways){
        CrumblingBlock block;
        for(int i = 0; i < width; i++){
            if(sideways) {
                block = new CrumblingBlock(x + offset + i * 10, y, 10, 10);
            } else {
                block = new CrumblingBlock(x + offset, y + i * 10, 10, 10);
            }
            world.blocks.add(block);
        }
    }

}
