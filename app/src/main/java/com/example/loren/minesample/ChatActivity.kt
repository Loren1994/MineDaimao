package com.example.loren.minesample

import android.support.v7.widget.GridLayoutManager
import android.transition.Explode
import android.view.View
import bean.MessageBean
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.util.Constant
import com.example.loren.minesample.util.http
import com.example.loren.minesample.widget.AdjustLinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import pers.victor.ext.toast
import java.util.*

class ChatActivity : BaseActivity(), AdjustLinearLayout.onSizeChangeListener {
    //ALI interface 2018.10.30
    private val ANSWER_URL = "http://jisuznwd.market.alicloudapi.com/iqa/query?question=%s"
    //SecretId: AKIDZzCBxVixrX1ILAs9JGxOcaIpAF4eM1oK
    //SecretKey: QbXQ7YjeNq30qSpxrWx85nGUTDm6B2e1
    private val TECENT_URL = "https://wenzhi.api.qcloud.com/v2/index.php?" +
            "    Action=TextSentiment" +
            "    &Nonce=345122" +
            "    &Region=qingdao" +
            "    &SecretId=AKIDZzCBxVixrX1ILAs9JGxOcaIpAF4eM1oK" +
            "    &Timestamp=1408704141" +
            "    &Signature=HgIYOPcx5lN6gz8JsCFBNAWp2oQ" +
            "    &content=哈哈哈"

    private fun getAnswer(question: String) {
        http {
            url = String.format(ANSWER_URL, question)
            success {
                addAnswerBean(it.toString())
            }
            fail { toast(it.toString()) }
        }
    }

    override fun initWidgets() {
        setAnim()
        data = Gson().fromJson(Constant.JSON, MessageBean::class.java).data
        title_tv!!.text = "智能问答"
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
            getAnswer(input_edt!!.text.toString())
            input_edt!!.setText("")
        })
    }

    private fun addAnswerBean(msg: String) {
        val bean = MessageBean.DataBean()
        bean.id = ""
        bean.name = ""
        bean.msg = msg
        bean.type = "1"
        bean.url = "http://awb.img.xmtbang.com/img/uploadnew/201602/25/02512de9a2874184873c56dadc6dbcbd.jpg"
        refresh(bean)
    }

    override fun setListeners() {
        click(adjust_ll)
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_chat

    private var data: ArrayList<MessageBean.DataBean>? = null
    private var mAdapter: ChatAdapter? = null


    private fun setAnim() {
        window.enterTransition = Explode().setDuration(200)
        window.exitTransition = Explode().setDuration(200)
    }

    private fun refresh(bean: MessageBean.DataBean) {
        val temp = data!!.size
        data!!.add(bean)
        chat_rv!!.adapter.notifyItemRangeInserted(temp, data!!.size)
        chat_rv!!.scrollBy(0, Integer.MAX_VALUE)
    }

    override fun onSizeChange(isShowInputMethod: Boolean) {
        if (isShowInputMethod)
            chat_rv!!.post { chat_rv!!.scrollBy(0, Integer.MAX_VALUE) }
    }

    override fun useTitleBar() = false
}
