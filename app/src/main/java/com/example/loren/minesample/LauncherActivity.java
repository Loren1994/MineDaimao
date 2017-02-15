package com.example.loren.minesample;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Copyright (c) 16-9-19 by loren
 */

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] mList = {"张三 18661881639", "李四 18612341234", "王五 18661881234", "王五 18661881234", "王五 18661881234",
            "王五 18661881234", "王五 18661881234", "王五 18661881234", "王五 18661881234", "王五 18661881234"};
    private Context mContext;
    private TextView shareTv;

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
        shareTv = (TextView) findViewById(R.id.move_view);
        findViewById(R.id.fs_tv).setOnClickListener(this);
        findViewById(R.id.flag_tv).setOnClickListener(this);
        shareTv.setOnClickListener(this);
        findViewById(R.id.custom_recycler).setOnClickListener(this);
        findViewById(R.id.remind_tv).setOnClickListener(this);
        findViewById(R.id.person_detail_tv).setOnClickListener(this);
        findViewById(R.id.yingshi_tv).setOnClickListener(this);
        findViewById(R.id.wx_vedio).setOnClickListener(this);
        findViewById(R.id.loren).setOnClickListener(this);
        findViewById(R.id.chat).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.open).setOnClickListener(this);

//        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
//        recycler.setLayoutManager(new GridLayoutManager(this, 1));
//        ShortcutAdapter mAdapter = new ShortcutAdapter(this, mList);
//        recycler.setAdapter(mAdapter);
//        ShortcutManager shortCutsManager = getSystemService(ShortcutManager.class);
//        ArrayList<ShortcutInfo> shortcutInfo = new ArrayList<>();
//        for (int i = 0; i < shortCutsManager.getMaxShortcutCountPerActivity(); i++) {
//            Intent intent = new Intent(this, LorenActivity.class).
//                    putExtra("message", "联系人:" + mAdapter.getMdata()[i]);
//            intent.setAction(Intent.ACTION_VIEW);
//            ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + i)
//                    .setShortLabel(mAdapter.getMdata()[i])
//                    .setLongLabel("联系人:" + mAdapter.getMdata()[i])
//                    .setIntent(intent)
//                    .build();
//            shortcutInfo.add(i, info);
//        }
//        shortCutsManager.setDynamicShortcuts(shortcutInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_view:
                startActivity(new Intent(mContext, MoveViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, shareTv, "share_tv").toBundle());
                break;
            case R.id.flag_tv:
                startActivity(new Intent(mContext, FlagActivity.class));
                break;
            case R.id.fs_tv:
                startActivity(new Intent(mContext, CustomViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.custom_recycler:
                startActivity(new Intent(mContext, ZzBeeLayoutActivity.class));
                break;
            case R.id.remind_tv:
                startActivity(new Intent(mContext, RemindActivity.class));
                break;
            case R.id.person_detail_tv:
                startActivity(new Intent(mContext, ApplicationJumpActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.yingshi_tv:
                startActivity(new Intent(mContext, VideoActivity.class));
                break;
            case R.id.wx_vedio:
                startActivity(new Intent(mContext, CopyWxPullActivity.class));
                break;
            case R.id.loren:
                startActivity(new Intent(mContext, LorenActivity.class));
                break;
            case R.id.chat:
                startActivity(new Intent(mContext, ChatActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.play:
                startActivity(new Intent(mContext, PlayActivity.class));
                break;
            case R.id.open:
//                startActivity(new Intent(mContext, OpenScreenActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(new Intent(mContext, OpenScreenActivity.class));
                break;

            default:
                break;
        }
    }
}
