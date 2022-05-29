//package com.company;


import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Paddle {
    int x;
    int y;
    Image myimage;
    int oldX;
    int oldY;
    int xSpeed;
    int ySpeed;
    public Paddle(int newx, int newy) {
        //get the initial x and y positions of the paddle
        x = newx;
        y = newy;
        //record the oldX and oldY to get the speed in the future
        oldX = x;
        oldY = y;
        // import the image
        try {
            // import the image
            myimage = ImageIO.read(new File("paddle.PNG"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // move the paddle
    public void setLoc(int newx, int newy) {
        x = newx;
        y = newy;
    }
    //get x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // draw the paddle
    public void paintMe(Graphics g) {
        g.drawImage(myimage, x-50, y-75, null);
    }
    //get speed using by doing delta x and delta y
    public int getXSpeed() {
        xSpeed = x - oldX;
        oldX = x;
        //System.out.println(xSpeed);
        return xSpeed;

    }
    //get
    public int getYSpeed() {
        ySpeed = y - oldY;
        oldY = y;
        return ySpeed;
    }
    //get the aiXSpeed
    public int getAiXSpeed(){
        return xSpeed;
    }
    //get the aiYSpeed
    public int getAiYSpeed(){
        return ySpeed;
    }
    //set the x and y speed for the ai
    public void setXSpeed(int newSpeed) {
    	xSpeed = newSpeed;
    }
    public void setYSpeed(int newSpeed) {
    	ySpeed = newSpeed;
    }

}