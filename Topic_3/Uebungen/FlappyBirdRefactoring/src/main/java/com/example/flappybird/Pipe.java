package com.example.flappybird;

public class Pipe {

	private int x = FlappyBird.WIDTH + 5;
	private int y;

	String location;

	public static final int WIDTH         = 67;
	public static final int HEIGHT        = 416;
	public static final int PIPE_DISTANCE = 150;
	public static final int PIPE_SPACING  = HEIGHT + 170;
	private static final int SPEED        = -2;

	public boolean canAwardPoint = true;

	public Pipe (String location) {
		this.location = location;
		reset();
	}

	public void reset () {
		x = FlappyBird.WIDTH + 5; // Reset x-coordinate

		if (location.equals("top")) {
			y = - Math.max((int) (Math.random() * 320) + 30, 140);
		}
	}

	public void move () {
		x += SPEED;
	}

	public boolean collide (int nX, int nY, int nW, int nH) {

		if (nX > x && nY < 0 && canAwardPoint) {
			return true;
		}

		return nX < x + WIDTH && 
				nX + nW > x &&
				nY < y + HEIGHT &&
				nY + nH > y;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public void setY (int newY) {
		y = newY;
	}
}
