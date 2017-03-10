package com.example.loren.minesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import bean.TranslationBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import util.HttpUtil;

public class TranslationActivity extends AppCompatActivity {

    @BindView(R.id.input_edt)
    EditText inputEdt;
    @BindView(R.id.output_edt)
    EditText outputEdt;
    @BindView(R.id.translation_iv)
    ImageView translationIv;
    @BindView(R.id.container_ll)
    LinearLayout containerLl;
    private String TRANSLATION_URL = "http://fanyi.youdao.com/openapi.do?keyfrom=Skykai521&key=977124034&type=data&doctype=json&version=1.1&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        ButterKnife.bind(this);
        translationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTranslation();
            }
        });
        containerLl.getChildAt(0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                translate();
            }
        });
    }

    private void translate() {

    }


    private void toTranslation() {
        HttpUtil.get(TRANSLATION_URL + inputEdt.getText().toString().trim(), new HttpUtil.NetCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    TranslationBean bean = new Gson().fromJson(json, TranslationBean.class);
                    String result = "";
                    for (int i = 0; i < bean.getBasic().getExplains().size(); i++) {
                        result += bean.getBasic().getExplains().get(i) + "  ";
                    }
                    outputEdt.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(TranslationActivity.this, "异常了哈哈哈哈哈", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(TranslationActivity.this, reason, Toast.LENGTH_LONG).show();
            }
        });
    }
}
