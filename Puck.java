//package com.company;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Puck {
    int x = 0;
    int y = 0;
    int xSpeed = 0;
    int ySpeed = 0;
    int speed = 0;
    Image myimage;
    double angle = 0;

    public Puck(int newx, int newy) {
        x = newx;
        y = newy;
        // import the image
        try {
            // import the image
            myimage = ImageIO.read(new File("puck.PNG"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // draw the puck

    public void paintMe(Graphics g) {
        g.drawImage(myimage, x-42 , y-65, null);
    }
    //set the location
    public void setLoc(int newx, int newy) {
        x = newx;
        y = newy;
    }
    //get the x and y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //check for collision using distance
    public boolean hitPaddle(int Mousex, int Mousey) {
        double distance = Math.sqrt(Math.pow(Mousey - y, 2) + Math.pow(Mousex - x, 2));
        if (distance <= 72) {
            return true;
        }
        return false;
    }
    //get the x and y speed
    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }
    //set the x and y speed
    //set a max speed limit so the puck does not move to fast
    public void setXSpeed(int newSpeed) {
    	
        int sign = Integer.signum(newSpeed);
        if(sign>0){
            xSpeed = Math.min(5, newSpeed);
        }
        else{
            xSpeed = Math.max(-5, newSpeed);
        }
    }

    public void setYSpeed(int newSpeed) {
    	
        int sign = Integer.signum(newSpeed);
        if(sign>0){
            ySpeed = Math.min(5, newSpeed);
        }
        else{
            ySpeed = Math.max(-5, newSpeed);
        }

    }



}