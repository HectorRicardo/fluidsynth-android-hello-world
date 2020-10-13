package com.example.fluidsynthandroidhelloworld;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Fluidsynth {

    public static final Fluidsynth synthesizer = new Fluidsynth();
    private Fluidsynth() {}

    private boolean open = false;

    public void open(String soundfontFileName, Context context) {
        if (open) {
            throw new RuntimeException("Synthesizer is already open!");
        }
        try {
            String tempSoundfontPath = copyAssetToTempFile(context, soundfontFileName);
            open(tempSoundfontPath);
            open = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        closeNative();
        open = false;
    }

    private String copyAssetToTempFile(Context context, String fileName) throws IOException {
        try (InputStream is = context.getAssets().open(fileName)) {
            String tempFileName = "tmp_" + fileName;
            try (FileOutputStream fos = context.openFileOutput(tempFileName, Context.MODE_PRIVATE)) {
                int bytes_read;
                byte[] buffer = new byte[4096];
                while ((bytes_read = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes_read);
                }
            }
            return context.getFilesDir() + "/" + tempFileName;
        }
    }

    private native void open(String tempSoundfontPath);
    private native void closeNative();

    public native void noteOn(int midiNumber);
    public native void noteOff(int midiNumber);
    public native void controlChange(int controller, int value);
    public native void programChange(int program);

    static {
        System.loadLibrary("fluidsynth_wrapper");
    }
}