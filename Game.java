package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener {    // class made for gameplay, uses action listener and key listener for user input

    public int finalScore = 0;
    public boolean ended; // to determine if game has ended
    private boolean ranOnce = false; // is used to run file writer method ONCE after game has ended (timer makes it run multiple times)
    private boolean start = false;  // boolean to determine if the game has started/is running
    private int totalBlocks = 21;   // game starts with 21 blocks
    private int currentScore = 0;   // starts at 0 score
    private Timer time;             // sets timer
    private int delay = 5;          // delay for timer

    // starting position of slider
    private int sliderX = 310;

    // starting position of ball
    private int ballX = 120;
    private int ballY = 350;

    // direction of ball initially
    private int directionX = -1;
    private int directionY = -2;

    // created object for blocks
    private Blocks blocks;
    // class run to check if game ended, if so update txt


    public Game() {

        blocks = new Blocks(3, 7);  // created new object instance for blocks

        addKeyListener(this);   // adds keyboard listener
        addMouseListener(this); // adds a mouse listener

        setFocusable(true); // allows object to gain focus when requested to do so
        setFocusTraversalKeysEnabled(false);

        time = new Timer(delay, this);  // generate new timer for instance
        time.start();  // start the timer


    }

    public void paint(Graphics graphics) {  // drawing the game and shapes involved
        FileReader returnList = new FileReader();   // creates object for reading txt list
        ArrayList<Integer> freshList = returnList.OpenAndRead();
        Collections.sort(freshList, Collections.reverseOrder());    // sorts descending


        if (ended) {
            this.finalScore = currentScore;  // sets final score
        }

        graphics.setColor(Color.BLACK);    // BACKGROUND
        graphics.fillRect(1, 1, 1000, 600);

        // CALLING FOR BLOCKS.JAVA TO DRAW BLOCKS
        blocks.draw((Graphics2D) graphics);

        graphics.setColor(Color.GREEN);    // BORDERS ADDED
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 1000, 3);
        graphics.fillRect(691, 0, 3, 592);

        // BORDERS OUTSIDE THE GAME
        graphics.fillRect(991, 0, 3, 592);
        graphics.fillRect(691, 568, 300, 3);

        // no bottom border added as once the ball passes the bottom the user loses the game

        // slider using abstract class
        Shape sliderShape;
        sliderShape = new Slider(100, 8, sliderX, 550);
        sliderShape.draw(graphics);

        // ball using abstract class
        Shape ballShape;
        ballShape = new Ball(20, 20, ballY, ballX);
        ballShape.draw(graphics);

        // displays leaderboard
        graphics.setColor(Color.YELLOW);   // INSTRUCTIONS
        graphics.setFont(new Font("arial", Font.BOLD, 14));
        graphics.drawString("Leaderboard:", 720, 160);
        graphics.setFont(new Font("arial", Font.BOLD, 20));

        int textSpaceY = 170;
        for (int x = 0; x < 10; x++) {
            textSpaceY = textSpaceY + 20;   // spaces out the text
            graphics.drawString((x + 1) + " Place:       " + freshList.get(x), 720, textSpaceY);
        }

        // GAME CONDITIONS
        if (ended == true && ranOnce == false) {  //  write current score down as one entry
            time.stop();
            System.out.println("ended = true and WriteCurrentScore() is ran once, score appended to text file is: " + finalScore);
            FileWriter fW = new FileWriter();
            fW.WriteCurrentScore(finalScore);
            ranOnce = true;

        } else time.restart();
        ranOnce = false;


        if (totalBlocks <= 0) {
            start = false;  // stops the game and shows message
            ended = true;
            directionX = 0;
            directionY = 0;
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("arial", Font.PLAIN, 40));
            graphics.drawString("You Won!", 210, 300);
            graphics.setFont(new Font("arial", Font.PLAIN, 20));
            graphics.drawString("your score: " + currentScore, 230, 330);
            graphics.drawString("Press Enter to restart!", 230, 370);


        }


        if (ballY > 570) {   // GAME OVER
            start = false;  // stops the game and shows message
            ended = true;
            directionX = 0;
            directionY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("arial", Font.PLAIN, 40));
            graphics.drawString("Game Over!", 210, 300);
            graphics.setFont(new Font("arial", Font.PLAIN, 20));
            graphics.drawString("your score: " + currentScore, 230, 330);
            graphics.drawString("Press Enter to restart!", 230, 370);


        }

        graphics.setColor(Color.WHITE);   // SCORE ADDED
        graphics.setFont(new Font("arial", Font.PLAIN, 20));
        graphics.drawString("Your score: " + currentScore, 780, 50);


        //TESTING
        graphics.setColor(Color.DARK_GRAY);
        graphics.setFont(new Font("arial", Font.PLAIN, 9));
        graphics.drawString("Final score: " + finalScore, 600, 20);
        graphics.drawString("Has ended? " + ended, 500, 20);
        graphics.drawString("Has started? " + start, 400, 20);
        //TESTING

        // INSTRUCTIONS
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.PLAIN, 12));
        graphics.drawString("Controls: Left click on mouse and left arrow", 720, 85);
        graphics.drawString("on keyboard to go left.", 720, 100);
        graphics.drawString("Right click on mouse and right arrow to go", 720, 115);
        graphics.drawString("right. Press any stated above to start.", 720, 130);
        graphics.dispose();
    }


    // KEYBOARD

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();   // starts timer

        // what the ball should do when interacting with other objects
        if (start) {     // if game is currently running

            // intersection between slider and ball
            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(sliderX, 550, 100, 8))) {
                directionY = -directionY;   // changes direction of the ball
            }

            // action for intersection between ball and blocks
            a:
            for (int i = 0; i < blocks.blocks.length; i++) {
                for (int j = 0; j < blocks.blocks[0].length; j++) {
                    if (blocks.blocks[i][j] > 0) {    // if block value is above 0 then it has not been intersected before

                        // setting variables
                        int blockX = j * blocks.bWidth + 80;
                        int blockY = i * blocks.bHeight + 50;
                        int blockWidth = blocks.bWidth;
                        int blockHeight = blocks.bHeight;

                        // creating rectangle which is used to detect intersection between ball and block
                        Rectangle r = new Rectangle(blockX, blockY, blockWidth, blockHeight);
                        // creating rectangle around ball to detect collision
                        Rectangle ballR = new Rectangle(ballX, ballY, 20, 20);

                        Rectangle blockR = r;

                        if (ballR.intersects(blockR)) {   // if ball intersections(collides) with blocks
                            blocks.setBlockValue(0, i, j);  // for position i,j set value to 0 (intersected)

                            totalBlocks--;  // remove from total every time ball collides

                            currentScore += 100; // add 100 points for hitting a block

                            if (ballX + 19 <= blockR.x || ballX + 1 >= blockR.x + blockR.width) {     // if ball hits the top OR bottom of the blocks
                                directionX = -directionX;    // switch X direction
                            } else {
                                directionY = -directionY;   // otherwise switch Y direction (when ball hits the sides)
                            }

                            break a;    // break label added to break the entire loop
                        }

                    }

                }
            }
            ballX += directionX;    // changes position of ball due to direction
            ballY += directionY;

            if (ballX < 0) {      // LEFT BORDER DIRECTION CHANGE
                directionX = -directionX;
            }
            if (ballY < 0) {      // TOP BORDER DIRECTION CHANGE
                directionY = -directionY;
            }
            if (ballX > 670) {      // RIGHT BORDER DIRECTION CHANGE
                directionX = -directionX;
            }
        }
        /////////////
        repaint();      // repaints everything again as shapes change position due to increments

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {    // restart time after ENTER is pressed (timer is stopped to allow FileWriter WriteCurrentScore() to run only ONCE)
            requestFocus();
            time.restart();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !ended) {   // if right arrow key is pressed
            requestFocus();
            if (sliderX >= 600) { // checks that slider is not outside the border
                sliderX = 600;
            } else {
                goRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !ended) {   // if left arrow key is pressed
            requestFocus();
            if (sliderX < 10) {  // check for opposite side of border
                sliderX = 10;   // when at 10 stay at 10
            } else {
                goLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {    // restart
            requestFocus();
            if (!start) {
                start = true;   // game restarts
                ended = false;
                ranOnce = false;

                finalScore = 0; // restart score relevant to leaderboard

                ballY = 350;    // resets position of objects and quantity of blocks
                ballX = 120;

                directionY = -2;
                directionX = -1;

                sliderX = 310;
                currentScore = 0;

                totalBlocks = 21;

                blocks = new Blocks(3, 7);

                repaint();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        requestFocus();
        if (e.getButton() == MouseEvent.BUTTON3 & !ended) {  // right
            requestFocus();
            if (sliderX >= 600) {
                sliderX = 600;
            } else {
                goRight();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1 & !ended) {  // left
            requestFocus();
            if (sliderX < 10) {
                sliderX = 10;
            } else {
                goLeft();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // METHODS FOR MOVING THE SLIDER
    public void goRight() {
        start = true;
        ended = false;
        sliderX += 20; // adds increments for every right keystroke
    }

    public void goLeft() {
        start = true;
        ended = false;
        sliderX -= 20; // takes away increments for every left keystroke
    }

}