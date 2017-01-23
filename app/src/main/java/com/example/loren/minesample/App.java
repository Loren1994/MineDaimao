package com.example.loren.minesample;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
    public static JCVideoPlayerStandard videoPlayer;
    public static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        videoPlayer = new JCVideoPlayerStandard(this);
        mContext = this;
    }
}
