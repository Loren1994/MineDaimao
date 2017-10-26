package com.example.loren.minesample

import android.support.v7.widget.GridLayoutManager
import android.transition.Explode
import android.view.View
import bean.MessageBean
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.widget.AdjustLinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import util.Constant
import java.util.*

class ChatActivity : BaseActivity(), AdjustLinearLayout.onSizeChangeListener {
    override fun initWidgets() {
        setAnim()
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
    }

    override fun setListeners() {
        click(adjust_ll)
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_chat

    //    private Disposable disposable;
    private var data: ArrayList<MessageBean.DataBean>? = null
    private var mAdapter: ChatAdapter? = null


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
