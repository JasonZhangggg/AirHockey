//package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Table {
    Image myimage;
    Puck puck;
    public Table(Puck newPuck) {
        puck = newPuck;
        try {
            //import the image
            myimage = ImageIO.read(new File("table.PNG"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //draw the table
    public void paintMe(Graphics g) {
        g.drawImage(myimage, 0, 0, null);
    }

    //check if the paddle is hitting the x boundaries
    public int hitX(int x) {
        if (x < 46) {
            return 46;
        }
        else if (x > 513) {
            return 513;
        }
        return x;
    }

    //check if the paddle is hitting the y boundaries

    public int hitY(int y) {
        if (y > 915) {
            return 915;
        }
        else if (y < 540) {
            return 540;
        }
        return y;
    }
    //make the puck bounce back if hit a wall
    public void walls(){
        //make sure that the puck is not in the goal
        if(!(puck.getX() > 185 && puck.getX() < 400 && puck.getY() > 912) && !(puck.getX() > 185 && puck.getX() < 400 && puck.getY() < 42)) {
            //if the puck hits on of the walls
            if (puck.getY() > 913 || puck.getY() < 55) {
                //reverse its speed
                puck.setYSpeed(-puck.getYSpeed());
            }
            //if the puck hits on of the walls
            if ((puck.getX() < 37 || puck.getX() > 536)) {
                //reverse its speed
                puck.setXSpeed(-puck.getXSpeed());
            }
        }
    }
}