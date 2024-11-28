package com.example.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GamePanel extends JPanel implements KeyListener, MouseListener {

    // Game states
    final static int MENU = 0;
    final static int GAME = 1;

    ////////////////////
    // Game variables //
    private InputManager inputManager;
    private DrawingManager drawingManager;
    // Textures
    public static HashMap<String, Texture> textures = new Sprites().getGameTextures();
    // Moving base effect
    private static final int baseSpeed = 2;
    private static final int[] baseCoords = {0, 435};
    public boolean ready = false;                   // If game has loaded
    public ArrayList<Pipe> pipes;                   // Arraylist of Pipe objects
    private Calendar cal;
    ////////////////////

    public int getBaseSpeed() {
        return baseSpeed;
    }

    // Fonts
    private Font flappyFontBase,
            flappyFontReal,
            flappyScoreFont,
            flappyMiniFont = null;
    private int gameState = MENU;
    private int score;           // Player score

    public int getPipeDistTracker() {
        return pipeDistTracker;
    }

    public void setPipeDistTracker(int pipeDistTracker) {
        this.pipeDistTracker = pipeDistTracker;
    }

    private int pipeDistTracker; // Distance between pipes
    private boolean inStartGameState = false;       // To show instructions scren

    public int[] getBaseCoords(){
        return baseCoords;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setClickedPoint(Point clickedPoint) {
        this.clickedPoint = clickedPoint;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Bird getGameBird() {
        return gameBird;
    }

    public boolean isInStartGameState() {
        return inStartGameState;
    }

    public void setInStartGameState(boolean inStartGameState) {
        this.inStartGameState = inStartGameState;
    }

    private Point clickedPoint = new Point(-1, -1); // Store point when player clicks
    private boolean scoreWasGreater;                // If score was greater than previous highscore

    public boolean isDarkTheme() {
        return darkTheme;
    }

    private boolean darkTheme;                      // Boolean to show dark or light screen

    public String getMedal() {
        return medal;
    }

    public void setMedal(String medal) {
        this.medal = medal;
    }

    private String medal;                           // Medal to be awarded after each game
    private Bird gameBird;
    private final Highscore highscore = new Highscore();

    public Highscore getHighscore() {
        return highscore;
    }

    public boolean getscoreWasGreater() {
        return scoreWasGreater;
    }

    public void setscoreWasGreater(boolean scoreWasGreater) {
        this.scoreWasGreater = scoreWasGreater;
    }

    public GamePanel() {

        this.inputManager = new InputManager(this);
        this.drawingManager = new DrawingManager(this);
        // Try to load ttf file
        try {
            InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream("/res/fonts/flappy-font.ttf"));
            flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

            // Header and sub-header fonts
            flappyScoreFont = flappyFontBase.deriveFont(Font.PLAIN, 50);
            flappyFontReal = flappyFontBase.deriveFont(Font.PLAIN, 20);
            flappyMiniFont = flappyFontBase.deriveFont(Font.PLAIN, 15);

        } catch (Exception ex) {

            // Exit is font cannot be loaded
            ex.printStackTrace();
            System.err.println("Could not load Flappy Font!");
            System.exit(-1);
        }

        restart(); // Reset game variables

        // Input listeners
        addKeyListener(this);
        addMouseListener(this);

    }

    /**
     * To start game after everything has been loaded
     */
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }

    /**
     * Restarts game by resetting game variables
     */
    public void restart() {

        // Reset game statistics
        score = 0;
        pipeDistTracker = 0;
        scoreWasGreater = false;

        // Get current hour with Calendar
        // If it is past noon, use the dark theme
        cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        // Set random scene assets
        darkTheme = currentHour > 12; // If we should use the dark theme

        // Game bird
        gameBird = new Bird(172, 250);

        // Remove old pipes
        pipes = new ArrayList<>();
    }

    /**
     * Checks if point is in rectangle
     *
     * @param      r     Rectangle
     * @return Boolean if point collides with rectangle
     */
    public boolean isTouching(Rectangle r) {
        return r.contains(clickedPoint);
    }

    public void paintComponent(Graphics g) {
        drawingManager.paintComponent(g);
    }

    public void keyTyped(KeyEvent e) {
        inputManager.keyTyped(e);
    }

    public void keyReleased(KeyEvent e) {
        inputManager.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        inputManager.keyPressed(e);
    }

    public void mouseExited(MouseEvent e) {
        inputManager.mouseExited(e);
    }

    public void mouseEntered(MouseEvent e) {
        inputManager.mouseEntered(e);
    }

    public void mouseReleased(MouseEvent e) {
        inputManager.mouseReleased(e);
    }

    public void mouseClicked(MouseEvent e) {
        inputManager.mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        inputManager.mousePressed(e);
    }
}