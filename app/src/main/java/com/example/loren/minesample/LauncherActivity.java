package com.example.loren.minesample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        findViewById(R.id.fs_tv).setOnClickListener(this);
        findViewById(R.id.flag_tv).setOnClickListener(this);
        findViewById(R.id.move_view).setOnClickListener(this);
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
            default:
                break;
        }
    }
}
