package com.example.flappybird;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Highscore {

	private static final String FILE_PATH = "/res/data/highscore.dat";
	private static URL dataURL = Highscore.class.getResource(FILE_PATH);
	private static Scanner dataScanner    = null;
	private static PrintWriter dataWriter = null;
	private int bestScore;
	private static File dataFile;

	static {
		try {
			dataFile = Paths.get(dataURL.toURI()).toFile();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public Highscore () {

		try {
			dataScanner = new Scanner(dataFile);
		} catch (IOException e) {
			System.out.println("Cannot load highscore!");
		}

		bestScore = Integer.parseInt(dataScanner.nextLine());
	}

	public int bestScore () {
		return bestScore;
	}

	public void setNewBest (int newBest) {

		bestScore = newBest;

		try {
			dataWriter = new PrintWriter(FILE_PATH, "UTF-8");
			dataWriter.println(newBest);
			dataWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Could not set new highscore!");
		}
	}
}
