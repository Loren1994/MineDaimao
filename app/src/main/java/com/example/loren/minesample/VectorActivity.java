package com.example.loren.minesample;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanks.htextview.util.DisplayUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VectorActivity extends AppCompatActivity {

    @BindView(R.id.vector_view)
    TextView vectorView;
    @BindView(R.id.volume_view)
    TextView volumeView;
    @BindView(R.id.volume_container_ll)
    LinearLayout volumeContainerLl;
    @BindView(R.id.cur_volume_tv)
    TextView curVolumeTv;
    private AudioRecordDemo mAudioRecord;
    private int CHANGE_SIZE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        ButterKnife.bind(this);
        for (int i = 0; i < 15; i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.volume_item, null);
            volumeContainerLl.addView(item);
        }
        mAudioRecord = new AudioRecordDemo(new AudioRecordDemo.OnVolumeListener() {
            @Override
            public void onVolumeListener(final double volume) {
                Log.d("loren", "分贝值：" + volume);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        curVolumeTv.setText("分贝值：" + String.format(Locale.CHINA, "%.2f", volume));
                    }
                });
                for (int i = 0; i < volumeContainerLl.getChildCount(); i++) {
                    final TextView view = (TextView) ((RelativeLayout) volumeContainerLl.getChildAt(i)).getChildAt(0);
                    final float heightNum = (float) (volume * (i > (volumeContainerLl.getChildCount() / 2) ? (volumeContainerLl.getChildCount() - i) : i));
                    addListener(view);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final ViewGroup.LayoutParams params = view.getLayoutParams();
                            int heightTemp = params.height;
                            ValueAnimator animator = ValueAnimator.ofInt(heightTemp,
                                    DisplayUtils.dp2px(VectorActivity.this, heightNum));
                            animator.setDuration(100);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    params.height = (int) animation.getAnimatedValue();
                                    view.setLayoutParams(params);
                                }
                            });
                            animator.start();
                        }
                    }, 50 * i);
                }
            }
        });
        mAudioRecord.getNoiseLevel();
    }

    private void addListener(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        changeParams(v, MotionEvent.ACTION_UP);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        changeParams(v, MotionEvent.ACTION_DOWN);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    private void changeParams(View view, int event) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (event == MotionEvent.ACTION_DOWN) {
            params.width += CHANGE_SIZE;
            params.height += CHANGE_SIZE;
        } else {
            params.width -= CHANGE_SIZE;
            params.height -= CHANGE_SIZE;
        }
        view.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        mAudioRecord.stopAudioThread();
        super.onDestroy();
    }
}
