//package com.company;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    Paddle paddle;
    Table table;
    Puck puck;
    Paddle aiPaddle;
    public Board(Paddle newPaddle, Table newTable, Puck newPuck, Paddle newAi){
        super();
        //take in the puck, paddle, table and ai paddle
        puck = newPuck;
        paddle = newPaddle;
        table = newTable;
        aiPaddle = newAi;
    }
    public void paintComponent(Graphics g) {
        //draw everything
        super.paintComponent(g);
        table.paintMe(g);
        paddle.paintMe(g);
        puck.paintMe(g);
        aiPaddle.paintMe(g);

    }
}