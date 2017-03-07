package com.example.loren.minesample.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loren.minesample.ChatActivity;
import com.example.loren.minesample.CopyWxPullActivity;
import com.example.loren.minesample.CustomViewActivity;
import com.example.loren.minesample.FlagActivity;
import com.example.loren.minesample.MoveViewActivity;
import com.example.loren.minesample.OpenScreenActivity;
import com.example.loren.minesample.R;
import com.example.loren.minesample.RemindActivity;
import com.example.loren.minesample.VectorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by loren on 2017/3/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.fs_tv)
    TextView fsTv;
    @BindView(R.id.flag_tv)
    TextView flagTv;
    @BindView(R.id.move_view)
    TextView moveView;
    @BindView(R.id.remind_tv)
    TextView remindTv;
    @BindView(R.id.wx_vedio)
    TextView wxVedio;
    @BindView(R.id.chat)
    TextView chat;
    @BindView(R.id.open)
    TextView open;
    @BindView(R.id.vector)
    TextView vector;
    @BindView(R.id.container)
    LinearLayout container;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        for (int i = 0; i < container.getChildCount(); i++) {
            if (i % 2 == 0) {
                container.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.main));
            } else {
                container.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.match));
            }
        }
        moveView.setOnClickListener(this);
        fsTv.setOnClickListener(this);
        flagTv.setOnClickListener(this);
        remindTv.setOnClickListener(this);
        wxVedio.setOnClickListener(this);
        chat.setOnClickListener(this);
        open.setOnClickListener(this);
        vector.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_view:
                startActivity(new Intent(mContext, MoveViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), moveView, "share_tv").toBundle());
                break;
            case R.id.flag_tv:
                startActivity(new Intent(mContext, FlagActivity.class));
                break;
            case R.id.fs_tv:
                startActivity(new Intent(mContext, CustomViewActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.remind_tv:
                startActivity(new Intent(mContext, RemindActivity.class));
                break;
            case R.id.wx_vedio:
                startActivity(new Intent(mContext, CopyWxPullActivity.class));
                break;
            case R.id.chat:
                startActivity(new Intent(mContext, ChatActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.vector:
                startActivity(new Intent(mContext, VectorActivity.class));
                break;
            case R.id.open:
//                startActivity(new Intent(mContext, OpenScreenActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(new Intent(mContext, OpenScreenActivity.class));
                getActivity().overridePendingTransition(0, 0);
                break;

            default:
                break;
        }
    }
}
