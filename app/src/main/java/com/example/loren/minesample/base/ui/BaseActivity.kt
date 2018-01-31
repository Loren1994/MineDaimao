package com.example.loren.minesample.base.ui

import android.app.LauncherActivity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import com.example.loren.minesample.App
import com.example.loren.minesample.R
import com.example.loren.minesample.base.view.AutoRecyclerView
import com.example.loren.minesample.base.view.LoadingDialog
import com.example.loren.minesample.base.view.TitleBar
import io.objectbox.BoxStore
import org.greenrobot.eventbus.EventBus
import pers.victor.ext.ActivityMgr
import pers.victor.ext.findColor
import pers.victor.ext.inputMethodManager
import pers.victor.ext.toast
import pub.devrel.easypermissions.EasyPermissions


/**
 * Author : victor
 * Time : 16-9-11 20:38
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private lateinit var layoutMain: LinearLayout
    private lateinit var loadingDialog: LoadingDialog
    lateinit var boxStore: BoxStore
    private var titleBar: TitleBar? = null
    private var exitTime = 0L
    private var lastClickTime = 0L
    private var doubleBackFinish = false
    private var fakeFinish = false
    private var registerEventBus = false
    private var loadDataAtOnce = true
    private var onPermissionsDenied: ((String) -> Unit)? = null
    private var onPermissionsGranted: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
        boxStore = (application as App).boxStore
        if (savedInstanceState != null) {
            finishAffinity()
            ActivityMgr.removeAll()
            startActivity<LauncherActivity>()
            finish()
//            val intent = Intent(this, LauncherActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
            return
        }
        if (useTitleBar()) {
            layoutMain = LinearLayout(this)
            layoutMain.orientation = LinearLayout.VERTICAL
            titleBar = TitleBar(this)
            if (useImmersive()) {
                titleBar?.useTitleImmersive()
            }
            titleBar?.useElevation()
            layoutMain.addView(titleBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            titleBar?.setLeftDrawable(R.drawable.ic_back)
            titleBar?.setTitleBarLeftClick { finish() }
            titleBar?.setTitleBarText(intent.getStringExtra("title")
                    ?: getString(R.string.app_name))
        }
//        if (allowFullScreen()) {
//            requestWindowFeature(Window.FEATURE_NO_TITLE)
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        }
        setContentView(bindLayout())
        if (useImmersive()) {
            window.navigationBarColor = findColor(R.color.gray_dark)
            window.statusBarColor = findColor(R.color.transparent)
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = option
        }
        if (allowFullScreen()) {
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            window.decorView.systemUiVisibility = option
            window.navigationBarColor = findColor(R.color.transparent)
        }
        window.setBackgroundDrawable(ColorDrawable(findColor(R.color.background)))
        ActivityMgr.add(this)
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

    override fun setContentView(layoutResID: Int) {
        if (useTitleBar()) {
            val view = layoutInflater.inflate(layoutResID, layoutMain, false)
            layoutMain.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            super.setContentView(layoutMain)
        } else {
            super.setContentView(layoutResID)
        }
    }

    open fun isGoneBar() = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isGoneBar() && hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun setContentView(view: View) {
        if (useTitleBar()) {
            layoutMain.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            super.setContentView(layoutMain)
        } else {
            super.setContentView(view)
        }
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        if (useTitleBar()) {
            layoutMain.addView(view, params)
            super.setContentView(layoutMain)
        } else {
            super.setContentView(view, params)
        }
    }

    protected fun setTitleBarBackground(id: Int) = titleBar?.setBackgroundColor(id)

    protected fun setTitleBarText(title: String?) = titleBar?.setTitleBarText(title)

    protected fun setTitleBarText(id: Int) = titleBar?.setTitleBarText(resources.getString(id))

    protected fun setTitleBarColor(color: Int) = titleBar?.setTitleBarColor(findColor(color))

    protected fun setTitleBarLeft(drawable: Int) = titleBar?.setLeftDrawable(drawable)

    protected fun setTitleBarRight(drawable: Int) = titleBar?.setRightDrawable(drawable)

    protected fun setTitleBarLeft(leftText: String) = titleBar?.setLeftText(leftText)

    protected fun setTitleBarRight(rightText: String) = titleBar?.setRightText(rightText)

    protected fun setTitleBarRight(rightText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setRightTextAndIcon(rightText, iconId, iconAtLeft)

    protected fun setTitleBarLeft(leftText: String, iconId: Int, iconAtLeft: Boolean = true) = titleBar?.setLeftTextAndIcon(leftText, iconId, iconAtLeft)

    protected fun setTitleBarLeftVisibility(visibility: Int) = titleBar?.setLeftVisibility(visibility)

    protected fun setTitleBarRightVisibility(visibility: Int) = titleBar?.setRightVisibility(visibility)

    protected fun setTitleBarVisibility(visibility: Int) = { titleBar?.visibility = visibility }()

    protected fun setTitleBarRightClick(func: () -> Unit) = titleBar?.setTitleBarRightClick { func() }

    protected fun setTitleBarLeftClick(func: () -> Unit) = titleBar?.setTitleBarLeftClick { func() }

    protected fun setTitleBarTitleClick(click: () -> Unit) = titleBar?.setTitleBarTitleClick { click() }

    protected fun registerEventBus() = { this.registerEventBus = true }()

    override fun onPause() {
        super.onPause()
        if (loadingDialog.isShowing) {
            dismissLoadingDialog()
        }
    }

    override fun onDestroy() {
        if (registerEventBus) {
            EventBus.getDefault().unregister(this)
        }
        ActivityMgr.remove(this)
        super.onDestroy()
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

    protected fun disallowLoadDataAtOnce() = { loadDataAtOnce = false }()

    protected fun click(vararg views: View) = views.forEach { it.setOnClickListener(this) }

    protected fun requestPermission(vararg permission: String, granted: ((String) -> Unit)? = null, denied: ((String) -> Unit)? = null, rationale: String? = null) {
        this.onPermissionsGranted = granted
        this.onPermissionsDenied = denied
        val list = arrayListOf<String>()
        permission.forEach {
            if (EasyPermissions.hasPermissions(this, it)) {
                this.onPermissionsGranted?.invoke(it)
            } else {
                list.add(it)
            }
        }
        EasyPermissions.requestPermissions(this, rationale ?: getString(R.string.app_name)
        +"需要申请权限", 110, *list.toTypedArray())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, permissions: MutableList<String>?) {
        permissions?.forEach { this.onPermissionsDenied?.invoke(it) }
        this.onPermissionsDenied = null
    }

    override fun onPermissionsGranted(requestCode: Int, permissions: MutableList<String>?) {
        permissions?.forEach { this.onPermissionsGranted?.invoke(it) }
        this.onPermissionsGranted = null
    }

    protected open fun allowFullScreen() = false

    protected open fun useImmersive() = true

    fun showLoadingDialog(msg: String = "加载中…", cancelable: Boolean = true) {
        if (loadingDialog.isShowing) {
            loadingDialog.setText(msg)
            return
        }
        loadingDialog.setCancelable(cancelable)
        loadingDialog.setCanceledOnTouchOutside(cancelable)
        loadingDialog.show(msg)
    }

    fun showLoadingDialog(cancelable: Boolean) = showLoadingDialog("加载中…", cancelable)

    fun dismissLoadingDialog() = loadingDialog.dismiss()

    protected fun setDoubleBackFinish() = { this.doubleBackFinish = true }()

    protected fun setFakeFinish() = { this.fakeFinish = true }()

    override fun onBackPressed() {
        if (!doubleBackFinish) {
            if (fakeFinish) moveTaskToBack(true) else super.onBackPressed()
            return
        }
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出")
            exitTime = System.currentTimeMillis()
        } else {
            if (fakeFinish) moveTaskToBack(true) else super.onBackPressed()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun handleDataList(recyclerView: AutoRecyclerView?, oldList: MutableList<out Any>, newList: List<Any>, isRefresh: Boolean, hasNextPage: Boolean, loadDataListener: (() -> Unit)) {
        recyclerView?.let {
            oldList as MutableList<Any>
            if (isRefresh) {
                recyclerView.scrollToPosition(0)
                oldList.clear()
                oldList.addAll(newList)
                recyclerView.adapter.notifyDataSetChanged()
                recyclerView.setOnLoadingListener { loadDataListener() }
            } else {
                val size = oldList.size
                oldList.addAll(newList)
                recyclerView.adapter.notifyItemRangeInserted(size, newList.size)
            }
            recyclerView.hasNextPage(hasNextPage)
        }
    }

    fun hideInputMethod() {
        window.peekDecorView()?.let {
            inputMethodManager.hideSoftInputFromWindow(window.peekDecorView().windowToken, 0)
        }
    }

    fun showInputMethod(v: EditText) = inputMethodManager.showSoftInput(v, 0)

    protected fun clearWindowBackground() = window.setBackgroundDrawable(null)

    protected fun steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    inline fun <reified T> startActivity() = startActivity(Intent(this, T::class.java))

    inline fun <reified T> startActivityForResult(requestCode: Int) = startActivityForResult(Intent(this, T::class.java), requestCode)

    inline fun <reified T> startService(): ComponentName? = startService(Intent(this, T::class.java))

    inline fun <reified T> bindService(sc: ServiceConnection, flags: Int = BIND_AUTO_CREATE) = bindService(Intent(this, T::class.java), sc, flags)
}
