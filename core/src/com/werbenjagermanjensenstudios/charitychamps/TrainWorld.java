package com.werbenjagermanjensenstudios.charitychamps;

import java.util.ArrayList;

/**
 * Created by spence95 on 11/10/2015.
 */
public class TrainWorld extends World{
    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 480;
    public final float squareWidth = 12;
    public final float squareHeight = 24;

    public ArrayList<Player> players;
    public ArrayList<Block> blocks;
    float lastTouchedX;
    float lastTouchedY;
    public Player currentPlayer;

    public ActionButton moveActionButton;
    public ActionButton attackActionButton;

    public CollisionManager collisionManager;
    public ScreenController screenController;
    proofOfConcept game;

    public TrainWorld(proofOfConcept game) {
        super(game, 1);
        moveActionButton = new ActionButton(ActionButton.moveName, true, 20, 100, 40, 40);
        attackActionButton = new ActionButton(ActionButton.attackName, false, 20, 150, 40, 40);
        blocks = new ArrayList<Block>();
    }

    public void touched(float x, float y){
        if(moveActionButton.bounds.contains(x, y)){
            moveActionButton.toggleActive();
            attackActionButton.toggleActive();
        }
        else if(attackActionButton.bounds.contains(x, y)){
            attackActionButton.toggleActive();
            moveActionButton.toggleActive();
        } else {
            bringUpMenu(x, y);
            if(!currentPlayer.isDone) {
                ActionButton activeButton = getActiveButton();
                System.out.println("ACTIVE BUTTON: ");
                System.out.println(activeButton.getActionName());
                if (activeButton.getActionName() == ActionButton.moveName) {
                    moveClicked(lastTouchedX, lastTouchedY);
                    //moveClicked(x, y);
                } else {
                    attackClicked(lastTouchedX, lastTouchedY);
                    //attackClicked(x, y);
                }
            }
        }
    }

    private void bringUpMenu(float x, float y){
        //only bring up when player isn't moving already
        if(this.currentPlayer.velocity != null) {
            if (this.currentPlayer.velocity.x == 0 && this.currentPlayer.velocity.y == 0) {
                lastTouchedX = x;
                lastTouchedY = y;
//                dot.position.x = x;
//                dot.position.y = y;
                //if hidden
//                if (this.actionMenu.state == 0 && this.actionMenu.position.y < 0) {
//                    this.actionMenu.changeState(1);
//                    this.actionMenu.isShown = true;
//                }
            }
        }
    }

    public void moveClicked(float x, float y){
        //if player is in middle of move update that move's ending to current position
        if(!this.currentPlayer.isMoving) {
//            this.currentPlayer.stop();
//            this.currentPlayer.actions.get(this.currentPlayer.turnCounter).x = this.currentPlayer.position.x;
//            this.currentPlayer.actions.get(this.currentPlayer.turnCounter).y = this.currentPlayer.position.y;

            this.currentPlayer.addMove(x, y);
        }
    }

    public void attackClicked(float x, float y) {
        if(this.currentPlayer.bullet.isShot == false) {
            Attack at = new Attack(x, y, 9);
            this.currentPlayer.addAttack(at);
            this.currentPlayer.bullet.shoot(x, y, this.currentPlayer.position.x, this.currentPlayer.position.y);
        }
    }

    public ActionButton getActiveButton(){
        if(moveActionButton.isActive){
            return moveActionButton;
        } else {
            return attackActionButton;
        }
    }

    public void start(){
        currentPlayer = new Player(game.getPlayerID(), squareWidth, squareHeight, false);
        players.add(currentPlayer);
    }

    public void update(float deltaTime) {

    }

        public void placeBlocks(){
        placeOutsideWalls();
        Block block = new Block(350 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 250, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 260, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 270, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 230, 10, 10);
        blocks.add(block);
        block = new Block(350 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(360 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(370 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(380 + 20, 280, 10, 10);
        blocks.add(block);


        block = new Block(450 + 20, 250, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 260, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 270, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 280, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 240, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 230, 10, 10);
        blocks.add(block);
        block = new Block(450 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(440 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(430 + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(420 + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(200  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(210  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(220  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(230  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(240  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(600  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(590  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(580  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(570  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(560  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(40  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(50  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(60  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(70  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(80  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(770  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(760  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(750  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(740  + 20, 220, 10, 10);
        blocks.add(block);
        block = new Block(730  + 20, 220, 10, 10);
        blocks.add(block);

        block = new Block(400  + 20, 10, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 20, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 30, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 40, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 50, 10, 10);
        blocks.add(block);

        block = new Block(400  + 20, 470, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 460, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 450, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 440, 10, 10);
        blocks.add(block);
        block = new Block(400  + 20, 430, 10, 10);
        blocks.add(block);

    }

    private void placeOutsideWalls(){
        Block block = new Block((WORLD_WIDTH / 2)+48, 0, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block((WORLD_WIDTH / 2)+48, WORLD_HEIGHT, WORLD_WIDTH, 10);
        blocks.add(block);
        block = new Block(48, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
        block = new Block(WORLD_WIDTH, WORLD_HEIGHT / 2, 10, WORLD_HEIGHT);
        blocks.add(block);
    }
}
