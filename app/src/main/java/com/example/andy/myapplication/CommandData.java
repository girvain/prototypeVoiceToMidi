package com.example.andy.myapplication;

import java.util.ArrayList;

/**
 *
 */
public class CommandData {
    private int command; // insert, delete etc
    private int name; // kick, snare etc
    private int pos; // beat
    private ArrayList<Integer> positions;


    CommandData() {
        this.pos = -1; // pos needs a defualt value of -1 because the uninitialised default is 0
        this.positions = new ArrayList<>();
    }

    /**
     * Function to check that all member variables are set, which means it's a valid command
     */
    public boolean validate() {
        if (command != 0 &&
                name != 0 &&
                positions.size() >= 1 &&
                !positions.contains(-1) // doesn't contain a -1, which is an unrecognised position
        ) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
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
