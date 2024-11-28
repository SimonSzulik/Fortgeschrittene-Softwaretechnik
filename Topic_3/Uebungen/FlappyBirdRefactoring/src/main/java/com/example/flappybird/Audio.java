package com.example.flappybird;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    private AudioInputStream audioInputStream;
    private Clip clip;

    private void playSound(String sound) {

        // Path to sound file
        String soundURL = "/res/sound/" + sound + ".wav";

        // Try to load and play sound
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(soundURL));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Count not load %s.wav!\n", sound);
        }
    }

    public void jump() {
        playSound("jump");
    }

    public void point() {
        playSound("point");
    }

    public void hit() {
        playSound("hit");
    }
}
