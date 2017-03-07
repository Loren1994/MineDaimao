package com.example.loren.minesample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class AmazingActivity extends AppCompatActivity {

    private boolean isStart = false;
    private float curX;
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazing);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isStart = event.getRawX() < 100;
                curX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isStart) {
                    getWindow().getDecorView().setX(event.getRawX());
                }
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                if (isStart) {
                    if (velocityTracker.getXVelocity() > 5000) {
                        startAnim(event.getRawX(), true);
                    } else {
                        startAnim(event.getRawX(), event.getRawX() > App.SCREEN_WIDTH / 2);
                    }
                }
                velocityTracker.clear();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startAnim(float curX, final boolean isFinish) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(getWindow().getDecorView(), "x",
                curX, isFinish ? App.SCREEN_WIDTH : 0f).setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFinish) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
