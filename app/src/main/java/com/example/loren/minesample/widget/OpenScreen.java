package com.example.loren.minesample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Copyright (c) 17-1-19 by loren
 */

public class OpenScreen extends View {

    public boolean isTouch = true;//是否可用
    private float widthArea = 0f;
    private float heightArea = 0f;
    private ArrayList<PointF> positionArr = new ArrayList<>();//9个点的坐标
    private float CIRCLE_RADIUS = 0;//9个圆的半径
    private Paint mPaint;
    private Paint mLinePaint;//圆和触摸点的线
    private float[][] a = new float[9][2];//9个点的二维数组
    private ArrayList<Integer> curCircle = new ArrayList<>();//已连接的所有点的下标
    private float moveX = 0f;
    private float moveY = 0f;
    private onDialogListener listener;
    private String password = "";//设置的锁屏密码(一串数字)
    private Paint mClickPaint;//点击的点

    public OpenScreen(Context context) {
        super(context);
        init();
    }

    public OpenScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setIsTouch(boolean flag) {
        isTouch = flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return isTouch && super.dispatchTouchEvent(event);
    }

    public void setListener(onDialogListener listener) {
        this.listener = listener;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    private void init() {
        //点正常状态的属性
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        for (int i = 0; i < 9; i++) {
            positionArr.add(new PointF());
        }
        //点击的点的属性
        mClickPaint = new Paint();
        mClickPaint.setColor(Color.GRAY);
        mClickPaint.setStyle(Paint.Style.STROKE);
        mClickPaint.setStrokeWidth(30);
        mClickPaint.setAntiAlias(true);
        //点击的点和触摸点之间的连线
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(50);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 9等分屏幕
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //锁屏3x3点矩阵
        widthArea = width / 3;
        heightArea = height / 3;
        CIRCLE_RADIUS = widthArea / 4;//设置半径
        //根据屏幕设置9个坐标点
        a[0][0] = widthArea / 2;
        a[1][0] = widthArea * 2 * 0.75f;
        a[2][0] = widthArea * 3 * 5 / 6;
        a[3][0] = widthArea / 2;
        a[4][0] = widthArea * 2 * 0.75f;
        a[5][0] = widthArea * 3 * 5 / 6;
        a[6][0] = widthArea / 2;
        a[7][0] = widthArea * 2 * 0.75f;
        a[8][0] = widthArea * 3 * 5 / 6;
        a[0][1] = heightArea / 2;
        a[1][1] = heightArea / 2;
        a[2][1] = heightArea / 2;
        a[3][1] = heightArea * 2 * 0.75f;
        a[4][1] = heightArea * 2 * 0.75f;
        a[5][1] = heightArea * 2 * 0.75f;
        a[6][1] = heightArea * 3 * 5 / 6;
        a[7][1] = heightArea * 3 * 5 / 6;
        a[8][1] = heightArea * 3 * 5 / 6;
        for (int i = 0; i < positionArr.size(); i++) {
            positionArr.get(i).x = a[i][0];
            positionArr.get(i).y = a[i][1];
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画出已连接的点
        for (int i = 0; i < positionArr.size(); i++) {
            if (curCircle.contains(i)) {
                canvas.drawCircle(positionArr.get(i).x, positionArr.get(i).y, CIRCLE_RADIUS, mClickPaint);
            } else {
                canvas.drawCircle(positionArr.get(i).x, positionArr.get(i).y, CIRCLE_RADIUS, mPaint);
            }
        }
        for (int i = 0; i < curCircle.size(); i++) {
            if (i + 1 < curCircle.size()) {
                canvas.drawLine(positionArr.get(curCircle.get(i)).x, positionArr.get(curCircle.get(i)).y, positionArr.get(curCircle.get(i + 1)).x, positionArr.get(curCircle.get(i + 1)).y, mLinePaint);
            }
        }
        if (moveX != 0 && moveY != 0 && curCircle.size() > 0 && curCircle.size() < 9 && isTouch) {
            canvas.drawLine(positionArr.get(curCircle.get(curCircle.size() - 1)).x, positionArr.get(curCircle.get(curCircle.size() - 1)).y, moveX, moveY, mLinePaint);
        }
    }

    /**
     * 根据触摸区域判断触摸点并存入数组
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                checkPassword();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
                int pos = whichCircle(event.getX(), event.getY());
                if (pos != -1 && !curCircle.contains(pos)) {
                    curCircle.add(pos);
                    System.out.println(pos);
                }
                postInvalidate();
                break;
        }
        return true;
    }

    /**
     * 检查密码是否正确
     */
    private void checkPassword() {
        StringBuilder pwd = new StringBuilder();
        for (Integer pos : curCircle) {
            pwd.append(pos).append("");
        }
        //连接点数小于等于2则忽略此次连接
        if (curCircle.size() > 2) {
            listener.checkListener(pwd.toString());
            if (!password.equals(pwd.toString())) {
                mLinePaint.setColor(Color.RED);
                mClickPaint.setColor(Color.RED);
                postInvalidate();
                isTouch = false;
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 1000);
            }
        } else {
            reset();
        }
    }

    /**
     * 重置锁屏页
     */
    private void reset() {
        mLinePaint.setColor(Color.YELLOW);
        mClickPaint.setColor(Color.GRAY);
        curCircle.clear();
        postInvalidate();
        isTouch = true;
    }

    /**
     * 判断触摸的是哪一个点
     */
    private int whichCircle(float x, float y) {
        int position = -1;
        for (int i = 0; i < positionArr.size(); i++) {
            if (Math.abs(x - positionArr.get(i).x) < CIRCLE_RADIUS && Math.abs(y - positionArr.get(i).y) < CIRCLE_RADIUS) {
                position = i;
            }
        }
        return position;
    }

    /**
     * 将密码回调，在activity里判断密码是否正确
     */
    public interface onDialogListener {
        void checkListener(String pwd);
    }
}
