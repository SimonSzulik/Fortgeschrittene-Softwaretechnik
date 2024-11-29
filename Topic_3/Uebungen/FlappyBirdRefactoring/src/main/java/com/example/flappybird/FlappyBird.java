package com.example.flappybird;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class FlappyBird extends JFrame implements ActionListener {
	InteractivePanel game;
	Timer gameTimer;

	public static final int WIDTH  = 375;
	public static final int HEIGHT = 667;
	private static final int DELAY = 12;

	public FlappyBird () {

		super("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);

		gameTimer = new Timer(DELAY, this);
		gameTimer.start();
  
		game = new InteractivePanel();
		add(game);

		setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icons.png"));

		setResizable(false);
		setVisible(true);
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
