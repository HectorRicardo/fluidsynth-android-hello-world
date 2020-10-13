package com.example.fluidsynthandroidhelloworld;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.fluidsynthandroidhelloworld.Fluidsynth.synthesizer;

public class MainActivity extends AppCompatActivity {

    private boolean playing = false;

    private final Runnable melodyRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                synthesizer.noteOn(60);
                synthesizer.noteOn(64);
                synthesizer.noteOn(67);
                Thread.sleep(500);
                synthesizer.noteOff(60);
                synthesizer.noteOff(64);
                synthesizer.noteOff(67);

                synthesizer.noteOn(62);
                synthesizer.noteOn(65);
                synthesizer.noteOn(69);
                Thread.sleep(500);
                synthesizer.noteOff(62);
                synthesizer.noteOff(65);
                synthesizer.noteOff(69);

                synthesizer.noteOn(64);
                synthesizer.noteOn(67);
                synthesizer.noteOn(71);
                Thread.sleep(500);
                synthesizer.noteOff(64);
                synthesizer.noteOff(67);
                synthesizer.noteOff(71);

            } catch (InterruptedException ignored) {
            } finally {
                playing = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synthesizer.open("sndfnt.sf2", this);

        synthesizer.programChange(0);
        synthesizer.controlChange(65, 127);
        synthesizer.controlChange(5, 1);

        play();
    }

    public void play(View view) {
        play();
    }

    public void play() {
        if (playing) return;
        playing = true;
        new Thread(melodyRunnable).start();
    }

    @Override
    protected void onDestroy() {
        synthesizer.close();
        super.onDestroy();
    }
}
