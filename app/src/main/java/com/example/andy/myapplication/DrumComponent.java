package com.example.andy.myapplication;

/**
 * this class is used for a non midi mapping of how to render the midi track
 * for each drum
 */

public class DrumComponent {
    private final int KICK = 36;
    private final int SNARE = 38;
    private final int HIHAT_CLOSE = 42;
    private final int HIHAT_OPEN = 46;
    private int name;
    private int[] beats;

    public DrumComponent(int name) {
        this.name = name;
        beats = new int[7]; // this is 8 8th notes in a bar

    }
}
