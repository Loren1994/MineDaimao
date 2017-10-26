package com.example.loren.minesample

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.loren.minesample.base.ui.BaseActivity

import com.example.loren.minesample.widget.OpenScreen
import kotlinx.android.synthetic.main.activity_open_screen.*


class OpenScreenActivity : BaseActivity(), OpenScreen.onDialogListener {
    override fun initWidgets() {
        open_os!!.setListener(this)
        open_os!!.setPassword(password)
        animator = ObjectAnimator.ofFloat(victor_iv, "alpha", 0f, 1f).setDuration(1000)
        timer = Timer(TOTAL_TIME, 1000)

        startAnim()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout()=R.layout.activity_open_screen

    private val password = "0124678"
    private var animator: ObjectAnimator? = null
    private val TOTAL_TIME: Long = 10000
    private var errorNum = 0
    private var timer: Timer? = null
    private var endAnim: Animator? = null

    private fun setAnim() {
        window.enterTransition = Fade().setDuration(1000)
        window.exitTransition = Fade().setDuration(1000)
    }

    override fun checkListener(pwd: String) {
        Log.d("loren", "pwd:" + pwd)
        if (pwd == password) {
            Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show()
            open_os!!.setIsTouch(false)
            animator!!.start()
        } else {
            errorNum++
            isStopScreen()
            Toast.makeText(this, "密码错误！！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isStopScreen() {
        if (errorNum > 5) {
            //            startCountDown();
            timer!!.start()
        }
    }

    private fun startAnim() {
        parent_rl!!.post {
            //圆形动画的x,y坐标  位于View的中心
            val cx = (parent_rl!!.left + parent_rl!!.right) / 2
            val cy = (parent_rl!!.top + parent_rl!!.bottom) / 2
            //起始大小半径
            val startX = 0f
            //结束大小半径 大小为对角线的一半
            val startY = Math.sqrt((cx * cx + cy * cy).toDouble()).toFloat()
            val animator = ViewAnimationUtils.createCircularReveal(parent_rl, cx, cy, startX, startY)
            animator.interpolator = AccelerateInterpolator()
            animator.duration = 600
            animator.start()
        }
    }

    private fun endAnim() {
        parent_rl!!.post {
            val cx = (parent_rl!!.left + parent_rl!!.right) / 2
            val cy = (parent_rl!!.top + parent_rl!!.bottom) / 2
            val startR = 0f
            val endR = Math.sqrt((cx * cx + cy * cy).toDouble()).toFloat()
            endAnim = ViewAnimationUtils.createCircularReveal(parent_rl, cx, cy, endR, startR)
            endAnim!!.interpolator = AccelerateInterpolator()
            endAnim!!.duration = 600
            endAnim!!.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    timer!!.cancel()
                    window.decorView.rootView.alpha = 0f
                    finish()
                    overridePendingTransition(0, 0)
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
            endAnim!!.start()
        }
    }


    override fun onBackPressed() {
        endAnim()
    }

    internal inner class Timer(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            msg_tv!!.visibility = View.VISIBLE
            open_os!!.setIsTouch(false)
            msg_tv!!.text = "手机已停用，请" + millisUntilFinished / 1000 + "s后重试"
            parent_rl!!.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.grey))
        }

        override fun onFinish() {
            open_os!!.setIsTouch(true)
            errorNum = 0
            msg_tv!!.visibility = View.GONE
            parent_rl!!.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
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
