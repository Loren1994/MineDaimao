package com.example.loren.minesample.base.ui

import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.loren.minesample.R
import com.example.loren.minesample.base.view.AutoRecyclerView
import com.example.loren.minesample.base.view.TitleBar
import org.greenrobot.eventbus.EventBus
import pers.victor.ext.findColor
import pub.devrel.easypermissions.EasyPermissions

/**
 * Author : victor
 * Time : 16-9-11 20:42
 */
abstract class BaseFragment : Fragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks {
    protected lateinit var mContext: BaseActivity
    private var titleBar: TitleBar? = null
    private var lastClickTime = 0L
    private var registerEventBus = false
    private var loadDataAtOnce = true
    private var onPermissionsDenied: (() -> Unit)? = null
    private var onPermissionsGranted: (() -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseActivity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (useTitleBar()) {
            val layoutMain = LinearLayout(mContext)
            layoutMain.orientation = LinearLayout.VERTICAL
            titleBar = TitleBar(mContext)
            titleBar?.useElevation()
            layoutMain.addView(titleBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val layoutContent = inflater.inflate(bindLayout(), layoutMain, false)
            layoutMain.addView(layoutContent, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            layoutMain
        } else {
            inflater.inflate(bindLayout(), container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        initWidgets()
        setListeners()
        if (registerEventBus) {
            EventBus.getDefault().register(this)
        }
        if (loadDataAtOnce) {
            loadData()
            loadData(true)
        }
    }

    open fun useTitleBar() = true

    protected fun setTitleBarVisibility(visibility: Int) = { titleBar?.visibility = visibility }()

    protected fun setTitleBarText(title: String) = titleBar?.setTitleBarText(title)

    protected fun setTitleBarText(id: Int) = titleBar?.setTitleBarText(resources.getString(id))

    protected fun setTitleBarColor(id: Int) = titleBar?.setTitleBarColor(findColor(id))

    protected fun setTitleBarLeft(drawable: Int) = titleBar?.setLeftDrawable(drawable)

    protected fun setTitleBarRight(drawable: Int) = titleBar?.setRightDrawable(drawable)

    protected fun setTitleBarLeft(leftText: String) = titleBar?.setLeftText(leftText)

    protected fun setTitleBarRight(rightText: String) = titleBar?.setRightText(rightText)

    protected fun setTitleBarRight(rightText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setRightTextAndIcon(rightText, iconId, iconAtLeft)

    protected fun setTitleBarLeft(leftText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setLeftTextAndIcon(leftText, iconId, iconAtLeft)

    protected fun setTitleBarLeftVisibility(visibility: Int) {
        titleBar?.setLeftVisibility(visibility)
        if (visibility == View.INVISIBLE) {
            titleBar?.setTitleBarLeftClick(null)
        }
    }

    protected fun setTitleBarRightVisibility(visibility: Int) {
        titleBar?.setRightVisibility(visibility)
        if (visibility == View.INVISIBLE) {
            titleBar?.setTitleBarRightClick(null)
        }
    }

    protected fun setTitleBarLeftClick(click: () -> Unit) = titleBar?.setTitleBarLeftClick { click() }

    protected fun setTitleBarRightClick(click: () -> Unit) = titleBar?.setTitleBarRightClick { click() }

    protected fun setTitleBarTitleClick(click: () -> Unit) = titleBar?.setTitleBarTitleClick { click() }

    protected fun registerEventBus() = { this.registerEventBus = true }()

    override fun onDestroyView() {
        if (registerEventBus) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastClickTime > 300) {
            lastClickTime = System.currentTimeMillis()
            onWidgetsClick(v)
        }
    }

    abstract fun initWidgets()

    abstract fun setListeners()

    abstract fun onWidgetsClick(v: View)

    abstract fun bindLayout(): Int

    open fun initData() {
    }

    open fun loadData() {
    }

    open fun loadData(isRefresh: Boolean) {
    }

    protected fun click(vararg views: View) = views.forEach { it.setOnClickListener(this) }

    open protected fun disallowLoadDataAtOnce() = { loadDataAtOnce = false }()

    protected fun requestPermission(vararg permission: String, granted: (() -> Unit)? = null, denied: (() -> Unit)? = null, rationale: String? = null) {
        this.onPermissionsGranted = granted
        this.onPermissionsDenied = denied
        val permissionList = arrayListOf<String>()
        permission.forEach {
            if (!EasyPermissions.hasPermissions(mContext, it)) {
                permissionList.add(it)
            }
        }
        if (permissionList.size == 0) {
            this.onPermissionsGranted?.invoke()
        }
        EasyPermissions.requestPermissions(activity!!, rationale
                ?: "${getString(R.string.app_name)}需要申请权限", 110, *permissionList.toTypedArray())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, permissions: MutableList<String>) {
        this.onPermissionsDenied?.invoke()
        this.onPermissionsDenied = null
    }

    override fun onPermissionsGranted(requestCode: Int, permissions: MutableList<String>) {
        this.onPermissionsGranted?.invoke()
        this.onPermissionsGranted = null
    }

    protected fun showLoadingDialog(msg: String = "加载中…", cancelable: Boolean = true) = mContext.showLoadingDialog(msg, cancelable)

    protected fun showLoadingDialog(cancelable: Boolean) = mContext.showLoadingDialog(cancelable)

    protected fun dismissLoadingDialog() = mContext.dismissLoadingDialog()

    protected fun finish() = mContext.finish()

    protected fun handleDataList(recyclerView: AutoRecyclerView?, oldList: MutableList<out Any>, newList: List<Any>, isRefresh: Boolean, hasNextPage: Boolean, loadDataListener: (() -> Unit)) = mContext.handleDataList(recyclerView, oldList, newList, isRefresh, hasNextPage, loadDataListener)

    protected fun hideInputMethod() = mContext.hideInputMethod()

    protected fun showInputMethod(v: EditText) = mContext.showInputMethod(v)

    protected inline fun <reified T> startActivity() = mContext.startActivity<T>()

    protected inline fun <reified T> startActivityForResult(requestCode: Int) = mContext.startActivityForResult<T>(requestCode)

    protected inline fun <reified T> startService() = mContext.startService<T>()

    protected inline fun <reified T> bindService(sc: ServiceConnection, flags: Int = Context.BIND_AUTO_CREATE) = mContext.bindService<T>(sc, flags)
}
