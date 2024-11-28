package com.example.flappybird;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class Bird {

    // Bird Textures, Colors and Sound
    public static HashMap<String, Texture> birdTextures = new Sprites().getBirdTextures();
    public static Audio audio = new Audio();

    public final int BIRD_WIDTH = 44;
    public final int BIRD_HEIGHT = 31;
    private final int BASE_COLLISION = 521 - BIRD_HEIGHT - 5;
    private final int SHIFT = 10;
    private final int STARTING_BIRD_X = 90;
    private final int STARTING_BIRD_Y = 343;

    // Bird attributes
    public String color;
    String[] colors = {"yellow", "blue", "red"};
    private int x, y;
    private boolean isAlive = true;
    private final Random rand = new Random();
    // Bird constants
    private int FLOAT_MULTIPLIER = -1;
    // Physics variables
    private double velocity = 0;
    private final double gravity = .41;
    private double delay = 0;
    private double rotation = 0;
    // Bird sprites
    private final BufferedImage[] sprites;

    public Bird(int x, int y) {
        this.color = colors[rand.nextInt(colors.length)];
        this.x = x;
        this.y = y;
        this.sprites = new BufferedImage[]{
                birdTextures.get(color + "Bird1").getImage(),
                birdTextures.get(color + "Bird2").getImage(),
                birdTextures.get(color + "Bird3").getImage()};
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public boolean isAlive() {return isAlive;}

    public void setGameStartPos() {
        x = STARTING_BIRD_X;
        y = STARTING_BIRD_Y;
    }

    public void menuFloat() {
        y += FLOAT_MULTIPLIER;
        if (FLOAT_MULTIPLIER < 220 || FLOAT_MULTIPLIER > 280) {
            FLOAT_MULTIPLIER *= -1;
        }
    }

    public void kill() {
        audio.hit();
        isAlive = false;
    }

    public void jump() {
        audio.jump();

        if (delay < 1) {
            velocity = -SHIFT;
            delay = SHIFT;
        }
    }

    public int score(int score){
        audio.point();
        return score+1;
    }

    /**
     * Bird movement during the game
     */
    public void inGame() {

        // If the bird did not hit the base, lower it
        if (y < BASE_COLLISION) {

            // Change and velocity
            velocity += gravity;

            // Lower delay if possible
            if (delay > 0) {
                delay--;
            }

            // Add rounded velocity to y-coordinate
            y += (int) velocity;

        } else {
            // Play audio and set state to dead
            audio.hit();
            isAlive = false;
        }
    }

    /**
     * Renders bird
     */
    public void renderBird(Graphics g) {

        // Calculate angle to rotate bird based on y-velocity
        rotation = ((90 * (velocity + 25) / 25) - 90) * Math.PI / 180;

        // Divide for clean jump
        rotation /= 2;

        // Handle rotation offset
        rotation = Math.min(rotation, Math.PI / 2);

        if (!isAlive()) {

            // Drop bird on death
            if (y < BASE_COLLISION - 10) {
                velocity += gravity;
                y += (int) velocity;
            }

        }
        // Create bird animation and pass in rotation angle
        Animation.animate(g, sprites, x, y, .09, rotation);
    }
}
