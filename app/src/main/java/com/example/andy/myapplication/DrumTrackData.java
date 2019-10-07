package com.example.andy.myapplication;

import android.util.Log;

import java.util.ArrayList;

/**
 * This class will be to manage the state of the drum components and hold them
 * in a container.
 */
public class DrumTrackData {

    static final int INSERT = 500;
    static final int KICK = 36;
    static final int SNARE = 38;
    static final int HIHAT_CLOSE = 42;
    static final int HIHAT_OPEN = 46;

    private ArrayList<DrumComponent> drumComponentList;

    public DrumTrackData() {
        this.drumComponentList = new ArrayList<>();
    }


    public DrumComponent checkComponentExists(int name) {
        for (DrumComponent i : drumComponentList) {
            if (i.getName() == name) {
                return i;
            }
        }
        return null;
    }

    // method returns true of false so we know if any changes have happened.
    // This will be important later for keeping the state of the drumComponentList
    // because if the added compoenet didn't change anything then there is no need
    // to update the state.
    public boolean addDrumHit(int name, int pos) {
        DrumComponent drumComponent = checkComponentExists(name);

        // if there is already a DrumComponent, like a KICK, we want to keep the previous
        // beats it plays on so we add to it, unless it's already containing a hit
        if (drumComponent != null) {
            if (drumComponent.getBeats()[pos] == 0) {
                drumComponent.getBeats()[pos] = 1;
                return true;
            } else {
                return false;
            }
        } else {
            drumComponent = new DrumComponent(name);
            drumComponent.getBeats()[pos] = 1;
            drumComponentList.add(drumComponent);
            return true;
        }
    }

    public CommandData convertStringToCommandDataObj(String input) {
        String[] parsedPhraseArray = input.split(" ");
        CommandData commandData = new CommandData();
        boolean isBeat = false;
        for (String word : parsedPhraseArray) {
            if (word.equals("insert")) {
                commandData.setCommand(INSERT);
            } else if (word.equals("kick")) {
                commandData.setName(KICK);
            } else if (word.equals("beat")) {
                isBeat = true;
//                commandData.setPos(0);
            } else if (isBeat) {
                switch (word) {
                    case "one":
                        commandData.setPos(0);
                        break;
                    case "two":
                        commandData.setPos(1);
                        break;
                    case "three":
                        commandData.setPos(2);
                        break;
                    case "four":
                        commandData.setPos(3);
                        break;
                    case "five":
                        commandData.setPos(4);
                        break;
                    case "six":
                        commandData.setPos(5);
                        break;
                    case "seven":
                        commandData.setPos(6);
                        break;
                    case "eight":
                        commandData.setPos(7);
                        break;
                    case "1":
                        commandData.setPos(0);
                        break;
                    case "2":
                        commandData.setPos(1);
                        break;
                    case "3":
                        commandData.setPos(2);
                        break;
                    case "4":
                        commandData.setPos(3);
                        break;
                    case "5":
                        commandData.setPos(4);
                        break;
                    case "6":
                        commandData.setPos(5);
                        break;
                    case "7":
                        commandData.setPos(6);
                        break;
                    case "8":
                        commandData.setPos(7);
                        break;
                    default:
                        commandData.setPos(-1);// this is because the default uninitialised value is 0
                }
            }
        }
        return commandData;
    }

    public void processCommand(String input) {
        CommandData commandData = convertStringToCommandDataObj(input);
        if (commandData.getCommand() == INSERT) {
            if (commandData.getPos() != -1) {
                addDrumHit(commandData.getName(), commandData.getPos());
            }

        }
    }


    public ArrayList<DrumComponent> getDrumComponentList() {
        return drumComponentList;
    }


}
