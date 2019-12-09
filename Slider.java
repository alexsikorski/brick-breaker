package assignment2;

import java.awt.*;

public class Slider extends Shape {

    Slider(int width, int height, int posX, int posY) {
        super(width, height);   // width and height stay consistent/define the shape and therefore are supers

        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
    }

    void draw(Graphics graphics) {   // draws the shape
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(posX, posY, width, height);
    }
}
