package com.example.andy.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TextView tempo;

    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener afChangeListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(this, UserManual.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

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


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                pauseButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                playPauseVisabilitySetup();
                requestAudioFocus();
            }
        });

        pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                playPauseVisabilitySetup();
                requestAudioFocus();
            }
        });
        tempo = findViewById(R.id.tempo);

        // setup audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Permanent loss of audio focus
                    // Pause playback immediately
                    mediaPlayer.pause();
                    playPauseVisabilitySetup();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    // Pause playback
                    mediaPlayer.pause();
                    playPauseVisabilitySetup();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Lower the volume, keep playing
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Your app has been granted audio focus again
                    // Raise volume to normal, restart playback if necessary
                    mediaPlayer.start();

                }
            }
        };

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

    public void hidePlayPause() {
        playButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
    }

    public void loadFileIntoMediaPlayer() {
        File file = getApplicationContext().getFileStreamPath("exampleout.mid");
        Uri uri = Uri.fromFile(file);
        try {
            // this needs reset coz mediaplayer holds onto files even when they are re-written at source
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (Exception e) {
            // SORT THIS LATER!!!
            hidePlayPause();
        }
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            hidePlayPause();
        }

    }

    public void toastEmptyUndoStack() {
            CharSequence text = "There is nothing to undo!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
    }

    public void toastMethod(CharSequence text) {
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
    }

    public void requestAudioFocus() {
        mAudioManager.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
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
                        pauseButton.setVisibility(View.INVISIBLE);
                    } else if (!dtdResult.isCommandRecognised()) {
                        toastMethod("Command Not Recognised");
                    } else if (dtdResult.isReset()) {
                        // the media player needs to clear file it was playing as the drumTrackData
                        // has been wiped of it's state.
                        mediaPlayer.reset();
                        pauseButton.setVisibility(View.INVISIBLE);
                    } else if (dtdResult.isTempoChanged()) {
                        String parsed = Integer.toString(drumTrackData.getTempo());
                        tempo.setText(parsed);
                    }

                    // Only convert and write to file if there has been a change in the state
//                    else if (dtdResult.isStateChanged()) {
//
//                    }

                    midiPlayer.convertDrumTrackDataToMidi(drumTrackData, 40);
                    midiPlayer.writeToFile(getApplicationContext(), midiPlayer.getMidi());

                    loadFileIntoMediaPlayer();

                    requestAudioFocus();
                    mediaPlayer.start();
                    // check for an empty drumComponentList and remove play/pause buttons as
                    // there is nothing to be played or paused when this happens. Otherwise,
                    // display buttons with playPauseVisabilitySetup().
                    if (dtdResult.isComponentListEmpty()) {
                        hidePlayPause();
                    } else {
                        playPauseVisabilitySetup();
                    }

                }
                break;
            }
        }
    }

}