package com.example.flappybird;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class Bird{

	// Textures and Bird Color
	public static HashMap<String, Texture> textures = new Sprites().getGameTextures();
	private final Random rand = new Random();
	String[] colors = {"yellow", "blue", "red"};

	// Bird attributes
	public String color;
	private int x, y;
	private boolean isAlive = true;
	
	// Bird constants
	private int FLOAT_MULTIPLIER      = -1;
	public final int BIRD_WIDTH       = 44;
	public final int BIRD_HEIGHT      = 31;
	private final int BASE_COLLISION  = 521 - BIRD_HEIGHT - 5;
	private final int SHIFT           = 10;
	private final int STARTING_BIRD_X = 90;
	private final int STARTING_BIRD_Y = 343;
	
	// Physics variables
	private double velocity           = 0;
	private double gravity            = .41;
	private double delay              = 0;
	private double rotation           = 0;
	private static double currentFrame = 0;

	// Bird sprites and Sounds
	public static Audio audio   = new Audio();
	private BufferedImage[] sprites;

	public Bird(int x, int y) {
		this.color = colors[rand.nextInt(colors.length)];
		this.x = x;
		this.y = y;
		this.sprites = new BufferedImage[]{
				textures.get(color + "Bird1").getImage(),
				textures.get(color + "Bird2").getImage(),
				textures.get(color + "Bird3").getImage()};
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public boolean isAlive () {
		return isAlive;
	}

	public void kill () {
		isAlive = false;
		audio.hit();
	}

	public void setGameStartPos () {
		x = STARTING_BIRD_X;
		y = STARTING_BIRD_Y;
	}

	public void menuFloat () {
		y += FLOAT_MULTIPLIER;

		if (y < 220 ||  y > 280) {
			FLOAT_MULTIPLIER *= -1;
		}
	}

	public void jump () {
		if (delay < 1) {
			velocity = -SHIFT;
			delay = SHIFT;
		}
		audio.jump();
	}

	public void score(){
		audio.point();
	}

	public void inGame () {

		if (y < BASE_COLLISION) {
			velocity += gravity;

			if (delay > 0) { delay--; }

			y += (int) velocity;
		} else {
			audio.hit();
			isAlive = false;
		}
	}

	public static void animate (Graphics g, BufferedImage[] sprites, int x, int y, double speed, double angle) {

		Graphics2D g2d        = (Graphics2D) g;
		AffineTransform trans = g2d.getTransform();
		AffineTransform at    = new AffineTransform();

		// Number of frames
		int count = sprites.length;

		// Rotate the image
		at.rotate(angle, x + 25, y + 25);
		g2d.transform(at);

		// Draw the current rotated frame
		g2d.drawImage(sprites[(int) (Math.round(currentFrame))], x, y, null);

		g2d.setTransform(trans);

		// Switch animation frames
		if (currentFrame >= count - 1) {
			currentFrame = 0;
		} else currentFrame += speed;
	}

	public void renderBird (Graphics g) {

		rotation = ((90 * (velocity + 25) / 25) - 90) * Math.PI / 180;
		
		rotation /= 2;

		rotation = Math.min(rotation, Math.PI / 2);

		if (!isAlive()) {
			if (y < BASE_COLLISION - 10) {
				velocity += gravity;
				y += (int) velocity;
			}
		}
		animate(g, sprites, x, y, .09, rotation);
	}
}
