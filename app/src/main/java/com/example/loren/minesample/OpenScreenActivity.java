package com.example.loren.minesample;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loren.minesample.widget.OpenScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenScreenActivity extends AppCompatActivity implements OpenScreen.onDialogListener {

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
    private long TOTAL_TIME = 5000;
    private int errorNum = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        ButterKnife.bind(this);
        openOs.setListener(this);
        openOs.setPassword(password);
        animator = ObjectAnimator.ofFloat(victorIv, "alpha", 0f, 1f).setDuration(1000);
        timer = new Timer(TOTAL_TIME, 1000);
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

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }


    class Timer extends CountDownTimer {

        Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            msgTv.setVisibility(View.VISIBLE);
            openOs.setIsTouch(false);
            msgTv.setText("手机已停用，请" + millisUntilFinished / 1000 + "s后重试");
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
