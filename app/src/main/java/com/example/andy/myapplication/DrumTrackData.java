package com.example.andy.myapplication;

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
    static final int DELETE = 501;

    private ArrayList<DrumComponent> drumComponentList;
    private CommandProcessor commandProcessor;

    public DrumTrackData(CommandProcessor commandProcessor) {
        this.drumComponentList = new ArrayList<>();
        this.commandProcessor = commandProcessor;
    }

    /**
     * Recieves a string version on a command, uses convertStringCommandToDataObj()
     * to parse the command into a CommandData object. Checks the type of command
     * and calls the commands corrisponding method, i.e addDrumHit.
     * @param input
     */
    public void processCommand(String input) {
        CommandData commandData = commandProcessor.convertStringToCommandDataObj(input);

        if (commandData != null && commandData.getCommand() == INSERT) {
            for (int hit : commandData.getPositions()) {
                addDrumHit(commandData.getName(), hit);
            }
        } else if(commandData != null && commandData.getCommand() == DELETE) {
            for (int hit : commandData.getPositions()) {
                deleteDrumHit(commandData.getName(), hit);
            }
        }
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
    // because if the added component didn't change anything then there is no need
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

    public boolean deleteDrumHit(int name, int pos) {
        DrumComponent drumComponent = checkComponentExists(name);

        if (drumComponent != null) {
            if (drumComponent.getBeats()[pos] == 1) {
                drumComponent.getBeats()[pos] = 0;
                return true;
            }
        }
        return false;
    }

    public ArrayList<DrumComponent> getDrumComponentList() {
        return drumComponentList;
    }


}
