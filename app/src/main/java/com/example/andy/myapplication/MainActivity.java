package com.example.andy.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MidiPlayer midiPlayer;
    private DrumTrackData drumTrackData;

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
        drumTrackData = new DrumTrackData();

        /****************? Dummy object data for testing *****************/
//        drumTrackData = new DrumTrackData();
//        drumTrackData.addDrumHit(DrumTrackData.KICK, 0);
//        drumTrackData.addDrumHit(DrumTrackData.KICK, 2);
//        drumTrackData.addDrumHit(DrumTrackData.KICK, 4);
//        drumTrackData.addDrumHit(DrumTrackData.KICK, 6);
//        drumTrackData.addDrumHit(DrumTrackData.SNARE, 2);
//        drumTrackData.addDrumHit(DrumTrackData.SNARE, 6);

        midiPlayer = new MidiPlayer();
        midiPlayer.writeToFile(this, midiPlayer.getMidi());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
//                mp.release();
            }
        });


        Button button = findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                midiPlayer.convertDrumTrackDataToMidi(drumTrackData);
//                midiPlayer.writeToFile(getApplicationContext(), midiPlayer.getMidi());
//                loadFileIntoMediaPlayer();
//
//                mediaPlayer.start();

//                drumTrackData.processCommand("insert kick B1");
//                midiPlayer.convertDrumTrackDataToMidi(drumTrackData);
//                midiPlayer.writeToFile(getApplicationContext(), midiPlayer.getMidi());
//                loadFileIntoMediaPlayer();
//                mediaPlayer.start();

                // button for playback of file, needs to load fresh from internal mem each time
                // incase of any add/removes of notes from Midi file.
                loadFileIntoMediaPlayer();
                mediaPlayer.start();
            }
        });
    }

    public void loadFileIntoMediaPlayer() {
        File file = getApplicationContext().getFileStreamPath("exampleout.mid");
        Uri uri = Uri.fromFile(file);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (Exception e) {
            // SORT THIS LATER!!!
        }
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {

        }

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


                    // This is the same as calling drumTrackData.addDrumHit but with voice input
                    drumTrackData.processCommand(newResult);
                    midiPlayer.convertDrumTrackDataToMidi(drumTrackData, 40);
                    midiPlayer.writeToFile(getApplicationContext(), midiPlayer.getMidi());

                    loadFileIntoMediaPlayer();
                    mediaPlayer.start();

                }
                break;
            }
        }
    }
}