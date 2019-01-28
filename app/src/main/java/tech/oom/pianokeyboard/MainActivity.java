package tech.oom.pianokeyboard;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import tech.oom.library.keyBoard.Key;
import tech.oom.library.keyBoard.PianoKeyBoard;
import tech.oom.library.sound.MidiSynthUtils;
import tech.oom.library.sound.SF2SoundbankUtils;
import tech.oom.library.sound.SoundPlayUtils;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SeekBar seekBar;
    private PianoKeyBoard keyBoard;
    /**
     * 按键的listener
     */
    PianoKeyBoard.KeyListener listener = new PianoKeyBoard.KeyListener() {
        @Override
        public void onKeyPressed(Key key) {
            keyBoard.showCircleAndFinger(key,true,"15");
            if (key.getKeyCode()>21){

                keyBoard.showCircleAndFinger(keyBoard.getKeyByKeycode(key.getKeyCode()-1),false,"22");
            }
//某个键被按下的回调
        }

        @Override
        public void onKeyUp(Key key) {
//某个键被松开的回调
        }

        @Override
        public void currentFirstKeyPosition(int position) {
//            键盘显示的第一个键的index/position更新回调
            seekBar.setMax(keyBoard.getMaxMovePosition());
            seekBar.setProgress(position);
        }
    };

    public String getCopyFile(Context context, String fileName)   {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundPlayUtils.init(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        MidiSynthUtils.init(this);
        SF2SoundbankUtils.init(this);

        seekBar = (SeekBar) findViewById(R.id.activity_play_seek_bar);
        keyBoard = (PianoKeyBoard) findViewById(R.id.keyboard);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                keyBoard.moveToPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setMax(keyBoard.getMaxMovePosition());

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        keyBoard.setKeyCount(8);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyBoard.showPrevious();
//                keyBoard.setPronuncTextDimension(12 * getResources().getDisplayMetrics().scaledDensity);
//            id=  SoundPlayUtils.play(10);
//                mMidiSynth.sendNoteOn(0,55,100);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyBoard.showNext();
                Log.i(TAG, "onClick:stop ");
//                SoundPlayUtils.stop(id);
//                mMidiSynth.sendNoteOff(0,55,100);
            }
        });

        keyBoard.setKeyListener(listener);
        keyBoard.showNext();
        keyBoard.setShowSparse(true);

    }
    int id;

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
