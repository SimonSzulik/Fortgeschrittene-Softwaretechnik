package com.example.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import java.util.HashMap;

public class InputManager implements KeyListener, MouseListener {
    public static HashMap<String, Texture> textures = new Sprites().getGameTextures();
    private GamePanel gamePanel;

    public InputManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (gamePanel.getGameState() == gamePanel.MENU) {

            // Start game on 'enter' key
            if (keyCode == KeyEvent.VK_ENTER) {
                gamePanel.setGameState(gamePanel.GAME);
                gamePanel.setInStartGameState(true);
            }

        } else if (gamePanel.getGameState() == gamePanel.GAME && gamePanel.getGameBird().isAlive()) {

            if (keyCode == KeyEvent.VK_SPACE) {

                // Exit instructions state
                if (gamePanel.isInStartGameState()) {
                    gamePanel.setInStartGameState(false);
                }

                // Jump and play audio even if in instructions state
                gamePanel.getGameBird().jump();
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

        gamePanel.setClickedPoint(e.getPoint());

        if (gamePanel.getGameState() == gamePanel.MENU) {

            TouchPlayButton();

            if (gamePanel.isTouching(textures.get("rateButton").getRect())) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI("http://www.paulkr.com"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Sorry could not open URL...");
                }
            }

        } else if (gamePanel.getGameState() == gamePanel.GAME) {

            if (gamePanel.getGameBird().isAlive()) {

                // Allow jump with clicks
                if (gamePanel.isInStartGameState()) {
                    gamePanel.setInStartGameState(false);
                }

                // Jump and play sound
                gamePanel.getGameBird().jump();

            } else {

                // On game over screen, allow restart and leaderboard buttons
                TouchPlayButton();
            }
        }
    }

    private void TouchPlayButton() {
        if (gamePanel.isTouching(textures.get("playButton").getRect())) {
            gamePanel.setGameState(gamePanel.GAME);
            gamePanel.setInStartGameState(true);
            gamePanel.restart();
            gamePanel.getGameBird().setGameStartPos();

        } else if (gamePanel.isTouching(textures.get("leaderboard").getRect())) {

            // Dummy message
            JOptionPane.showMessageDialog(gamePanel,
                    "We can't access the leaderboard right now!",
                    "Oops!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
