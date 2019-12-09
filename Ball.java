package assignment2;

import java.awt.*;

public class Ball extends Shape {

    Ball(int width, int height, int posX, int posY) {
        super(width, height);

        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
    }

    void draw(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.fillOval(posY, posX, width, height);
    }
}
