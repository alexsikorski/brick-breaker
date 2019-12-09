package assignment2;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();     // making game frame
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setBounds(10, 10, 1000, 600); // game is 700 width, rest is 300
        gameFrame.setTitle("Block Breaker by 1603930");

        Game game = new Game(); // adding Game object to the frame
        gameFrame.add(game);
    }
}
