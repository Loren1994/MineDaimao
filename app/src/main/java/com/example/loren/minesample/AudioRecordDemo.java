package com.example.loren.minesample;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by loren on 2017/2/23.
 */

public class AudioRecordDemo {
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private static final String TAG = "AudioRecord";
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun;
    Object mLock;
    private Thread mAudioThread;
    private OnVolumeListener onVolumeListener;


    public AudioRecordDemo(final OnVolumeListener onVolumeListener) {
        this.onVolumeListener = onVolumeListener;
        mLock = new Object();
        mAudioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (short aBuffer : buffer) {
                        v += aBuffer * aBuffer;
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
//                    Log.d(TAG, "分贝值:" + volume);
                    onVolumeListener.onVolumeListener(volume);
                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        });
    }

    public void getNoiseLevel() {
        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;

        mAudioThread.start();
    }

    public void stopAudioThread() {
        isGetVoiceRun = false;
    }

    interface OnVolumeListener {
        void onVolumeListener(double volume);
    }
}
