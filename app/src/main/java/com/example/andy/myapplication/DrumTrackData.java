package com.example.andy.myapplication;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class will be to manage the state of the drum components and hold them
 * in a container.
 */
public class DrumTrackData {

    // command constants
    static final int INSERT = 500;
    static final int DELETE = 501;
    static final int UNDO = 502;
    static final int RESET = 503;
    static final int KICK = 36;
    static final int SNARE = 38;
    static final int HIHAT_CLOSE = 42;
    static final int HIHAT_OPEN = 46;
    static final int SET_TEMPO = 504;

    private ArrayList<DrumComponent> drumComponentList;
    private CommandProcessor commandProcessor;
    private ArrayDeque<ArrayList> undoBackStack;
    private boolean stateChanged;
    private int tempo;

    public DrumTrackData(CommandProcessor commandProcessor) {
        this.drumComponentList = new ArrayList<>();
        this.commandProcessor = commandProcessor;
        this.undoBackStack = new ArrayDeque<>();
        this.tempo = 120; // set a default tempo to start with
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
            insertHandler(commandData); // perform action using commmandData
            dtdResult.setCommandRecognised(true); // set result Object accordingly
        } else if(commandData != null && commandData.getCommand() == DELETE) {
            deleteHandler(commandData);
            dtdResult.setCommandRecognised(true);
        } else if (commandData != null && commandData.getCommand() == UNDO) {
            dtdResult.setUndoStackEmpty(undoBackStack.isEmpty());
            dtdResult.setCommandRecognised(true);
            undoLastChange();
        } else if (commandData != null && commandData.getCommand() == RESET) {
            resetHandler();
            dtdResult.setReset(true);
            dtdResult.setCommandRecognised(true);
        } else if (commandData != null && commandData.getCommand() == SET_TEMPO) {
            tempo = commandData.getTempoValue();
            dtdResult.setCommandRecognised(true);
        }
        else {
            dtdResult.setCommandRecognised(false);
        }

        // add the copy of previous state of drumComponentList to the backstack
        if (stateChanged) {
            undoBackStack.push(copyOfDrumComponentList);
        }

        return dtdResult;
    }



    /**
     * Hander method for Insert commands. Loops through a commandData objects positions arrayList
     * and calls addDrumHit for each position.
     * @param commandData
     */
    public void insertHandler(CommandData commandData) {
        for (int hit : commandData.getPositions()) {
            if (addDrumHit(commandData.getName(), hit)) {
                stateChanged = true;
            }
        }
    }

    /**
     * Same as insertHandler but with DELETE commands
     * @param commandData
     */
    public void deleteHandler(CommandData commandData) {
        for (int hit : commandData.getPositions()) {
            if (deleteDrumHit(commandData.getName(), hit)) {
                stateChanged = true;
            }
        }
    }

    /**
     * not much to see but improves readability in the code it's used in, processCommand().
     */
    public void resetHandler() {
        drumComponentList.clear();
        stateChanged = true;
    }

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

    public int getTempo() {
        return tempo;
    }
}
