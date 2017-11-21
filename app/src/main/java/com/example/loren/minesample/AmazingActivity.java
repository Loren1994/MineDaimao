package com.example.loren.minesample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.example.loren.minesample.base.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import static pers.victor.ext.CommonExtKt.findColor;


/**
 * Copyright (c) 16-9-19 by loren
 * ._       _____    ____    _____   __   _
 * | |     /  _  \ |  _  \  | ____| |  \ | |
 * | |     | | | | | |_| |  | |__   |   \| |
 * | |     | | | | |  _  /  |  __|  | |\   |
 * | |___  | |_| | | | \ \  | |___  | | \  |
 * |_____| \_____/ |_|  \_\ |_____| |_|  \_|
 */
public class AmazingActivity extends BaseActivity {

    private boolean isStart = false;
    private float curX;
    private VelocityTracker velocityTracker;

    @Override
    public boolean useTitleBar() {
        return false;
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
                        startAnim(event.getRawX(), event.getRawX() > App.Companion.getSCREEN_WIDTH() / 2);
                    }
                }
                velocityTracker.clear();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startAnim(float curX, final boolean isFinish) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(getWindow().getDecorView(), "x",
                curX, isFinish ? App.Companion.getSCREEN_WIDTH() : 0f).setDuration(500);
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

    @Override
    public void initWidgets() {
        getWindow().setBackgroundDrawable(new ColorDrawable(findColor(R.color.transparent)));
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onWidgetsClick(@NotNull View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_amazing;
    }
}
