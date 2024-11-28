package com.example.flappybird;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class DrawingManager extends JPanel {
    public static HashMap<String, Texture> textures = new Sprites().getGameTextures();
    GamePanel gamePanel;
    // Fonts
    private Font flappyFontBase,
            flappyFontReal,
            flappyScoreFont,
            flappyMiniFont = null;

    public DrawingManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void drawCentered(String s, int w, int h, int y, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        // Calculate x-coordinate based on string length and width
        int x = (w - fm.stringWidth(s)) / 2;
        g.drawString(s, x, y);
    }

    public void drawBase(Graphics g) {
        // Moving base effect
        g.drawImage(textures.get("base").getImage(), gamePanel.getBaseCoords()[0], textures.get("base").getY(), null);
        g.drawImage(textures.get("base").getImage(), gamePanel.getBaseCoords()[1], textures.get("base").getY(), null);
    }

    public void drawMenu(Graphics g) {
        // Title
        g.drawImage(textures.get("titleText").getImage(),
                textures.get("titleText").getX(),
                textures.get("titleText").getY(), null);

        // Buttons
        drawButtons(g);
        g.drawImage(textures.get("rateButton").getImage(),
                textures.get("rateButton").getX(),
                textures.get("rateButton").getY(), null);

        // Credits :p
        drawCentered("Created by Paul Krishnamurthy", FlappyBird.WIDTH, FlappyBird.HEIGHT, 600, g);
        g.setFont(flappyMiniFont); // Change font
        drawCentered("www.PaulKr.com", FlappyBird.WIDTH, FlappyBird.HEIGHT, 630, g);
    }

    public void drawButtons(Graphics g) {
        g.drawImage(textures.get("playButton").getImage(),
                textures.get("playButton").getX(),
                textures.get("playButton").getY(), null);
        g.drawImage(textures.get("leaderboard").getImage(),
                textures.get("leaderboard").getX(),
                textures.get("leaderboard").getY(), null);
    }

    public void drawScore(Graphics g, int drawNum, boolean mini, int x, int y) {

        // Char array of digits
        char[] digits = ("" + drawNum).toCharArray();

        int digitCount = digits.length;

        // Calculate width for numeric textures
        int takeUp = 0;
        for (char digit : digits) {

            // Size to add varies based on texture
            if (mini) {
                takeUp += 18;
            } else {
                takeUp += digit == '1' ? 25 : 35;
            }
        }
    }

    public void gameOver(Graphics g) {
        // Game over text
        g.drawImage(textures.get("gameOverText").getImage(),
                textures.get("gameOverText").getX(),
                textures.get("gameOverText").getY(), null);

        // Scorecard
        g.drawImage(textures.get("scoreCard").getImage(),
                textures.get("scoreCard").getX(),
                textures.get("scoreCard").getY(), null);

        // New highscore image
        if (gamePanel.getscoreWasGreater()) {
            g.drawImage(textures.get("newHighscore").getImage(),
                    textures.get("newHighscore").getX(),
                    textures.get("newHighscore").getY(), null);
        }


        // Draw mini fonts for current and best scores
        drawScore(g, gamePanel.getScore(), true, 303, 276);
        drawScore(g, gamePanel.getHighscore().bestScore(), true, 303, 330);

        // Handle highscore
        if (gamePanel.getScore() > gamePanel.getHighscore().bestScore()) {

            // New best score
            gamePanel.setscoreWasGreater(true);
            gamePanel.getHighscore().setNewBest(gamePanel.getScore()); // Set in data file
        }
        // Draw mini fonts for current and best scores
        drawScore(g, gamePanel.getScore(), true, 303, 276);
        drawScore(g, gamePanel.getHighscore().bestScore(), true, 303, 330);

        // Handle highscore
        if (gamePanel.getScore() > gamePanel.getHighscore().bestScore()) {

            // New best score
            gamePanel.setscoreWasGreater(true);
            gamePanel.getHighscore().setNewBest(gamePanel.getScore()); // Set in data file
        }

        // Medal
        if (gamePanel.getScore() >= 10) {
            gamePanel.setMedal("bronze");
        }
        if (gamePanel.getScore() >= 20) {
            gamePanel.setMedal("silver");
        }
        if (gamePanel.getScore() >= 30) {
            gamePanel.setMedal("gold");
        }
        if (gamePanel.getScore() >= 40) {
            gamePanel.setMedal("platinum");
        }

        // Only award a medal if they deserve it
        if (gamePanel.getScore() > 9) {
            g.drawImage(textures.get(gamePanel.getMedal()).getImage(),
                    textures.get(gamePanel.getMedal()).getX(),
                    textures.get(gamePanel.getMedal()).getY(), null);
        }
        // Buttons
        drawButtons(g);
    }

    public void startGameScreen(Graphics g) {

        // Set bird's new position
        gamePanel.getGameBird().setGameStartPos();

        // Get ready text
        g.drawImage(textures.get("getReadyText").getImage(),
                textures.get("getReadyText").getX(),
                textures.get("getReadyText").getY(), null);

        // Instructions image
        g.drawImage(textures.get("instructions").getImage(),
                textures.get("instructions").getX(),
                textures.get("instructions").getY(), null);

    }

    public void pipeHandler(Graphics g) {

        // Decrease distance between pipes
        if (gamePanel.getGameBird().isAlive()) {
            gamePanel.setPipeDistTracker(gamePanel.getPipeDistTracker()-1);
        }

        // Initialize pipes as null
        Pipe topPipe = null;
        Pipe bottomPipe = null;

        // If there is no distance,
        // a new pipe is needed
        if (gamePanel.getPipeDistTracker() < 0) {

            // Reset distance
            gamePanel.setPipeDistTracker(Pipe.PIPE_DISTANCE);

            for (Pipe p : gamePanel.pipes) {

                // If pipe is out of screen
                if (p.getX() < 0) {
                    if (topPipe == null) {
                        topPipe = p;
                        topPipe.canAwardPoint = true;
                    } else if (bottomPipe == null) {
                        bottomPipe = p;
                        topPipe.canAwardPoint = true;
                    }
                }
            }

            Pipe currentPipe; // New pipe object for top and bottom pipes

            // Move and handle initial creation of top and bottom pipes

            if (topPipe == null) {
                currentPipe = new Pipe("top");
                topPipe = currentPipe;
                gamePanel.pipes.add(topPipe);
            } else {
                topPipe.reset();
            }

            if (bottomPipe == null) {
                currentPipe = new Pipe("bottom");
                bottomPipe = currentPipe;
                gamePanel.pipes.add(bottomPipe);

                // Avoid doubling points when passing initial pipes
                bottomPipe.canAwardPoint = false;
            } else {
                bottomPipe.reset();
            }

            // Set y-coordinate of bottom pipe based on
            // y-coordinate of top pipe
            bottomPipe.setY(topPipe.getY() + Pipe.PIPE_SPACING);

        }

        // Move and draw each pipe

        for (Pipe p : gamePanel.pipes) {

            // Move the pipe
            if (gamePanel.getGameBird().isAlive()) {
                p.move();
            }

            // Draw the top and bottom pipes
            if (p.getY() <= 0) {
                g.drawImage(textures.get("pipe-top").getImage(), p.getX(), p.getY(), null);
            } else {
                g.drawImage(textures.get("pipe-bottom").getImage(), p.getX(), p.getY(), null);
            }

            // Check if bird hits pipes
            if (gamePanel.getGameBird().isAlive()) {
                if (p.collide(
                        gamePanel.getGameBird().getX(),
                        gamePanel.getGameBird().getY(),
                        gamePanel.getGameBird().BIRD_WIDTH,
                        gamePanel.getGameBird().BIRD_HEIGHT
                )) {
                    // Kill bird and play sound
                    gamePanel.getGameBird().kill();
                } else {

                    // Checks if bird passes a pipe
                    if (gamePanel.getGameBird().getX() >= p.getX() + Pipe.WIDTH / 2 && p.canAwardPoint) {
                        gamePanel.setScore(gamePanel.getGameBird().score(gamePanel.getScore()));
                        p.canAwardPoint = false;
                    }

                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set font and color
        g.setFont(flappyFontReal);
        g.setColor(Color.white);

        // Only move screen if bird is alive
        if (gamePanel.getGameBird().isAlive()) {
            // Move base
            gamePanel.getBaseCoords()[0] = gamePanel.getBaseCoords()[0] - gamePanel.getBaseSpeed() < -435 ? 435 : gamePanel.getBaseCoords()[0] - gamePanel.getBaseSpeed();
            gamePanel.getBaseCoords()[1] = gamePanel.getBaseCoords()[1] - gamePanel.getBaseSpeed() < -435 ? 435 : gamePanel.getBaseCoords()[1] - gamePanel.getBaseSpeed();
        }

        // Background
        g.drawImage(gamePanel.isDarkTheme() ? textures.get("background2").getImage() :
                textures.get("background1").getImage(), 0, 0, null);

        // Draw bird
        gamePanel.getGameBird().renderBird(g);
        drawBase(g);

        switch (gamePanel.getGameState()) {

            case 0:

                drawMenu(g);
                gamePanel.getGameBird().menuFloat();

                break;

            case 1:

                if (gamePanel.getGameBird().isAlive()) {

                    // Start at instructions state
                    if (gamePanel.isInStartGameState()) {
                        startGameScreen(g);
                    } else {
                        // Start game
                        pipeHandler(g);
                        gamePanel.getGameBird().inGame();
                    }

                    drawScore(g, gamePanel.getScore(), false, 0, 0); // Draw player score

                } else {

                    pipeHandler(g);
                    // Draw game over assets
                    gameOver(g);
                }
                break;
        }
    }
}
