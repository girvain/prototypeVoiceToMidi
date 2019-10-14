package com.example.andy.myapplication;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class will be to manage the stateChanged of the drum components and hold them
 * in a container.
 */
public class DrumTrackData {

    static final int INSERT = 500;
    static final int DELETE = 501;
    static final int UNDO = 502;
    static final int KICK = 36;
    static final int SNARE = 38;
    static final int HIHAT_CLOSE = 42;
    static final int HIHAT_OPEN = 46;

    private ArrayList<DrumComponent> drumComponentList;
    private CommandProcessor commandProcessor;
    private ArrayDeque<ArrayList> undoBackStack;
    private boolean stateChanged;

    public DrumTrackData(CommandProcessor commandProcessor) {
        this.drumComponentList = new ArrayList<>();
        this.commandProcessor = commandProcessor;
        this.undoBackStack = new ArrayDeque<>();
    }

    /**
     * Recieves a string version on a command, uses the CommandProcessor class
     * to parse the command into a CommandData object. It then Checks the type of command
     * and calls the commands corrisponding method, i.e addDrumHit.
     * The if () with addDrumHit is to manage the state of changes made. If there has been
     * any change to anything in the componentList, the addDrumHit returns true and stateChanged
     * will be set to true. The opposite is set in place for deleting with the state being
     * false.
     * @return returns DTDResult object that contains information on state for the interface
     * "MainActivity" to use for displaying etc.
     * @param input
     */
    public DTDResult processCommand(String input) {
        DTDResult dtdResult = new DTDResult();
        stateChanged = false; // set this to false for each new command processed

        // commandData is null if it's not been created successfully
        CommandData commandData = commandProcessor.convertStringToCommandDataObj(input);

        // copy the componentList "state" before it gets modified
        ArrayList<DrumComponent> copyOfDrumComponentList = copyDrumCompList();

        if (commandData != null && commandData.getCommand() == INSERT) {
            for (int hit : commandData.getPositions()) {
                if (addDrumHit(commandData.getName(), hit)) {
                    stateChanged = true;
                }
            }
        } else if(commandData != null && commandData.getCommand() == DELETE) {
            for (int hit : commandData.getPositions()) {
                if (deleteDrumHit(commandData.getName(), hit)) {
                    stateChanged = true;
                }
            }
        } else if (commandData != null && commandData.getCommand() == UNDO) {
            dtdResult.setUndoStackEmpty(undoBackStack.isEmpty());
            undoLastChange();
        }
        // add the copy of previous state of drumComponentList to the backstack
        if (stateChanged) {
            undoBackStack.push(copyOfDrumComponentList);
        }

        return dtdResult;

    }

    // TODO create better error handling and Interface update of undo stack empty instead od swallowing errors
    /**
     * Method to take the most recent drumComponentList pushed to the backstack and swap it with the current
      */
    public boolean undoLastChange() {
        if (undoBackStack.size() >= 1) {
            drumComponentList = undoBackStack.pop();
            return true;
        } else
            return false;
    }

    public ArrayList<DrumComponent> copyDrumCompList() {
        ArrayList<DrumComponent> newList = new ArrayList<>();
        Iterator<DrumComponent> iter = drumComponentList.iterator();
        while (iter.hasNext()) {
            try {
                newList.add((DrumComponent) iter.next().clone());
            } catch (Exception e) {}
        }
        return newList;
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
    // This will be important later for keeping the stateChanged of the drumComponentList
    // because if the added component didn't change anything then there is no need
    // to update the stateChanged.
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

    public boolean isStateChanged() {
        return stateChanged;
    }

    public ArrayDeque<ArrayList> getUndoBackStack() {
        return undoBackStack;
    }
}
