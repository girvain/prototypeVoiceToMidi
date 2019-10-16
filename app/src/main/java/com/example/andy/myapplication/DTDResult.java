package com.example.andy.myapplication;

/**
 * Class to hold information about the command that has been processed by the DrumTrackDataClass
 * processCommand method.
 */
public class DTDResult {
    private boolean stateChanged;
    private boolean undoStackEmpty;
    private boolean commandRecognised;
    private boolean reset;
    private boolean componentListEmpty;
    private boolean tempoChanged;


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

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isComponentListEmpty() {
        return componentListEmpty;
    }

    public void setComponentListEmpty(boolean componentListEmpty) {
        this.componentListEmpty = componentListEmpty;
    }

    public boolean isTempoChanged() {
        return tempoChanged;
    }

    public void setTempoChanged(boolean tempoChanged) {
        this.tempoChanged = tempoChanged;
    }
}
