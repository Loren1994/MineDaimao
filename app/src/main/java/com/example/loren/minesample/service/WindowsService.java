package com.example.loren.minesample.service;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loren.minesample.App;
import com.example.loren.minesample.ChatActivity;
import com.example.loren.minesample.FlagActivity;
import com.example.loren.minesample.OpenScreenActivity;
import com.example.loren.minesample.R;

/**
 * Created by loren on 2017/2/16.
 */

public class WindowsService extends Service {

    private TextView windowTv;
    private LinearLayout windowParent;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private View windowView;
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;
    private int offset = -40;
    private Timer timer;
    private boolean isLeft = true;
    private boolean isClickVisible = false;
    private LinearLayout windowLl;
    private Point preP, curP;
    private boolean isClick = false;

    @Override

    public void onCreate() {
        super.onCreate();
        timer = new Timer(3000, 1000);
        initWindows();
        initView();
        windowManager.addView(windowView, windowParams);
    }

    private void initWindows() {
        windowManager = (WindowManager) getApplication().getSystemService(Application.WINDOW_SERVICE);
        //TYPE_TOAST:无需申请悬浮窗权限(<=Build.VERSION_CODES.N) TYPE_PHONE:需申请
        windowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        windowParams.gravity = Gravity.START | Gravity.TOP;
        windowParams.x = offset;
        windowParams.y = 500;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        windowView = LayoutInflater.from(this).inflate(R.layout.windows_service, null);
        windowTv = windowView.findViewById(R.id.windows_tv);
        windowParent = windowView.findViewById(R.id.parent_ll);
        windowLl = windowView.findViewById(R.id.container);
        setWindowItemListener((TextView) windowView.findViewById(R.id.open_screen_tv), OpenScreenActivity.class);
        setWindowItemListener((TextView) windowView.findViewById(R.id.flag_tv), FlagActivity.class);
        setWindowItemListener((TextView) windowView.findViewById(R.id.chat_tv), ChatActivity.class);
        windowView.findViewById(R.id.stop_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlWindowParent();
                stopSelf();
                windowManager.removeViewImmediate(windowView);
            }
        });
        windowTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isClick = true;
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        preP = new Point((int) event.getRawX(), (int) event.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isClick = false;
                        timer.cancel();
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();
                        if (isInterrupt()) {
                            isClickVisible = true;
                            curP = new Point((int) event.getRawX(), (int) event.getRawY());
                            int dx = curP.x - preP.x, dy = curP.y - preP.y;
                            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                            layoutParams.x += dx;
                            layoutParams.y += dy;
                            windowManager.updateViewLayout(windowView, layoutParams);
                            preP = curP;
                            windowParent.setVisibility(View.GONE);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isClick) {
                            return false;
                        }
                        timer.cancel();
                        timer.start();
                        if (isInterrupt()) {
                            if (event.getRawX() >= App.Companion.getSCREEN_WIDTH() / 2) {
                                isLeft = false;
                                ValueAnimator animator = new ValueAnimator();
                                animator.setDuration(200);
                                animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth());
                                animator.setInterpolator(new DecelerateInterpolator());
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                                        layoutParams.x = (int) animation.getAnimatedValue();
                                        windowManager.updateViewLayout(windowView, layoutParams);
                                    }
                                });
                                animator.start();
                            } else {
                                isLeft = true;
                                ValueAnimator animator = new ValueAnimator();
                                animator.setDuration(200);
                                animator.setIntValues((int) event.getRawX() - windowView.getMeasuredWidth() / 2, 0);
                                animator.setInterpolator(new DecelerateInterpolator());
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                                        layoutParams.x = (int) animation.getAnimatedValue();
                                        windowManager.updateViewLayout(windowView, layoutParams);
                                    }
                                });
                                animator.start();
                            }
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
        windowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickVisible)
                    controlWindowParent();
                else
                    clickVisible();
            }
        });
    }

    private void clickVisible() {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(150);
        if (isLeft)
            animator.setIntValues(offset, 0);
        else
            animator.setIntValues(App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth() + Math.abs(offset), App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth());
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                layoutParams.x = (int) animation.getAnimatedValue();
                windowManager.updateViewLayout(windowView, layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isClickVisible = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void autoGone() {
        windowParent.setVisibility(View.GONE);
        windowView.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = new ValueAnimator();
                animator.setDuration(150);
                if (isLeft)
                    animator.setIntValues(0, offset);
                else
                    animator.setIntValues(App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth(), App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth() + Math.abs(offset));
                animator.setInterpolator(new DecelerateInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowView.getLayoutParams();
                        layoutParams.x = (int) animation.getAnimatedValue();
                        windowManager.updateViewLayout(windowView, layoutParams);
                    }
                });
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isClickVisible = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });
    }

    private boolean isInterrupt() {
        return Math.abs(endX - startX) > ViewConfiguration.get(windowView.getContext()).getScaledTouchSlop() || Math.abs(endY - startY) > ViewConfiguration.get(windowView.getContext()).getScaledTouchSlop();
    }

    private void setWindowItemListener(TextView tv, final Class clazz) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlWindowParent();
                Intent intent = new Intent(getApplicationContext(), clazz);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void controlWindowParent() {
        windowParent.setVisibility(windowParent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        if (isLeft) {
            windowLl.removeView(windowParent);
            windowLl.addView(windowParent);
        } else {
            windowView.post(new Runnable() {
                @Override
                public void run() {
                    windowParams.x = App.Companion.getSCREEN_WIDTH() - windowView.getMeasuredWidth();
                    windowManager.updateViewLayout(windowView, windowParams);
                }
            });
            windowLl.removeAllViews();
            windowLl.addView(windowParent);
            windowLl.addView(windowTv);
        }
        if (windowParent.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(windowParent, "alpha", 0, 1);
            animator.setDuration(500);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }
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
        timer.cancel();
        super.onDestroy();
    }

    class Timer extends CountDownTimer {
        Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            autoGone();
        }
    }
}
