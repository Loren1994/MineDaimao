package com.example.loren.minesample

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.format.Formatter
import android.view.View
import android.widget.TextView
import com.example.loren.minesample.adapter.AppListAdapter
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.AppBean
import kotlinx.android.synthetic.main.app_manager_activity.*
import pers.victor.ext.dp2px
import pers.victor.ext.no
import pers.victor.ext.toast
import pers.victor.ext.yes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/**
 * Copyright © 15/12/2017 by loren
 */
class AppManagerActivity : BaseActivity() {

    private var isRunning = false
    private lateinit var allAdapter: AppListAdapter
    private var data: MutableList<AppBean> = arrayListOf()
    private var runList: List<ActivityManager.RunningAppProcessInfo> = arrayListOf()
    private lateinit var am: ActivityManager
    private lateinit var dialog: AlertDialog.Builder

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!hasPermission()) {
                startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (!hasPermission()) {
                startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1)
            }
        }
    }

    private fun hasPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        var mode = 0
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun initWidgets() {
        setTitleBarText("手机信息")
        setTitleBarRight("全部的")
        setTitleBarRightClick {
            isRunning = !isRunning
            setTitleBarRight(isRunning.yes { "运行的" }.no { "全部的" })
            if (isRunning) {
                filterRunningProcess()
            } else {
                setAllAppList()
            }
        }
        dialog = AlertDialog.Builder(this)
        am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        initDeviceInfo()
        allAdapter = AppListAdapter(this, data, {
            dialog.setTitle("提取Apk文件")
                    .setMessage("待提取:${data[it].sourceDir}")
                    .setPositiveButton("嗯!") { _, _ ->
                        val arr = data[it].packageName.split(".")
                        saveApk(data[it].sourceDir, arr[arr.size - 1])
                    }
                    .setNegativeButton("啥?") { _, _ -> }
                    .show()
        })
        app_rv.adapter = allAdapter
        setAllAppList()
    }

    private fun saveApk(path: String, name: String) {
        try {
            val outPath = "${Environment.getExternalStorageDirectory()}/getApk/"
            val newFile = File("$outPath/$name.apk")
            if (newFile.exists()) {
                toast("已经提取过同名apk")
                return
            }
            val newPath = File(outPath)
            if (!newPath.exists()) {
                newPath.mkdirs()
            }
            val fileInputStream = FileInputStream(path)
            val fileOutputStream = FileOutputStream("$outPath/$name.apk")
            val bt = ByteArray(8192)
            var c = 0
            fun read(): Int {
                c = fileInputStream.read(bt)
                return c
            }
            while (read() > 0) {
                fileOutputStream.write(bt, 0, c)
            }
            fileInputStream.close()
            fileOutputStream.close()
            log("$outPath/$name.apk提取成功")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun filterRunningProcess() {
        val tempList = arrayListOf<String>()
        runList = am.runningAppProcesses
        log("runningAppProcesses = ${runList.size}")
        runList.forEach { i ->
            i.pkgList.forEach {
                tempList.add(it)
                data.forEach { item ->
                    if (item.packageName.contentEquals(it)) {
                        item.pid = i.pid.toString()
                    }
                }
            }
        }
        val t = data.filter { tempList.contains(it.packageName) }.toMutableList()
        data.clear()
        data.addAll(t)
        allAdapter.notifyDataSetChanged()
    }

    private fun setAllAppList() {
        data.clear()
        val appList = packageManager.getInstalledPackages(0)
        appList.forEach {
            val appBean = AppBean()
            appBean.lastUpdateTime = it.lastUpdateTime
            appBean.activities = it.activities
            appBean.applicationInfo = it.applicationInfo
            appBean.firstInstallTime = it.firstInstallTime
            appBean.packageName = it.packageName
            appBean.pid = ""
            appBean.sourceDir = it.applicationInfo.sourceDir
            data.add(appBean)
        }
        data.sortByDescending { it.lastUpdateTime }
        allAdapter.notifyItemRangeChanged(0, data.size)
    }

    @SuppressLint("SetTextI18n")
    private fun initDeviceInfo() {
        val memoryInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memoryInfo)
        val memSize = memoryInfo.availMem
        val restMemSize = Formatter.formatFileSize(baseContext, memSize)
        val tv1 = TextView(this)
        tv1.text = "剩余的内存:$restMemSize"
        tv1.setPadding(dp2px(5), dp2px(3), dp2px(5), dp2px(3))
        info_container_ll.addView(tv1)
        val fields = Build::class.java.declaredFields
        fields.forEach {
            val tv = TextView(this)
            it.isAccessible = true
            tv.text = "${it.name}:${it.get(null)}"
            tv.setPadding(dp2px(5), dp2px(3), dp2px(5), dp2px(3))
            info_container_ll.addView(tv)
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.app_manager_activity
}