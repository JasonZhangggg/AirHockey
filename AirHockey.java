/*Names: Kevin Seong and Jason Zhang
* Date: 6/6/2019
* Period: 5
* Project: Air Hockey
*/

//package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class AirHockey implements MouseListener, Runnable, ActionListener {
    //create all the objects including puck, paddle and ai paddle
    JFrame frame = new JFrame("Air Hockey");
    Paddle paddle = new Paddle(291, 744);
    Puck puck = new Puck(300, 500);
    Paddle aiPaddle = new Paddle(291, 239);
    Table table = new Table(puck);
    //ai and your score
    JLabel userScore = new JLabel("Your Score: 0");
    JLabel aiScore = new JLabel("AI Score: 0");
    int slowDown = 0;
    //create the board
    Board board = new Board(paddle, table, puck, aiPaddle);
    boolean mouseDown = false;
    Container south = new Container();
    //all variables
    int Mousex;
    int Mousey;
    Point mouse;
    int xspeed;
    int yspeed;
    int hitXSpeed;
    int hitYSpeed;
    int aiScoreNum = 0;
    int userScoreNum = 0;
    boolean justHit = false;
    JFrame introFrame = new JFrame();
    //easy medium and hard buttons
    JButton easy = new JButton("Easy");
    JButton medium = new JButton("Medium");
    JButton hard = new JButton("Hard");
    //create the thread
    Thread thread = new Thread(this);
    //physics engine for ai paddle and players paddle
    Physics physics = new Physics(puck, paddle);
    Physics aiPhysics = new Physics(puck, aiPaddle);
    //create a new ai
    AI ai = new AI(puck, aiPaddle);
    //create the text field and button to set the number of wins until game ends
    JTextField askWins= new JTextField("Wins util game end");
    JButton setWins = new JButton("Set");
    //flags for checking if both the set wins and difficulty buttons were pressed
    boolean flag = false;
    boolean flag2 = false;
    //number of wins for game to end
    int numForWins;
    public AirHockey() {
        //frame size and layouts
        frame.setSize(565, 965);
        frame.setLayout(new BorderLayout());
        frame.add(board, BorderLayout.CENTER);
        //create the intro frame with the difficulty and wins till end
        introFrame.setSize(600, 100);
        introFrame.setLayout(new GridLayout(1, 5));
        //add action listeners and textfields/buttons
        introFrame.add(askWins);
        introFrame.add(setWins);
        setWins.addActionListener(this);
        introFrame.add(easy);
        easy.addActionListener(this);
        introFrame.add(medium);
        medium.addActionListener(this);
        introFrame.add(hard);
        hard.addActionListener(this);
        introFrame.setVisible(true);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        south.add(userScore);
        south.add(aiScore);
        frame.add(south, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
        board.addMouseListener(this);
        //wait until both the the difficulty and the set wins have been clicked
        while(!flag || !flag2){
            System.out.print("");
        }

        introFrame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        thread.start();
        south.setLayout(new GridLayout(1, 2));
    }
    //thread that sets mouse location when mouse is clicked
    public void pointPos() {
        if (mouseDown) {
            new Thread() {
                public void run() {
                    while (mouseDown) {
                        //get mouse's location
                        mouse = MouseInfo.getPointerInfo().getLocation();
                        Mousex = (int) mouse.getX();
                        Mousey = (int) mouse.getY();
                        //make sure the paddle can not go through the wall
                        Mousex = table.hitX(Mousex);
                        Mousey = table.hitY(Mousey);
                        //set the paddle location to the mouse location
                        paddle.setLoc(Mousex, Mousey);
                        //get the speed
                        xspeed = paddle.getXSpeed();
                        yspeed = paddle.getYSpeed();
                        // System.out.println(xspeed+", "+yspeed);
                        frame.repaint();
                        try {
                            Thread.sleep(5);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // System.out.println(Mousex + ", " + Mousey +", "+ puck.getX());
                        // "+puck.getY()+", "+ paddle.getX()+", "+paddle.getY());
                    }
                }
            }.start();

        }
    }

    public static void main(String args[]) {
        AirHockey game = new AirHockey();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //if mouse is pressed
        mouse = MouseInfo.getPointerInfo().getLocation();
        Mousex = (int) mouse.getX();
        Mousey = (int) mouse.getY();
        // check if cursor is in paddle
        double distance = Math.sqrt(Math.pow(Mousey - paddle.getY(), 2) + Math.pow(Mousex - paddle.getX(), 2));
        //check if the mouse was clicked on the paddle
        if (distance <= 45) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                //if so set mouse down to true and run the pointPos thread
                mouseDown = true;
                pointPos();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //when mouse is released set mouse down to false.
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            //if the puck hits the wall, make the puck bounce
            table.walls();
            //if ai wins reset board and change the score
            if (puck.getX() > 185 && puck.getX() < 400 && puck.getY() > 912) {
                aiScoreNum++;
                puck.setXSpeed(0);
                puck.setYSpeed(0);
                mouseDown = false;
                puck.setLoc(300, 500);
                paddle.setLoc(291, 744);
                aiPaddle.setLoc(291, 239);
                //if player wins reset board and change the score
            } else if (puck.getX() > 185 && puck.getX() < 400 && puck.getY() < 56) {
                userScoreNum++;
                puck.setXSpeed(0);
                puck.setYSpeed(0);
                mouseDown = false;
                puck.setLoc(300, 500);
                paddle.setLoc(291, 744);
                aiPaddle.setLoc(291, 239);

            }
            //otherwise check for coalitions and update puck location
            else {
                //if the user's paddle hits the puck
                if (puck.hitPaddle(paddle.getX(), paddle.getY())
                        && (!(xspeed == 0 && yspeed == 0) || !(puck.getXSpeed() == 0 && puck.getYSpeed() == 0))) {
                    //run the physics engine with the hit speed
                    hitXSpeed = xspeed;
                    hitYSpeed = yspeed;
                    physics.ballCol(hitXSpeed, hitYSpeed);
                }
                //If the ai is close to the center
                if (260 <= aiPaddle.getX() && aiPaddle.getX() <= 320 && 200 <= aiPaddle.getY() && aiPaddle.getY() <= 270) {
                    //set justHit to false, which makes the ai go toward the puck
                    justHit = false;
                }
                //if the ai hit the puck
                if (puck.hitPaddle(aiPaddle.getX(), aiPaddle.getY())) {
                    //do the physics
                    aiPhysics.ballCol(aiPaddle.getAiXSpeed(), aiPaddle.getAiYSpeed());
                    //set justHit to true so it goes back to the center
                    justHit = true;
                }
                //if the ai paddle is going over the center line of the puck went over to the opponents side, make the ai paddle move back.
                if (aiPaddle.getY() > 512 || puck.getY() > 512) {
                    justHit = true;
                }
                //run the ai engine
                ai.move(justHit);
                //make the ai move
                aiPaddle.setLoc(aiPaddle.getX() + aiPaddle.getAiXSpeed(), aiPaddle.getY() + aiPaddle.getAiYSpeed());
                //set the puck location, and trim it so that it can not go through the wall
                puck.setLoc(Math.min(537, Math.max(puck.getX() + puck.getXSpeed(), 36)),
                        Math.min(914, Math.max(puck.getY() + puck.getYSpeed(), 54)));


            }
            //if ai reaches number of wins to win, print ai wins and reset score
            if(aiScoreNum == numForWins){
                JOptionPane.showMessageDialog(null, "Ai Won!", "Winner", JOptionPane.INFORMATION_MESSAGE);
                aiScoreNum = 0;
                userScoreNum = 0;
            }
            //if user reaches number of wins to win, print out user wins and reset score
            else if(userScoreNum == numForWins){
                JOptionPane.showMessageDialog(null, "You Won", "Winner", JOptionPane.INFORMATION_MESSAGE);
                userScoreNum=0;
                aiScoreNum = 0;
            }
            //output the ai and user score
            aiScore.setText("AI score: " + aiScoreNum);
            userScore.setText("Your score: " + userScoreNum);

            //System.out.println("Speed " + aiPaddle.getXSpeed() +", " + aiPaddle.getYSpeed() );
            frame.repaint();

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //if user clicked on easy, medium or hard, let the ai engine know and set flag to true, so we know that the difficulty has been selected
        if (e.getSource().equals(easy)) {
            flag = true;
            ai.easy();
        }
        if (e.getSource().equals(medium)) {
            flag = true;

            ai.medium();
        }
        if (e.getSource().equals(hard)) {
            flag = true;

            ai.hard();
        }

        if(e.getSource().equals(setWins)){
            String input = askWins.getText().toString();
            //check if the number for wins is a integer.
            //if not, say so
            //if so, set flag2 to true, and set numberForWins to the number in the textField
            try {
                numForWins = Integer.parseInt(input);
                flag2 = true;
            }
            catch (Exception a){
                System.out.println("Please enter a number");
            }

        }

    }
}