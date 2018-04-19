package com.example.loren.minesample.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.loren.minesample.App;
import com.example.loren.minesample.R;

/**
 * Copyright © 2017/12/27 by loren
 */

@SuppressLint("StaticFieldLeak")
public class ShowActivityService extends Service {
    private static View windowView;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowParams;
    private long clickTime = 0;
    private boolean isClick = false;
    private boolean isMoving = false;
    private Point preP, curP;

    public static void setTv(String pkg, String act) {
        if (null == windowView) {
            return;
        }
        ((TextView) windowView.findViewById(R.id.activity_tv)).setText(act);
        ((TextView) windowView.findViewById(R.id.package_tv)).setText(pkg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initWindows();
        initView();
        windowManager.addView(windowView, windowParams);
    }

    private void initWindows() {
        windowManager = (WindowManager) getApplication().getSystemService(Application.WINDOW_SERVICE);
        //TYPE_TOAST:无需申请悬浮窗权限(<=Build.VERSION_CODES.N) - TYPE_PHONE:需申请
        windowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        windowParams.gravity = Gravity.START | Gravity.TOP;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        windowView = LayoutInflater.from(this).inflate(R.layout.show_activity_service, null);
        windowView.findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMoving) {
                    return;
                }
                if (System.currentTimeMillis() - clickTime < 500) {
                    isClick = true;
                    stopSelf();
                    windowManager.removeViewImmediate(windowView);
                } else {
                    clickTime = System.currentTimeMillis();
                }
            }
        });
        windowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        preP = new Point((int) event.getRawX(), (int) event.getRawY());
                        isClick = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isClick = false;
                        isMoving = true;
                        curP = new Point((int) event.getRawX(), (int) event.getRawY());
                        int dx = curP.x - preP.x, dy = curP.y - preP.y;
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                        layoutParams.x += dx;
                        layoutParams.y += dy;
                        windowManager.updateViewLayout(windowView, layoutParams);
                        preP = curP;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isClick) {
                            return false;
                        }
                        ValueAnimator animator = new ValueAnimator();
                        animator.setDuration(400);
                        animator.setInterpolator(new DecelerateInterpolator());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                                layoutParams.x = (int) animation.getAnimatedValue();
                                windowManager.updateViewLayout(windowView, layoutParams);
                            }
                        });
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                isMoving = false;
                            }
                        });
                        if (curP.x >= App.Companion.getSCREEN_WIDTH() / 2) {
                            animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth());
                        } else {
                            animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, 0);
                        }
                        animator.start();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
