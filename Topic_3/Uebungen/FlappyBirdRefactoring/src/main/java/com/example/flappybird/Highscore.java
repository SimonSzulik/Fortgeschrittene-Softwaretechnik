package com.example.flappybird;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;


public class Highscore {

    // Read / Write to file setup
    private static final String FILE_PATH = "/res/data/highscore.dat";

    private static final URL dataURL = Highscore.class.getResource(FILE_PATH);
    private static final File dataFile;
    private static Scanner dataScanner = null;
    private static PrintWriter dataWriter = null;

    static {
        try {
            dataFile = Paths.get(dataURL.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Highscore
    private int bestScore;

    public Highscore() {

        // Load scanner with data file
        try {
            dataScanner = new Scanner(dataFile);
        } catch (IOException e) {
            System.out.println("Cannot load highscore!");
        }

        // Store highscore
        bestScore = Integer.parseInt(dataScanner.nextLine());

    }

    public int bestScore() {
        return bestScore;
    }

    public void setNewBest(int newBest) {

        // Set new best score
        bestScore = newBest;

        try {
            // Write new highscore to data file
            dataWriter = new PrintWriter(FILE_PATH, StandardCharsets.UTF_8);
            dataWriter.println(newBest);
            dataWriter.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Could not set new highscore!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
