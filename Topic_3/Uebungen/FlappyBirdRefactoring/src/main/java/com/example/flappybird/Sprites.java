package com.example.flappybird;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Sprites {

    private static BufferedImage spriteSheet;

    // HashMap of texture objects
    private static final HashMap<String, Texture> gameTextures = new HashMap<>();
    private static final HashMap<String, Texture> birdTextures = new HashMap<>();

    public Sprites() {

        try {
            spriteSheet = ImageIO.read(this.getClass().getResource("/res/img/spriteSheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load sprite sheet.");
            System.exit(-1); // Exit program if file could not be found
            return;
        }

        // Backgrounds
        putTexture(gameTextures,"background1", 0, 0, 0, 0, 144, 256);
        putTexture(gameTextures,"background2", 146, 0, 0, 0, 144, 256);

        // Pipes
        putTexture(gameTextures,"pipe-top", 56, 323, 0, 0, 26, 160);
        putTexture(gameTextures,"pipe-bottom", 84, 323, 0, 0, 26, 160);

        // Birds
        putTexture(birdTextures,"yellowBird1", 31, 491, 172, 250, 17, 12);
        putTexture(birdTextures,"yellowBird2", 59, 491, 172, 250, 17, 12);
        putTexture(birdTextures,"yellowBird3", 3, 491, 172, 250, 17, 12);

        putTexture(birdTextures,"blueBird1", 115, 329, 172, 250, 17, 12);
        putTexture(birdTextures,"blueBird2", 115, 355, 172, 250, 17, 12);
        putTexture(birdTextures,"blueBird3", 87, 491, 172, 250, 17, 12);

        putTexture(birdTextures,"redBird1", 115, 407, 172, 250, 17, 12);
        putTexture(birdTextures,"redBird2", 115, 433, 172, 250, 17, 12);
        putTexture(birdTextures,"redBird3", 115, 381, 172, 250, 17, 12);

        // Buttons
        putTexture(gameTextures,"playButton", 354, 118, 34, 448, 52, 29);
        putTexture(gameTextures,"leaderboard", 414, 118, 203, 448, 52, 29);
        putTexture(gameTextures,"rateButton", 465, 1, 147, 355, 31, 18);

        // Helpful / Text
        putTexture(gameTextures,"newHighscore", 112, 501, 210, 305, 16, 7);
        putTexture(gameTextures,"titleText", 351, 91, 72, 100, 89, 24);
        putTexture(gameTextures,"getReadyText", 295, 59, 68, 180, 92, 25);
        putTexture(gameTextures,"gameOverText", 395, 59, 62, 100, 96, 21);
        putTexture(gameTextures,"instructions", 292, 91, 113, 300, 57, 49);

        // SCORE IMAGES
        putTexture(gameTextures,"score-0", 496, 60, 0, 0, 12, 18);
        putTexture(gameTextures,"score-1", 136, 455, 0, 0, 8, 18);

        int score = 2;
        for (int i = 292; i < 335; i += 14) {
            putTexture(gameTextures,"score-" + score, i, 160, 0, 0, 12, 18);
            putTexture(gameTextures,"score-" + (score + 4), i, 184, 0, 0, 12, 18);
            score++;
        }

        // Mini numbers
        score = 0;
        for (int i = 323; score < 10; i += 9) {
            putTexture(gameTextures,"mini-score-" + score, 138, i, 0, 0, 10, 7);
            score++;
            if (score % 2 == 0) {
                i += 8;
            }
        }

        // Medals
        putTexture(gameTextures,"bronze", 112, 477, 73, 285, 22, 22);
        putTexture(gameTextures,"silver", 112, 453, 73, 285, 22, 22);
        putTexture(gameTextures,"gold", 121, 282, 73, 285, 22, 22);
        putTexture(gameTextures,"platinum", 121, 258, 73, 285, 22, 22);

        // Other assets
        putTexture(gameTextures,"base", 292, 0, 0, 521, 168, 56);
        putTexture(gameTextures,"scoreCard", 3, 259, 40, 230, 113, 57);
    }

    private void putTexture(HashMap<String, Texture> textures, String name, int texX, int texY, int panX, int panY, int width, int height) {
        BufferedImage subimage = spriteSheet.getSubimage(texX, texY, width, height);
        Texture texture = new Texture(resize(subimage), panX, panY);
        textures.put(name, texture);
    }

    private static BufferedImage resize(BufferedImage image) {
        // New width and height
        int newWidth = (int) (image.getWidth() * 2.605);
        int newHeight = (int) (image.getHeight() * 2.605);

        // Create new BufferedImage with updated width and height
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }

    public HashMap<String, Texture> getGameTextures() {return gameTextures;}
    public HashMap<String, Texture> getBirdTextures() {return birdTextures;}
}
