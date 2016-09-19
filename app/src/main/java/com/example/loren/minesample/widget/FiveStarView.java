package com.example.loren.minesample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.example.loren.minesample.R;

/**
 * Copyright (c) 16-9-18 by loren
 */

public class FiveStarView extends View {
    private PointF mPointA = new PointF();
    private PointF mPointB = new PointF();
    private PointF mPointC = new PointF();
    private PointF mPointD = new PointF();
    private PointF mPointE = new PointF();
    private PointF mPoint1 = new PointF();
    private PointF mPoint2 = new PointF();
    private PointF mPoint3 = new PointF();
    private PointF mPoint4 = new PointF();
    private PointF mPoint5 = new PointF();
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float pentagonWidth = 0; //五边形的宽
    private float width = 0;
    private float height = 0;
    private int color = 0;

    public FiveStarView(Context context) {
        super(context);
    }

    public FiveStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FiveStarView);
        color = typedArray.getColor(R.styleable.FiveStarView_color, Color.RED);
        typedArray.recycle();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public FiveStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FiveStarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        pentagonWidth = MeasureSpec.getSize(widthMeasureSpec) / 2 / cos(36);
        width = cos(36) * 2 * pentagonWidth;
        height = pentagonWidth / (2 * tan(18));
        setMeasuredDimension(widthMeasureSpec, (int) height);

        mPointA.set(width / 2, 0);
        mPointB.set(0, pentagonWidth * cos(54));
        mPointC.set((width - pentagonWidth) / 2, height);
        mPointD.set((width + pentagonWidth) / 2, height);
        mPointE.set(width, pentagonWidth * cos(54));
        mPoint1.set(width / 2 - mPointB.y * tan(18), mPointB.y);
        mPoint2.set(mPointC.x + width * cos(72) / (2 * (1 + sin(18))), height - width * sin(72) / (2 * (1 + sin(18))));
        mPoint3.set(width / 2, height - pentagonWidth * tan(36) / 2);
        mPoint4.set(mPointD.x - width * cos(72) / (2 * (1 + sin(18))), height - width * sin(72) / (2 * (1 + sin(18))));
        mPoint5.set((width / 2 + mPointB.y * tan(18)), mPointB.y);

        mPath.reset();
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

        mPaint.setColor(color);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


    private float cos(int degree) {
        return (float) Math.cos(Math.toRadians(degree));
    }

    private float sin(int degree) {
        return (float) Math.sin(Math.toRadians(degree));
    }

    private float tan(int degree) {
        return (float) Math.tan(Math.toRadians(degree));
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }
}
