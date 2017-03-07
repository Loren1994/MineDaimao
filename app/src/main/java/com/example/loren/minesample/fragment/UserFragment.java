package com.example.loren.minesample.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loren.minesample.AmazingActivity;
import com.example.loren.minesample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by loren on 2017/3/7.
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.amazing_tv)
    TextView amazingTv;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        amazingTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amazing_tv:
                startActivity(new Intent(mContext, AmazingActivity.class));
                break;
        }
    }
}
