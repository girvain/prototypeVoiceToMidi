package com.example.andy.myapplication;

import java.util.ArrayList;

/**
 * This is a data class that is constructed from the SecondParser to hold
 * the correct data from the users converted voice command to the DrumTrackData
 */
public class CommandData {
    private int command; // insert, delete etc
    private int name; // kick, snare etc
    private int pos; // beat
    private ArrayList<Integer> postions;


    CommandData() {
        this.pos = -1; // pos needs a defualt value of -1 because the uninitialised default is 0
        this.postions = new ArrayList<>();
    }


    public ArrayList<Integer> getPostions() {
        return postions;
    }

    public void setPostions(ArrayList<Integer> postions) {
        this.postions = postions;
    }

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
