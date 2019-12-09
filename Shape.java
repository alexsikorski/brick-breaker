package assignment2;


import java.awt.*;

public abstract class Shape {

    int width, height, posX, posY;  // variables used to draw the shape

    Shape(int width, int height) {   // constructor
        this.width = width;
        this.height = height;
    }

    abstract void draw(Graphics graphics);  // abstract method with graphics in order to draw

}
