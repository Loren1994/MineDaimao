package com.example.loren.minesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Copyright (c) 16-9-19 by loren
 */

public class Java2LotlinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.java_kotlin_activity);
        findViewById(R.id.fs_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Java2LotlinActivity.this, CustomViewActivity.class));
            }
        });
    }
}
