package com.example.flappybird; /**
 * FlappyBird.java
 * Main game class
 *
 * @author  Paul Krishnamurthy
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class FlappyBird implements ActionListener {

	GamePanel game;
	Timer gameTimer;

	// Game setup constants
	public static final int WIDTH  = 375;
	public static final int HEIGHT = 667;
	private static final int DELAY = 12;

	public FlappyBird () {

		JFrame frame = new JFrame("Flappy Bird");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);

		// Game timer
		gameTimer = new Timer(DELAY, this);
		gameTimer.start();

		// Add Panel to Frame
		game = new GamePanel();
		frame.add(game);

		// Set game icon
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icons.png"));

		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void actionPerformed (ActionEvent e) {

		if (game != null && game.ready) {
			game.repaint();
		}

	}


	public static void main(String[] args) {

		FlappyBird game = new FlappyBird();

	}

}

