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

//    private DrumTrackData drumTrackData;

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
        noteTrack.insertNote(9, KICK, 100, 0 * 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 0 * 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 480, 140);
        noteTrack.insertNote(9, KICK, 100, 2 * 480, 140);
        noteTrack.insertNote(9, KICK, 100, 3 * 480, 140);

        noteTrack.insertNote(9, HIHAT_CLOSE, 70, 0 * 480, 140);
        noteTrack.insertNote(9, HIHAT_CLOSE, 70, 1 * 480, 140);
        noteTrack.insertNote(9, HIHAT_CLOSE, 80, 2 * 480, 140);
        noteTrack.insertNote(9, HIHAT_CLOSE, 70, 3 * 480, 140);


        // 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);




    }

    /**
     * Method to multiply the initial MIDI track bar into multiple bars
     */
    public void convertDrumTrackDataToMidi(DrumTrackData drumTrackData, int bars) {
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

        for (DrumComponent drumComponent : drumTrackData.getDrumComponentList()) {
            for (int i = 0; i < drumComponent.getBeats().length; i++) {
                if (drumComponent.getBeats()[i] == 1) {
                    noteTrack.insertNote(9, drumComponent.getName(), 100, i*240, 140);
                }
            }
        }

        int count = 1;
        while (count < bars) {
            int addtionalBarTick =  1920;

            for (DrumComponent drumComponent : drumTrackData.getDrumComponentList()) {
                for (int i = 0; i < drumComponent.getBeats().length; i++) {
                    if (drumComponent.getBeats()[i] == 1) {
                        noteTrack.insertNote(9, drumComponent.getName(), 100, i*240+ (addtionalBarTick*count), 140);
                    }
                }
            }
            count++;
        }
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);
        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
    }

    /**
     * This method will most likely be called when the drumTrackData objects state changes
     * @return
     */
//    public void convertDrumTrackDataToMidi(DrumTrackData drumTrackData) {
//        MidiTrack tempoTrack = new MidiTrack();
//        MidiTrack noteTrack = new MidiTrack();
//
//        for (DrumComponent drumComponent : drumTrackData.getDrumComponentList()) {
//            for (int i = 0; i < drumComponent.getBeats().length; i++) {
//                if (drumComponent.getBeats()[i] == 1) {
//                    noteTrack.insertNote(9, drumComponent.getName(), 100, i*240, 140);
//                }
//            }
//        }
//        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
//        tracks.add(tempoTrack);
//        tracks.add(noteTrack);
//        midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
//    }

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
