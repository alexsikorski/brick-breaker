package assignment2;

import java.awt.*;

public class Blocks {
    public int blocks[][];  // setting 2d array for generating blocks

    public int bWidth; // setting variable for the width of the blocks
    public int bHeight; // setting variable for height of blocks

    public Blocks(int row, int col) {
        blocks = new int[row][col];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j] = 1;   // setting the element value to 1, this determines that block has not been hit (intersected) by the ball
            }
        }
        bWidth = 540 / col; // sets width and height of each individual block
        bHeight = 150 / row;
    }

    public void draw(Graphics2D graphics) {  // drawing the blocks
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {

                if (blocks[i][j] > 0) {   // if any blocks left
                    graphics.setColor(Color.RED);
                    graphics.fillRect(j * bWidth + 80, i * bHeight + 50, bWidth, bHeight);  // draws filled rectangles

                    graphics.setStroke(new BasicStroke(4));
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect(j * bWidth + 80, i * bHeight + 50, bWidth, bHeight); // draws again but just the strokes(outlines)
                }
            }
        }
    }

    public void setBlockValue(int value, int row, int col) {
        blocks[row][col] = value;
    }
}
