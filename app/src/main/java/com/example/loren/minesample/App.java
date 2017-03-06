package com.example.loren.minesample;

import android.app.Application;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;


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
 * Copyright (c) 17-1-23 by loren
 */

public class App extends Application {
    public static Application mContext;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float SCREEN_DENSITY;
    public static float SCALED_DENSITY;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext = this;
        getDisplayMetrics();
    }

    /*获取高度、宽度、密度、缩放比例*/
    private void getDisplayMetrics() {
        DisplayMetrics metric = mContext.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metric.widthPixels;
        SCREEN_HEIGHT = metric.heightPixels;
        SCREEN_DENSITY = metric.density;
        SCALED_DENSITY = metric.scaledDensity;
    }
}
