package com.example.andy.myapplication;

/**
 * Class to hold information about the command that has been processed by the DrumTrackDataClass.
 */
public class DTDResult {
    private boolean stateChanged;
    private boolean undoStackEmpty;
    private boolean commandRecognised;

//    public DTDResult(boolean stateChanged, boolean undoStackEmpty) {
//        this.stateChanged = stateChanged;
//        this.undoStackEmpty = undoStackEmpty;
//    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public boolean isUndoStackEmpty() {
        return undoStackEmpty;
    }

    public void setStateChanged(boolean stateChanged) {
        this.stateChanged = stateChanged;
    }

    public void setUndoStackEmpty(boolean undoStackEmpty) {
        this.undoStackEmpty = undoStackEmpty;
    }

    public void setCommandRecognised(boolean commandRecognised) {
        this.commandRecognised = commandRecognised;
    }

    public boolean isCommandRecognised() {
        return commandRecognised;
    }
}
