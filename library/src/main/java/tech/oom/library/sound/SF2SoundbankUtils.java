package tech.oom.library.sound;

import android.content.Context;

import java.io.IOException;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;
import cn.sherlock.com.sun.media.sound.SoftSynthesizer;
import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
import jp.kshoji.javax.sound.midi.MidiUnavailableException;
import jp.kshoji.javax.sound.midi.Receiver;
import jp.kshoji.javax.sound.midi.ShortMessage;

/**
 * Created by lgy on 2019/1/28
 */
public class SF2SoundbankUtils {
    private static SoftSynthesizer synth;
    private static Receiver recv;
    public static void init(Context context){
        SF2Soundbank sf = null;
        try {
            sf = new SF2Soundbank(context.getAssets().open("YDTECPlay.sf2"));
            synth = new SoftSynthesizer();
            try {
                synth.open();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
            synth.loadAllInstruments(sf);
            synth.getChannels()[0].programChange(0);
            synth.getChannels()[1].programChange(1);
            try {
                recv = synth.getReceiver();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void soundMidiOn(int noteId){
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(ShortMessage.NOTE_ON, 0, noteId, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        recv.send(msg, -1);
    }

    public static void soundMidiOff(int noteId){
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(ShortMessage.NOTE_OFF, 0, noteId, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        recv.send(msg, -1);
    }
}
