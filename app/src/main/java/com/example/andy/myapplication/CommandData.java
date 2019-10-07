package com.example.andy.myapplication;

/**
 * This is a data class that is constructed from the SecondParser to hold
 * the correct data from the users converted voice command to the DrumTrackData
 */
public class CommandData {
    private int command; // insert, delete etc
    private int name; // kick, snare etc
    private int pos; // beat

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
