//package com.company;

public class AI {
    Puck puck;
    Paddle paddle;
    //different times for different difficulties
    int easySpeed = 30;
    int medSpeed = 20;
    int hardSpeed = 15;
    //variables needed
    int speed;
    int puckX;
    int puckY;
    int paddleX;
    int paddleY;
    int xDist;
    int yDist;

    public AI(Puck newPuck, Paddle newPaddle) {
        //get the puck and paddle
        puck = newPuck;
        paddle = newPaddle;
    }
    //set easy, med and hard speeds
    public void easy() {
        speed = easySpeed;
    }

    public void medium() {
        speed = medSpeed;
    }

    public void hard() {
        speed = hardSpeed;
    }

    public void move(boolean justHit) {
        puckX = puck.getX();
        puckY = puck.getY();
        paddleX = paddle.getX();
        paddleY = paddle.getY();
        //check if just hit is false. If it is, move the paddle toward the puck
        if (!justHit) {
            xDist = puckX - paddleX;
            yDist = puckY - paddleY;

        }
        //otherwise move it to the center
        else {
            xDist = 291 - paddleX;
            yDist = 239 - paddleY;
        }
        //set the ai paddle's speed
        paddle.setXSpeed(Math.round(xDist / speed));
        paddle.setYSpeed(Math.round(yDist / speed));
    }
}
