package com.example.loren.minesample.jniutil;

/**
 * Copyright © 2018/11/7 by loren
 */
public class JniUtil {
    static {
        System.loadLibrary("jniutil");
    }

    public native String test();
}
