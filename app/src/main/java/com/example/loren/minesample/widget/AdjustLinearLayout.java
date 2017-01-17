package com.example.loren.minesample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑             永无BUG
 * <p>
 * Copyright (c) 17-1-17 by loren
 */

public class AdjustLinearLayout extends LinearLayout {

    private onSizeChangeListener listener;

    public AdjustLinearLayout(Context context) {
        super(context);
    }

    public AdjustLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AdjustLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setListener(onSizeChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (listener != null) {
            listener.onSizeChange(oldh > h);
        }
    }

    public interface onSizeChangeListener {
        void onSizeChange(boolean isShowInputMethod);
    }
}

