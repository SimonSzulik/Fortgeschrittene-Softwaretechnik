package com.example.flappybird;

import java.awt.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;

public class PanelDrawer extends JPanel{
    // Dates
    private Calendar cal;

    // Fonts
    public Font flappyFontBase,
            flappyFontReal,
            flappyScoreFont,
            flappyMiniFont = null;

    // Textures
    public static HashMap<String, Texture> textures = new Sprites().getGameTextures();
    public Point clickedPoint = new Point(-1, -1);
    public boolean darkTheme;

    // Moving base effect
    public static int baseSpeed    = 2;
    public static int[] baseCoords = { 0, 435 };

    // Game states
    final static int MENU = 0;
    final static int GAME = 1;
    public int gameState = MENU;
    public boolean inStartGameState = false;
    public boolean ready = false;

    // Scores
    public Highscore highscore = new Highscore();
    public boolean scoreWasGreater;
    public String medal;
    public int score;

    // Pipes and Bird
    public Bird gameBird;
    public int pipeDistTracker;
    public ArrayList<Pipe> pipes;

    public PanelDrawer(){
        // Try to load ttf file
        try {
            InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream("/res/fonts/flappy-font.ttf"));
            flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

            // Header and sub-header fonts
            flappyScoreFont = flappyFontBase.deriveFont(Font.PLAIN, 50);
            flappyFontReal  = flappyFontBase.deriveFont(Font.PLAIN, 20);
            flappyMiniFont  = flappyFontBase.deriveFont(Font.PLAIN, 15);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Could not load Flappy Font!");
            System.exit(-1);
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        g.setFont(flappyFontReal);
        g.setColor(Color.white);

        if (gameBird.isAlive()) {
            baseCoords[0] = baseCoords[0] - baseSpeed < -435 ? 435 : baseCoords[0] - baseSpeed;
            baseCoords[1] = baseCoords[1] - baseSpeed < -435 ? 435 : baseCoords[1] - baseSpeed;
        }

        g.drawImage(darkTheme ? textures.get("background2").getImage() :
                textures.get("background1").getImage(), 0, 0, null);

        gameBird.renderBird(g);

        if (gameState == MENU) {
            drawBase(g);
            drawMenu(g);
            gameBird.menuFloat();
        }else{
            if (gameBird.isAlive()) {
                if (inStartGameState) {
                    startGameScreen(g);
                } else {
                    pipeHandler(g);
                    gameBird.inGame();
                }
                drawBase(g);
                drawScore(g, score, false, 0, 0);
            } else {
                pipeHandler(g);
                drawBase(g);
                gameOver(g);
            }
        }
    }

    public void drawScore (Graphics g, int drawNum, boolean mini, int x, int y) {
        char[] digits = ("" + drawNum).toCharArray();

        int digitCount = digits.length;

        int takeUp = 0;
        for (char digit : digits) {
            if (mini) {
                takeUp += 18;
            } else {
                takeUp += digit == '1' ? 25 : 35;
            }
        }

        int drawScoreX = mini ? (x - takeUp) : (FlappyBird.WIDTH / 2 - takeUp / 2);

        // Draw every digit
        for (int i = 0; i < digitCount; i++) {
            g.drawImage(textures.get((mini ? "mini-score-" : "score-") + digits[i]).getImage(), drawScoreX, (mini ? y : 60), null);
            if (mini) {
                drawScoreX += 18;
            } else {
                drawScoreX += digits[i] == '1' ? 25 : 35;
            }
        }
    }

    public void drawCentered (String s, int w, int h, int y, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        // Calculate x-coordinate based on string length and width
        int x = (w - fm.stringWidth(s)) / 2;
        g.drawString(s, x, y);
    }

    public void drawBase (Graphics g) {
        g.drawImage(textures.get("base").getImage(), baseCoords[0], textures.get("base").getY(), null);
        g.drawImage(textures.get("base").getImage(), baseCoords[1], textures.get("base").getY(), null);
    }

    public void drawMenu (Graphics g) {
        drawImage(g, "titleText");

        drawButtons(g);
        drawImage(g, "rateButton");

        // Credits :p
        drawCentered("Created by Paul Krishnamurthy", FlappyBird.WIDTH, FlappyBird.HEIGHT, 600, g);
        g.setFont(flappyMiniFont); // Change font
        drawCentered("www.PaulKr.com", FlappyBird.WIDTH, FlappyBird.HEIGHT, 630, g);
    }

    public void drawLeaderBoardError(){
        JOptionPane.showMessageDialog(this,
                "We can't access the leaderboard right now!",
                "Oops!",
                JOptionPane.ERROR_MESSAGE);
    }

    public void startGameScreen (Graphics g) {
        gameBird.setGameStartPos();

        drawImage(g,"getReadyText" );
        drawImage(g,"instructions" );
    }

    public void pipeHandler (Graphics g) {
        // Decrease distance between pipes
        if (gameBird.isAlive()) {
            pipeDistTracker --;
        }

        Pipe topPipe = null;
        Pipe bottomPipe = null;

        if (pipeDistTracker < 0) {
            pipeDistTracker = Pipe.PIPE_DISTANCE;

            for (Pipe p : pipes) {
                if (p.getX() < 0) {
                    if (topPipe == null) {
                        topPipe = p;
                        topPipe.canAwardPoint = true;
                    }
                    else if (bottomPipe == null) {
                        bottomPipe = p;
                        topPipe.canAwardPoint = true;
                    }
                }
            }

            Pipe currentPipe;

            if (topPipe == null) {
                currentPipe = new Pipe("top");
                topPipe = currentPipe;
                pipes.add(topPipe);
            } else {
                topPipe.reset();
            }

            if (bottomPipe == null) {
                currentPipe = new Pipe("bottom");
                bottomPipe = currentPipe;
                pipes.add(bottomPipe);
                bottomPipe.canAwardPoint = false;
            } else {
                bottomPipe.reset();
            }

            bottomPipe.setY(topPipe.getY() + Pipe.PIPE_SPACING);
        }

        for (Pipe p : pipes) {
            if (gameBird.isAlive()) {
                p.move();
            }

            if (p.getY() <= 0) {
                g.drawImage(textures.get("pipe-top").getImage(), p.getX(), p.getY(), null);
            } else {
                g.drawImage(textures.get("pipe-bottom").getImage(), p.getX(), p.getY(), null);
            }

            if (gameBird.isAlive()) {
                if (p.collide(
                        gameBird.getX(),
                        gameBird.getY(),
                        gameBird.BIRD_WIDTH,
                        gameBird.BIRD_HEIGHT
                )) {
                    gameBird.kill();
                } else {
                    if (gameBird.getX() >= p.getX() + p.WIDTH / 2 && p.canAwardPoint) {
                            gameBird.score();
                            score ++;
                            p.canAwardPoint = false;
                        }
                }
            }
        }
    }

    public void gameOver (Graphics g) {
        drawImage(g, "gameOverText");
        drawImage(g, "scoreCard");
        drawImage(g, "newHighscore");

        drawScore(g, score, true, 303, 276);
        drawScore(g, highscore.bestScore(), true, 303, 330);

        if (score > highscore.bestScore()) {
            scoreWasGreater = true;
            highscore.setNewBest(score); // Set in data file
        }

        if (score >= 10) { medal = "bronze"; }
        if (score >= 20) { medal = "silver"; }
        if (score >= 30) { medal = "gold"; }
        if (score >= 40) { medal = "platinum"; }

        if (score > 9) {
            drawImage(g, medal);
        }
        drawButtons(g);
    }
    public void restart () {
        score            = 0;
        pipeDistTracker  = 0;
        scoreWasGreater  = false;

        cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        darkTheme = currentHour > 12;

        gameBird = new Bird(172, 250);

        pipes = new ArrayList<Pipe>();
    }

    public void drawButtons(Graphics g) {
        drawImage(g,"playButton" );
        drawImage(g,"leaderboard" );
    }

    public void drawImage(Graphics g, String textureName){
        g.drawImage(textures.get(textureName).getImage(),
                textures.get(textureName).getX(),
                textures.get(textureName).getY(), null);
    }
}
