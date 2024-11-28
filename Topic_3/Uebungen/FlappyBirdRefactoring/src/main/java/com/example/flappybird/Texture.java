package com.example.flappybird;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture {

    // Image attributes
    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int width;
    private final Rectangle rect;

    public Texture(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        int height = image.getHeight();
        this.rect = new Rectangle(x, y, width, height);
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRect() {
        return rect;
    }
}
