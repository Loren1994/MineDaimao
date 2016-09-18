package com.example.loren.minesample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c) 16-9-18 by loren
 */

public class FiveStarView extends View {

    private Paint mPaint = new Paint();
    private Canvas canvas;
    private Path mPath = new Path();
    private PointF mPointA, mPointB, mPointC, mPointD, mPointE;//外层五边形坐标
    private PointF mPoint1, mPoint2, mPoint3, mPoint4, mPoint5;//内层五边形坐标
    private float pentagonWidth = 400f; //五边形的宽
    private float width = (float) (Math.cos(Math.toRadians(36)) * 2 * pentagonWidth);
    private float height = (float) (pentagonWidth / (2 * Math.tan(Math.toRadians(18))));

    public FiveStarView(Context context) {
        super(context);
    }

    public FiveStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FiveStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FiveStarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPointA = new PointF();
        mPointB = new PointF();
        mPointC = new PointF();
        mPointD = new PointF();
        mPointE = new PointF();

        mPoint1 = new PointF();
        mPoint2 = new PointF();
        mPoint3 = new PointF();
        mPoint4 = new PointF();
        mPoint5 = new PointF();

        mPointA.set(width / 2, 0);
        mPointB.set(0, (float) (pentagonWidth * Math.cos(Math.toRadians(54))));
        mPointC.set((width - pentagonWidth) / 2, height);
        mPointD.set((width + pentagonWidth) / 2, height);
        mPointE.set(width, (float) (pentagonWidth * Math.cos(Math.toRadians(54))));

        mPoint1.set((float) (width / 2 - mPointB.y * Math.tan(Math.toRadians(18))), mPointB.y);
        mPoint2.set((float) (mPointC.x + mPoint1.y * Math.cos(Math.toRadians(72))), (float) (height - mPoint1.y * Math.sin(Math.toRadians(72))));
        mPoint3.set(width / 2, (float) (height - pentagonWidth / 2 * Math.tan(Math.toRadians(36))));
        mPoint4.set((float) (mPointC.x + pentagonWidth - mPoint1.y * Math.cos(Math.toRadians(72))), (float) (height - mPoint1.y * Math.sin(Math.toRadians(72))));
        mPoint5.set((float) (width / 2 + mPointB.y * Math.tan(Math.toRadians(18))), mPointB.y);

        mPath.moveTo(mPointA.x, mPointA.y);
        mPath.lineTo(mPoint1.x, mPoint1.y);
        mPath.lineTo(mPointB.x, mPointB.y);
        mPath.lineTo(mPoint2.x, mPoint2.y);
        mPath.lineTo(mPointC.x, mPointC.y);
        mPath.lineTo(mPoint3.x, mPoint3.y);
        mPath.lineTo(mPointD.x, mPointD.y);
        mPath.lineTo(mPoint4.x, mPoint4.y);
        mPath.lineTo(mPointE.x, mPointE.y);
        mPath.lineTo(mPoint5.x, mPoint5.y);
        mPath.close();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
