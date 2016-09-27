package com.example.loren.minesample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Copyright (c) 16-9-19 by loren
 */

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        mContext = this;
        LinearLayout container = ((LinearLayout) findViewById(R.id.container));
        for (int i = 0; i < container.getChildCount(); i++) {
            if (i % 2 == 0) {
                container.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.orange));
            } else {
                container.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
            }
        }
        findViewById(R.id.fs_tv).setOnClickListener(this);
        findViewById(R.id.flag_tv).setOnClickListener(this);
        findViewById(R.id.move_view).setOnClickListener(this);
        findViewById(R.id.custom_recycler).setOnClickListener(this);
//        findViewById(R.id.remind_tv).setOnClickListener(this);
        findViewById(R.id.person_detail_tv).setOnClickListener(this);
        findViewById(R.id.yingshi_tv).setOnClickListener(this);
        findViewById(R.id.wx_vedio).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_view:
                startActivity(new Intent(mContext, MoveViewActivity.class));
                break;
            case R.id.flag_tv:
                startActivity(new Intent(mContext, FlagActivity.class));
                break;
            case R.id.fs_tv:
                startActivity(new Intent(mContext, CustomViewActivity.class));
                break;
            case R.id.custom_recycler:
                startActivity(new Intent(mContext, ZzBeeLayoutActivity.class));
                break;
//            case R.id.remind_tv:
//                startActivity(new Intent(mContext, RemindActivity.class));
//                break;
            case R.id.person_detail_tv:
                startActivity(new Intent(mContext, ApplicationJumpActivity.class));
                break;
            case R.id.yingshi_tv:
                startActivity(new Intent(mContext, VideoActivity.class));
                break;
            case R.id.wx_vedio:
                startActivity(new Intent(mContext, CopyWxPullActivity.class));
                break;

            default:
                break;
        }
    }
}
