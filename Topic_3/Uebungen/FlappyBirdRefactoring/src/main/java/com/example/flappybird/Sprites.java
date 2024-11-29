package com.example.flappybird;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;

public class Sprites {
	private static final double RESIZE_FACTOR = 2.605;
	private static BufferedImage spriteSheet;
	private static HashMap<String, Texture> textures = new HashMap<>();

	public Sprites() {
		loadSpriteSheet();
		loadTextures();
	}

	private void loadSpriteSheet() {
		try {
			spriteSheet = ImageIO.read(this.getClass().getResource("/res/img/spriteSheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load sprite sheet.");
			System.exit(-1);
		}
	}

	private void loadTextures() {
		// Add background textures
		addTexture("background1", 0, 0, 144, 256);
		addTexture("background2", 146, 0, 144, 256);

		// Add pipe textures
		addTexture("pipe-top", 56, 323, 26, 160);
		addTexture("pipe-bottom", 84, 323, 26, 160);

		// Birds
		addTexture("yellowBird1", 31, 491, 17, 12, 172, 250);
		addTexture("yellowBird2", 59, 491, 17, 12, 172, 250);
		addTexture("yellowBird3", 3, 491, 17, 12, 172, 250);

		addTexture("blueBird1", 115, 329, 17, 12, 172, 250);
		addTexture("blueBird2", 115, 355, 17, 12, 172, 250);
		addTexture("blueBird3", 87, 491, 17, 12, 172, 250);

		addTexture("redBird1", 115, 407, 17, 12, 172, 250);
		addTexture("redBird2", 115, 433, 17, 12, 172, 250);
		addTexture("redBird3", 115, 381, 17, 12, 172, 250);

		// Buttons
		addTexture("playButton", 354, 118, 52, 29, 34, 448);
		addTexture("leaderboard", 414, 118, 52, 29, 203, 448);
		addTexture("rateButton", 465, 1, 31, 18, 147, 355);

		// Helpful / Text
		addTexture("newHighscore", 112, 501, 16, 7, 210, 305);
		addTexture("titleText", 351, 91, 89, 24, 72, 100);
		addTexture("getReadyText", 295, 59, 92, 25, 68, 180);
		addTexture("gameOverText", 395, 59, 96, 21, 62, 100);
		addTexture("instructions", 292, 91, 57, 49, 113, 300);

		// Large numbers
		addTexture("score-0", 496, 60, 12, 18);
		addTexture("score-1", 136, 455, 8, 18);

		int score = 2;
		for (int i = 292; i < 335; i += 14) {
			addTexture("score-" + score, i, 160, 12, 18);
			addTexture("score-" + (score + 4), i, 184, 12, 18);
			score++;
		}

		// Mini numbers
		score = 0;
		for (int i = 323; score < 10; i += 9) {
			addTexture("mini-score-" + score, 138, i, 10, 7);
			score++;
			if (score % 2 == 0) { i += 8; }
		}

		// Medals
		addTexture("bronze", 112, 477, 22, 22, 73, 285);
		addTexture("silver", 112, 453, 22, 22, 73, 285);
		addTexture("gold", 121, 282, 22, 22, 73, 285);
		addTexture("platinum", 121, 258, 22, 22, 73, 285);

		// Other assets
		addTexture("base", 292, 0, 168, 56, 0, 521);
		addTexture("scoreCard", 3, 259, 113, 57, 40, 230);
	}

	private void addTexture(String name, int x, int y, int width, int height) {
		addTexture(name, x, y, width, height, 0, 0);
	}

	private void addTexture(String name, int x, int y, int width, int height, int posX, int posY) {
		BufferedImage subImage = spriteSheet.getSubimage(x, y, width, height);
		BufferedImage resizedImage = resize(subImage);
		textures.put(name, new Texture(resizedImage, posX, posY));
	}

	private BufferedImage resize(BufferedImage image) {
		return getBufferedImage(image, RESIZE_FACTOR);
	}

	private static BufferedImage getBufferedImage(BufferedImage image, double resizeFactor) {
		int newWidth = (int) (image.getWidth() * resizeFactor);
		int newHeight = (int) (image.getHeight() * resizeFactor);

		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, newWidth, newHeight, null);
		g.dispose();
		return resizedImage;
	}

	public HashMap<String, Texture> getGameTextures() {
		return textures;
	}
}
