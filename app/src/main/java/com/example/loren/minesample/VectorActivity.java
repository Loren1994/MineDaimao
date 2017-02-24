package com.example.loren.minesample;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanks.htextview.util.DisplayUtils;

import java.util.Locale;

public class VectorActivity extends AppCompatActivity {

    private AudioRecordDemo mAudioRecord;
    private TextView mVolumnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        mVolumnTv = (TextView) findViewById(R.id.volume_view);
        mAudioRecord = new AudioRecordDemo(new AudioRecordDemo.OnVolumeListener() {
            @Override
            public void onVolumeListener(final double volume) {
                Log.d("loren", "分贝值：" + volume);
                mVolumnTv.post(new Runnable() {
                    @Override
                    public void run() {
                        mVolumnTv.setText(String.format(Locale.CHINA, "%.2f", volume));
                        final ViewGroup.LayoutParams params = mVolumnTv.getLayoutParams();
                        int heightTemp = params.height;
                        ValueAnimator animator = ValueAnimator.ofInt(heightTemp, DisplayUtils.dp2px(VectorActivity.this, (float) volume * 2.0f));
                        animator.setDuration(100);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                params.height = (int) animation.getAnimatedValue();
                                mVolumnTv.setLayoutParams(params);
                            }
                        });
                        animator.start();
                    }
                });
            }
        });
        mAudioRecord.getNoiseLevel();
    }

    @Override
    protected void onDestroy() {
        mAudioRecord.stopAudioThread();
        super.onDestroy();
    }
}
