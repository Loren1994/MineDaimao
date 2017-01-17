package com.example.loren.minesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        tv1.setTextSize(30);
        tv2.setTextSize(30);
        tv3.setTextSize(30);
        tv4.setTextSize(30);
        tv1.setText("开门呐！");
        tv2.setText("快开门呐！！");
        tv3.setText("你有本事抢男人！！！");
        tv4.setText("你有本事开门呐！！！！");
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        viewFlipper.addView(tv1);
        viewFlipper.addView(tv2);
        viewFlipper.addView(tv3);
        viewFlipper.addView(tv4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
    }
}
