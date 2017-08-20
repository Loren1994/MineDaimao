package com.example.loren.minesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.transition.Explode
import android.view.View
import bean.MessageBean
import com.example.loren.minesample.widget.AdjustLinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import util.Constant
import java.util.*

class ChatActivity : AppCompatActivity(), AdjustLinearLayout.onSizeChangeListener {

    //    private Disposable disposable;
    private var data: ArrayList<MessageBean.DataBean>? = null
    private var mAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_chat)
        setAnim()
        //        Observable.interval(10, TimeUnit.SECONDS, Schedulers.newThread())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Observer<Long>() {
        //                    @Override
        //                    public void onSubscribe(Disposable d) {
        //                        disposable = d;
        //                    }
        //
        //                    @Override
        //                    public void onNext(Long aLong) {
        //                        MessageBean.DataBean bean = new MessageBean.DataBean();
        //                        bean.setId("");
        //                        bean.setName("");
        //                        bean.setMsg("嗯！");
        //                        bean.setType("1");
        //                        bean.setUrl("http://tupian.enterdesk.com/2014/lxy/2014/12/01/5/1.jpg");
        //                        //refresh(bean);
        //                        Observable.just(bean)
        //                                .subscribe(new Consumer<MessageBean.DataBean>() {
        //                                    @Override
        //                                    public void accept(MessageBean.DataBean bean) throws Exception {
        //                                        refresh(bean);
        //                                    }
        //                                });
        //                    }
        //
        //                    @Override
        //                    public void onError(Throwable e) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onComplete() {
        //
        //                    }
        //                });

        data = Gson().fromJson(Constant.JSON, MessageBean::class.java).data
        title_tv!!.text = data!![1].name
        mAdapter = ChatAdapter(this, data!!)

        chat_rv!!.layoutManager = GridLayoutManager(this, 1)
        chat_rv!!.adapter = mAdapter
        chat_rv!!.adapter.notifyDataSetChanged()

        send_btn!!.setOnClickListener(View.OnClickListener {
            if (input_edt!!.text.toString().trim { it <= ' ' } == "")
                return@OnClickListener
            val bean = MessageBean.DataBean()
            bean.id = ""
            bean.name = ""
            bean.msg = input_edt!!.text.toString()
            bean.type = "0"
            bean.url = "http://img1.imgtn.bdimg.com/it/u=648722154,281408629&fm=11&gp=0.jpg"
            refresh(bean)
            input_edt!!.setText("")
        })
        adjust_ll!!.setListener(this)
    }

    private fun setAnim() {
        window.enterTransition = Explode().setDuration(1000)
        window.exitTransition = Explode().setDuration(1000)
    }

    private fun refresh(bean: MessageBean.DataBean) {
        val temp = data!!.size
        data!!.add(bean)
        chat_rv!!.adapter.notifyItemRangeInserted(temp, data!!.size)
        chat_rv!!.scrollBy(0, Integer.MAX_VALUE)
    }

    override fun onDestroy() {
        //        if (disposable != null && !disposable.isDisposed()) {
        //            disposable.dispose();
        //        }
        super.onDestroy()
    }

    override fun onSizeChange(isShowInputMethod: Boolean) {
        if (isShowInputMethod)
            chat_rv!!.post { chat_rv!!.scrollBy(0, Integer.MAX_VALUE) }

    }
}
