package com.example.andy.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int KICK = 36;
    private final int SNARE = 38;
    private final int HIHAT_CLOSE = 42;
    private final int HIHAT_OPEN = 46;
    MediaPlayer mediaPlayer;

    private final int REQ_CODE = 100;
    TextView textView;

    // My Code
    private SecondParser secondParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        ImageView speak = findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // My Code
        secondParser = new SecondParser();


        /********************** MIDI *************************************/
        // 1. Create some MidiTracks
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

// 2. Add events to the tracks
// Track 0 is the tempo map
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(120);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);


        noteTrack.insertNote(9, KICK, 100, 1, 140);
        noteTrack.insertNote(9, KICK, 100, 480, 140);
        noteTrack.insertNote(9, SNARE, 100, 480, 140);
        noteTrack.insertNote(9, KICK, 100, 2*480, 140);
        noteTrack.insertNote(9, KICK, 100, 3*480, 140);


// 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

        Log.v("getType()", Integer.toString(midi.getType()));

// 4. Write the MIDI data to a file
        File output = new File(getApplicationContext().getFilesDir(), "exampleout.mid");
        File textFile = new File(getApplicationContext().getFilesDir(), "test.mid");
        try
        {
            midi.writeToFile(output);
            midi.writeToFile(textFile);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }


         mediaPlayer = new MediaPlayer();
        File file = getApplicationContext().getFileStreamPath("exampleout.mid");
        Uri uri = Uri.fromFile(file);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (Exception e) {

        }

//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.escape);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {

        }


        /*****************************************************************/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    textView.setText(result.get(0));

                    // My Code
                    String newResult = secondParser.parseInput(result.get(0));
                    textView.setText(newResult);

                    mediaPlayer.start();

                }
                break;
            }
        }
    }
}