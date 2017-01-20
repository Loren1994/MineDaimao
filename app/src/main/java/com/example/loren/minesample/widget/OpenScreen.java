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

    public boolean isTouch = true;
    private float widthArea = 0f;
    private float heightArea = 0f;
    private ArrayList<PointF> positionArr = new ArrayList<>();
    private float CIRCLE_RADIUS = 0;
    private Paint mPaint;
    private Paint mLinePaint;
    private float[][] a = new float[9][2];
    private ArrayList<Integer> curCircle = new ArrayList<>();
    private float moveX = 0f;
    private float moveY = 0f;
    private onDialogListener listener;
    private String password = "";
    private Paint mClickPaint;

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
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        for (int i = 0; i < 9; i++) {
            positionArr.add(new PointF());
        }
        mClickPaint = new Paint();
        mClickPaint.setColor(Color.GRAY);
        mClickPaint.setStyle(Paint.Style.STROKE);
        mClickPaint.setStrokeWidth(30);
        mClickPaint.setAntiAlias(true);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(50);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        widthArea = width / 3;
        heightArea = height / 3;
        CIRCLE_RADIUS = widthArea / 4;
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

    private void checkPassword() {
        String pwd = "";
        for (Integer pos : curCircle) {
            pwd += pos + "";
        }
        if (curCircle.size() > 2) {
            listener.checkListener(pwd);
            if (!password.equals(pwd)) {
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

    private void reset() {
        mLinePaint.setColor(Color.YELLOW);
        mClickPaint.setColor(Color.GRAY);
        curCircle.clear();
        postInvalidate();
        isTouch = true;
    }

    private int whichCircle(float x, float y) {
        int position = -1;
        for (int i = 0; i < positionArr.size(); i++) {
            if (Math.abs(x - positionArr.get(i).x) < CIRCLE_RADIUS && Math.abs(y - positionArr.get(i).y) < CIRCLE_RADIUS) {
                position = i;
            }
        }
        return position;
    }

    public interface onDialogListener {
        void checkListener(String pwd);
    }
}
