package com.example.andy.myapplication;

/**
 * this class is used for a non midi mapping of how to render the midi track
 * for each drum
 */

public class DrumComponent implements Cloneable{

    private int name;
    private int[] beats;

    public DrumComponent(int name) {
        this.name = name;
        beats = new int[8]; // this is 8 8th notes in a bar
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            super.clone();
            DrumComponent dc = new DrumComponent(this.getName());
            int[] arraycopy = this.getBeats().clone();
            dc.setBeats(arraycopy);
            return dc;
        } catch (Exception e) {
            return e;
        }
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int[] getBeats() {
        return beats;
    }

    public void setBeats(int[] beats) {
        this.beats = beats;
    }
}
