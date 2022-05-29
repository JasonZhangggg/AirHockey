//package com.company;

public class Physics {
    Puck puck;
    Paddle paddle;

    Physics(Puck newPuck, Paddle newPaddle) {
        //get the puck and paddle objects
        puck = newPuck;
        paddle = newPaddle;
    }

    int m1 = 100;
    int m2 = 15;
    int sign = 1;
    //pseudo code from:
    //https://ericleong.me/research/circle-circle/
    public void ballCol(int xSpeed, int ySpeed) {
        //get puck and paddles locations
        int paddleX = paddle.getX();
        int paddleY = paddle.getY();
        int puckX = puck.getX();
        int puckY = puck.getY();
        //find the angle between the puck and paddle
        double angle = Math.atan((double) (paddleY - puckY) / (double) (paddleX - puckX));
        //find the distance needed to move the puck out of the paddle to avoid them overlapping
        double moveDist = (72) - Math.sqrt((puckX - paddleX) * (puckX - paddleX) + (puckY - paddleY) * (puckY - paddleY));
        sign = Integer.signum(paddleX - puckX);
        //set the puck's location to the edge of the paddle where they collide
        puck.setLoc((int) (puckX - sign * (Math.cos(angle) * moveDist / 2.0)), (int) (puckY - sign * (Math.sin(angle) * moveDist / 2.0)));
        //System.out.println("1 " + puckX + ", " + puckY);
        //System.out.println("2 " + (int) (puckX + Math.cos(angle) * moveDist / 2.0) + ", " + (int) (puckY + Math.sin(angle) * moveDist / 2.0));
        //get the new puck x and y
        puckX = puck.getX();
        puckY = puck.getY();
        //get distance between them
        double d = Math.sqrt(Math.pow(paddleX - puckX, 2) + Math.pow(paddleY - puckY, 2));
        double nx = (puckX - paddleX) / d;
        double ny = (puckY - paddleY) / d;
        //calculate the x vector and y vector and set the puck speed to that
        double p = 2 * (xSpeed * nx + ySpeed * ny - puck.getXSpeed() * nx - puck.getYSpeed() * ny) / (m1 + m2);
        puck.setXSpeed((int) (puck.getXSpeed() + p * m1 * nx));
        puck.setYSpeed((int) (puck.getYSpeed() + p * m1 * ny));
    }
}