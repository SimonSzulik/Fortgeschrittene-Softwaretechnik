package com.example.flappybird;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URI;


public class GamePanel extends PanelDrawer implements KeyListener, MouseListener {

	public GamePanel () {
		restart(); // Reset game variables

		// Input listeners
		addKeyListener(this);
		addMouseListener(this);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}

	//////////////////////
	// Keyboard actions //
	//////////////////////

	private boolean isTouching (Rectangle r) {
		return r.contains(clickedPoint);
	}

	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}

	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (gameState == MENU) {

			// Start game on 'enter' key
			if (keyCode == KeyEvent.VK_ENTER) {
				gameState = GAME;
				inStartGameState = true;
			}

		} else if (gameState == GAME && gameBird.isAlive()) {

			if (keyCode == KeyEvent.VK_SPACE) {

				// Exit instructions state
				if (inStartGameState) {
					inStartGameState = false;
				}

				// Jump and play audio even if in instructions state
				gameBird.jump();
				audio.jump();
			}
		}
	}

	///////////////////
	// Mouse actions //
	///////////////////

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}

	public void mousePressed (MouseEvent e) {

		// Save clicked point
		clickedPoint = e.getPoint();

		if (gameState == MENU) {

			if (isTouching(textures.get("playButton").getRect())) {
				gameState = GAME;
				inStartGameState = true;

			} else if (isTouching(textures.get("leaderboard").getRect())) {

				// Dummy message
				JOptionPane.showMessageDialog(this, 
					"We can't access the leaderboard right now!",
					"Oops!",
					JOptionPane.ERROR_MESSAGE);
			}

			if (gameBird.isAlive()) {

				if (isTouching(textures.get("rateButton").getRect())) {
					try {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().browse(new URI("http://paulkr.com")); // Open website
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Sorry could not open URL...");
					}
				}
			}

		} else if (gameState == GAME) {

			if (gameBird.isAlive()) {

				// Allow jump with clicks
				if (inStartGameState) {
					inStartGameState = false;
				}

				// Jump and play sound
				gameBird.jump();
				audio.jump();

			} else {

				// On game over screen, allow restart and leaderboard buttons
				if (isTouching(textures.get("playButton").getRect())) {
					inStartGameState = true;
					gameState = GAME;
					restart();
					gameBird.setGameStartPos();

				} else if (isTouching(textures.get("leaderboard").getRect())) {

					// Dummy message
					JOptionPane.showMessageDialog(this, 
						"We can't access the leaderboard right now!",
						"Oops!",
						JOptionPane.ERROR_MESSAGE);
				}

			}
			
		}

	}


}

