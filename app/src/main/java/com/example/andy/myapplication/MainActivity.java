package com.example.andy.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private SecondParser secondParser;

    private final int REQ_CODE = 100;
    private TextView textView;
    private ImageView playButton;
    private ImageView pauseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        ImageView speak = findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // stops the audio and removes loaded file on click
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }

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

        secondParser = new SecondParser();
        drumTrackData = new DrumTrackData(new CommandProcessor());


        midiPlayer = new MidiPlayer();
        midiPlayer.writeToFile(this, midiPlayer.getMidi());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                playPauseVisabilitySetup();
            }
        });

        pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                playPauseVisabilitySetup();
            }
        });

    }

    public void playPauseVisabilitySetup() {
        if (mediaPlayer.isPlaying()) {
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
        }
        else {
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
        }
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

    public void toastEmptyUndoStack() {
        if (drumTrackData.getUndoBackStack().size() <= 1) {
            CharSequence text = "There is nothing to undo!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
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

                    String newResult = secondParser.parseInput(result.get(0));
                    textView.setText(newResult);

                    // get a result object from DrumTrackData's parseCommand
                    DTDResult dtdResult = drumTrackData.processCommand(newResult);
                    if (dtdResult.isUndoStackEmpty()) {
                        toastEmptyUndoStack();
                    }
                    // Only convert and write to file if there has been a change in the state
//                    else if (dtdResult.isStateChanged()) {
//
//                    }

                    midiPlayer.convertDrumTrackDataToMidi(drumTrackData, 40);
                    midiPlayer.writeToFile(getApplicationContext(), midiPlayer.getMidi());

                    loadFileIntoMediaPlayer();
                    pauseButton.setVisibility(View.VISIBLE);
                    mediaPlayer.start();
                }
                break;
            }
        }
    }
}