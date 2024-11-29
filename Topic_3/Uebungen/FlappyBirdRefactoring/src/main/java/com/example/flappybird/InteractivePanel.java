package com.example.flappybird;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class InteractivePanel extends PanelDrawer implements KeyListener, MouseListener {

	public InteractivePanel() {
		restart(); // Reset game variables

		addKeyListener(this);
		addMouseListener(this);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}

	private boolean isTouching (Rectangle r) {
		return r.contains(clickedPoint);
	}

	public void keyTyped (KeyEvent e) {}
	public void keyReleased (KeyEvent e) {}
	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (gameState == MENU) {
			if (keyCode == KeyEvent.VK_ENTER) {
				gameState = GAME;
				inStartGameState = true;
			}

		} else if (gameState == GAME && gameBird.isAlive() && keyCode == KeyEvent.VK_SPACE) {

			if (inStartGameState) {
				inStartGameState = false;
			}
			gameBird.jump();
		}
	}

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {

		clickedPoint = e.getPoint();

		if (gameState == MENU) {

			if (isTouching(textures.get("playButton").getRect())) {
				gameState = GAME;
				inStartGameState = true;

			} else if (isTouching(textures.get("leaderboard").getRect())) {
				drawLeaderBoardError();
			}

			if (gameBird.isAlive() && isTouching(textures.get("rateButton").getRect())) {
					try {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().browse(new URI("https://paulkr.com")); // Open website
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Sorry could not open URL...");
					}
				}

		} else if (gameState == GAME) {

			if (gameBird.isAlive()) {

				if (inStartGameState) {
					inStartGameState = false;
				}
				gameBird.jump();
			} else {

				if (isTouching(textures.get("playButton").getRect())) {
					inStartGameState = true;
					gameState = GAME;
					restart();
					gameBird.setGameStartPos();

				} else if (isTouching(textures.get("leaderboard").getRect())) {
					drawLeaderBoardError();
				}
			}
		}
	}
}
