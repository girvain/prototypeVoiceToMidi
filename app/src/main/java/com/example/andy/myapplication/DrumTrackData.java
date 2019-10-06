package com.example.andy.myapplication;

import java.util.ArrayList;

/**
 * This class will be to manage the state of the drum components and hold them
 * in a container.
 */
public class DrumTrackData {

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









    public ArrayList<DrumComponent> getDrumComponentList() {
        return drumComponentList;
    }


}
