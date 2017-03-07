package com.example.loren.minesample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loren.minesample.widget.OpenScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenScreenActivity extends Activity implements OpenScreen.onDialogListener {

    @BindView(R.id.open_os)
    OpenScreen openOs;
    @BindView(R.id.victor_iv)
    ImageView victorIv;
    @BindView(R.id.msg_tv)
    TextView msgTv;
    @BindView(R.id.parent_rl)
    RelativeLayout parentRl;
    private String password = "0124678";
    private ObjectAnimator animator;
    private long TOTAL_TIME = 10000;
    private int errorNum = 0;
    private Timer timer;
    private Animator endAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
//        setAnim();
        ButterKnife.bind(this);
        openOs.setListener(this);
        openOs.setPassword(password);
        animator = ObjectAnimator.ofFloat(victorIv, "alpha", 0f, 1f).setDuration(1000);
        timer = new Timer(TOTAL_TIME, 1000);

        startAnim();
    }


    private void setAnim() {
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));
    }

    @Override
    public void checkListener(String pwd) {
        Log.d("loren", "pwd:" + pwd);
        if (pwd.equals(password)) {
            Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show();
            openOs.setIsTouch(false);
            animator.start();
        } else {
            errorNum++;
            isStopScreen();
            Toast.makeText(this, "密码错误！！", Toast.LENGTH_SHORT).show();
        }
    }

    private void isStopScreen() {
        if (errorNum > 5) {
//            startCountDown();
            timer.start();
        }
    }

    private void startAnim() {
        parentRl.post(new Runnable() {
            @Override
            public void run() {
                //圆形动画的x,y坐标  位于View的中心
                int cx = (parentRl.getLeft() + parentRl.getRight()) / 2;
                int cy = (parentRl.getTop() + parentRl.getBottom()) / 2;
                //起始大小半径
                float startX = 0f;
                //结束大小半径 大小为对角线的一半
                float startY = (float) Math.sqrt(cx * cx + cy * cy);
                Animator animator = ViewAnimationUtils.createCircularReveal(parentRl, cx, cy, startX, startY);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(600);
                animator.start();
            }
        });
    }

    private void endAnim() {
        parentRl.post(new Runnable() {
            @Override
            public void run() {
                int cx = (parentRl.getLeft() + parentRl.getRight()) / 2;
                int cy = (parentRl.getTop() + parentRl.getBottom()) / 2;
                float startR = 0f;
                float endR = (float) Math.sqrt(cx * cx + cy * cy);
                endAnim = ViewAnimationUtils.createCircularReveal(parentRl, cx, cy, endR, startR);
                endAnim.setInterpolator(new AccelerateInterpolator());
                endAnim.setDuration(600);
                endAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        timer.cancel();
                        getWindow().getDecorView().getRootView().setAlpha(0f);
                        finish();
                        overridePendingTransition(0, 0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                endAnim.start();
            }
        });
    }


    @Override
    public void onBackPressed() {
        endAnim();
    }

    class Timer extends CountDownTimer {

        Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            msgTv.setVisibility(View.VISIBLE);
            openOs.setIsTouch(false);
            msgTv.setText("手机已停用，请" + (millisUntilFinished / 1000) + "s后重试");
            parentRl.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
        }

        @Override
        public void onFinish() {
            openOs.setIsTouch(true);
            errorNum = 0;
            msgTv.setVisibility(View.GONE);
            parentRl.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }
//    private void startCountDown() {
//        msgTv.setVisibility(View.VISIBLE);
//        Observable.interval(1, TimeUnit.SECONDS, Schedulers.newThread())
//                .take(TOTAL_TIME).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
