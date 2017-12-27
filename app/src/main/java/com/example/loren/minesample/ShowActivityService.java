package com.example.loren.minesample;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import static pers.victor.ext.DisplayExtKt.getStatusBarHeight;

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

    public static void setTv(String pkg, String act, String dePkg, String deAct) {
        if (null == windowView) {
            return;
        }
        ((TextView) windowView.findViewById(R.id.activity_tv)).setText(act);
        ((TextView) windowView.findViewById(R.id.de_activity_tv)).setText(deAct);
        ((TextView) windowView.findViewById(R.id.package_tv)).setText(pkg);
        ((TextView) windowView.findViewById(R.id.de_package_tv)).setText(dePkg);
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
                        isClick = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isClick = false;
                        windowParams.x = (int) event.getRawX() - windowView.getMeasuredWidth() / 2;
                        windowParams.y = (int) event.getRawY() - windowView.getMeasuredHeight() / 2 - getStatusBarHeight();
                        windowManager.updateViewLayout(windowView, windowParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isClick) {
                            return false;
                        }
                        if (event.getRawX() >= App.Companion.getSCREEN_WIDTH() / 2) {
                            ValueAnimator animator = new ValueAnimator();
                            animator.setDuration(200);
                            animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth());
                            animator.setInterpolator(new DecelerateInterpolator());
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    windowParams.x = (int) animation.getAnimatedValue();
                                    windowParams.y = (int) event.getRawY() - windowView.getMeasuredHeight() / 2 - getStatusBarHeight();
                                    windowManager.updateViewLayout(windowView, windowParams);
                                }
                            });
                            animator.start();
                        } else {
                            ValueAnimator animator = new ValueAnimator();
                            animator.setDuration(200);
                            animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, 0);
                            animator.setInterpolator(new DecelerateInterpolator());
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    windowParams.x = (int) animation.getAnimatedValue();
                                    windowParams.y = (int) event.getRawY() - windowView.getMeasuredHeight() / 2 - getStatusBarHeight();
                                    windowManager.updateViewLayout(windowView, windowParams);
                                }
                            });
                            animator.start();
                        }
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
