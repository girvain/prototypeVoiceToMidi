package com.example.andy.myapplication;

import android.content.Context;
import android.media.MediaPlayer;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MidiPlayer {

    private final int KICK = 36;
    private final int SNARE = 38;
    private final int HIHAT_CLOSE = 42;
    private final int HIHAT_OPEN = 46;
    private MidiTrack tempoTrack;
    private MidiTrack noteTrack;
    private MidiFile midi;

    private DrumTrackData drumTrackData;

    public MidiPlayer() {
        // 1. Create some MidiTracks
        tempoTrack = new MidiTrack();
        noteTrack = new MidiTrack();

        // 2. Add events to the tracks
        // Track 0 is the tempo map
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(120);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);

        // Temp data
        noteTrack.insertNote(9, KICK, 100, 1, 140);
        noteTrack.insertNote(9, KICK, 100, 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 480, 140);
        noteTrack.insertNote(9, KICK, 100, 2 * 480, 140);
        noteTrack.insertNote(9, KICK, 100, 3 * 480, 140);


        // 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);


        /****************? Dummy object data for testing *****************/
        drumTrackData = new DrumTrackData();
        drumTrackData.addDrumHit(DrumTrackData.KICK, 0);
        drumTrackData.addDrumHit(DrumTrackData.KICK, 2);
        drumTrackData.addDrumHit(DrumTrackData.KICK, 4);
        drumTrackData.addDrumHit(DrumTrackData.KICK, 6);
        drumTrackData.addDrumHit(DrumTrackData.SNARE, 2);
        drumTrackData.addDrumHit(DrumTrackData.SNARE, 6);

    }

    /**
     * Method to create a midi file from a DrumtrackData objects drumComponentList
     */

    public void updateNoteTrack() {
//        midi.getTracks().get(1).insertNote(9, SNARE, 100, 5*480, 140);
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();
        noteTrack.insertNote(9, SNARE, 100, 0 * 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 1 * 480, 140);
        noteTrack.insertNote(9, SNARE, 100, (2 * 480) + 220, 140);
        noteTrack.insertNote(9, SNARE, 100, 2 * 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 3 * 480, 140);
        noteTrack.insertNote(9, SNARE, 0, 5 * 480, 140);
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);
        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

        /**
         * Algo to copy a current midi file and add to it
         * get a list of DrumComponents to loop thu
         * if the component beats[] containts a "hit" (a '1' in the array),
         * do a noteTrack.insertNote with drumComponent.getName (which could be KICK etc)
         * and also in the .insertNote, drumComponent.getPosOfBeat
         * which will need converted to the right tick
         */
    }

    /**
     * This method will most likely be called when the drumTrackData objects state changes
     * @return
     */
    public void convertDrumTrackDataToMidi() {
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

        for (DrumComponent drumComponent : drumTrackData.getDrumComponentList()) {
            for (int i = 0; i < drumComponent.getBeats().length; i++) {
                if (drumComponent.getBeats()[i] == 1) {
                    noteTrack.insertNote(9, drumComponent.getName(), 100, i*240, 140);
                }
            }
        }
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);
        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
    }

    public void writeToFile(Context context, MidiFile midi) {
        // 4. Write the MIDI data to a file
        File output = new File(context.getFilesDir(), "exampleout.mid");
        try {
            midi.writeToFile(output);
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    public MidiFile getMidi() {
        return midi;
    }

}
