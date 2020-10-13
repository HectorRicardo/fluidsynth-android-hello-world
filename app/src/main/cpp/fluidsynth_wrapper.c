#include <jni.h>
#include <fluidsynth.h>

fluid_settings_t* settings;
fluid_synth_t* synth;
fluid_audio_driver_t* adriver;

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_open(JNIEnv* env, jobject thisObj, jstring jSoundfontPath) {
    settings = new_fluid_settings();
    synth = new_fluid_synth(settings);
    adriver = new_fluid_audio_driver(settings, synth);

    const char* soundfontPath = (*env)->GetStringUTFChars(env, jSoundfontPath, NULL);
    fluid_synth_sfload(synth, soundfontPath, 1);
}

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_noteOn(JNIEnv* env, jobject thisObj, jint midiNumber) {
    fluid_synth_noteon(synth, 0, midiNumber, 127);
}

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_noteOff(JNIEnv* env, jobject thisObj, jint midiNumber) {
    fluid_synth_noteoff(synth, 0, midiNumber);
}

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_controlChange(JNIEnv* env, jobject thisObj, jint controller, jint value) {
    fluid_synth_cc(synth, 0, controller, value);
}

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_programChange(JNIEnv* env, jobject thisObj, jint program) {
    fluid_synth_program_change(synth, 0, program);
}

JNIEXPORT void JNICALL Java_com_example_fluidsynthandroidhelloworld_Fluidsynth_closeNative(JNIEnv* env, jobject thisObj) {
    delete_fluid_audio_driver(adriver);
    delete_fluid_synth(synth);
    delete_fluid_settings(settings);
}
