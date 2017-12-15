package com.example.loren.minesample.entity;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;

/**
 * Copyright © 15/12/2017 by loren
 */

public class AppBean {
    public ActivityInfo[] activities = null;
    public ApplicationInfo applicationInfo;
    public long firstInstallTime;
    public long lastUpdateTime;
    public String packageName;
    public String pid = "";
    public String sourceDir = "";
}
