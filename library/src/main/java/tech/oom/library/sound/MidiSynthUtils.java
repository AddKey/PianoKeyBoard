package tech.oom.library.sound;

import android.content.Context;
import android.util.Log;

import org.herac.tuxguitar.player.impl.midiport.fluidsynth.MidiSynth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lgy on 2019/1/22
 */
public class MidiSynthUtils {
    private static final String TAG = "MidiSynthUtils";
    private static MidiSynth mMidiSynth;
    private static MidiSynthUtils mMidiSynthUtils;

    public MidiSynthUtils(Context context) {
        String filePath = getCopyFile(context,"YDTECPlay.sf2");
        mMidiSynth = new MidiSynth(filePath);
        mMidiSynth.startSynth();
    }

    public static MidiSynthUtils init(Context context) {
        if (mMidiSynthUtils == null)
            mMidiSynthUtils = new MidiSynthUtils(context);
        return mMidiSynthUtils;
    }

    private String getCopyFile(Context context, String fileName)   {
        File cacheFile = new File(context.getFilesDir(), fileName);
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile.getAbsolutePath();
    }

    public static void sendNoteOff(int channel, int key, int velocity){
        if (mMidiSynth!=null){
            mMidiSynth.sendNoteOff(channel, key, velocity);
        }else {
            Log.e(TAG, "MidiSynthUtils is not init ");
        }
    }

    public static void sendNoteOn(int channel, int key, int velocity){
        if (mMidiSynth!=null){
            mMidiSynth.sendNoteOn(channel, key, velocity);
        }else {
            Log.e(TAG, "MidiSynthUtils is not init ");
        }
    }
    public static void destroyMidiSynth(){
       if (mMidiSynth!=null){
           mMidiSynth.destroy();
       }
    }
}
