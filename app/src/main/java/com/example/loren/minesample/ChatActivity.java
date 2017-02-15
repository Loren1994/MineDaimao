package com.example.loren.minesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loren.minesample.widget.AdjustLinearLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import bean.MessageBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import util.Constant;

public class ChatActivity extends AppCompatActivity implements AdjustLinearLayout.onSizeChangeListener {

    @BindView(R.id.chat_rv)
    RecyclerView chatRv;
    @BindView(R.id.input_edt)
    EditText inputEdt;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.adjust_ll)
    AdjustLinearLayout adjustLl;
    private Disposable disposable;
    private ArrayList<MessageBean.DataBean> data = null;
    private ChatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        setAnim();
        Observable.interval(10, TimeUnit.SECONDS, Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        MessageBean.DataBean bean = new MessageBean.DataBean();
                        bean.setId("");
                        bean.setName("");
                        bean.setMsg("嗯！");
                        bean.setType("1");
                        bean.setUrl("http://tupian.enterdesk.com/2014/lxy/2014/12/01/5/1.jpg");
                        //refresh(bean);
                        Observable.just(bean)
                                .subscribe(new Consumer<MessageBean.DataBean>() {
                                    @Override
                                    public void accept(MessageBean.DataBean bean) throws Exception {
                                        refresh(bean);
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        data = new Gson().fromJson(Constant.JSON, MessageBean.class).getData();
        titleTv.setText(data.get(1).getName());
        mAdapter = new ChatAdapter(this, data);

        chatRv.setLayoutManager(new GridLayoutManager(this, 1));
        chatRv.setAdapter(mAdapter);
        chatRv.getAdapter().notifyDataSetChanged();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEdt.getText().toString().trim().equals(""))
                    return;
                MessageBean.DataBean bean = new MessageBean.DataBean();
                bean.setId("");
                bean.setName("");
                bean.setMsg(inputEdt.getText().toString());
                bean.setType("0");
                bean.setUrl("http://img1.imgtn.bdimg.com/it/u=648722154,281408629&fm=11&gp=0.jpg");
                refresh(bean);
                inputEdt.setText("");
            }
        });
        adjustLl.setListener(this);
    }

    private void setAnim() {
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        getWindow().setExitTransition(new Explode().setDuration(1000));
    }

    private void refresh(MessageBean.DataBean bean) {
        int temp = data.size();
        data.add(bean);
        chatRv.getAdapter().notifyItemRangeInserted(temp, data.size());
        chatRv.scrollBy(0, Integer.MAX_VALUE);
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onSizeChange(boolean isShowInputMethod) {
        if (isShowInputMethod)
            chatRv.post(new Runnable() {
                @Override
                public void run() {
                    chatRv.scrollBy(0, Integer.MAX_VALUE);
                }
            });

    }
}
