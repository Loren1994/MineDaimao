package com.example.loren.minesample

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.format.Formatter
import android.view.View
import android.widget.TextView
import com.example.loren.minesample.adapter.AppListAdapter
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.AppBean
import com.example.loren.minesample.widget.CommonPopupWindow
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
    private lateinit var batteryTv: TextView
    private lateinit var batteryStatusTv: TextView
    private lateinit var voltageTv: TextView
    private lateinit var voltageStatusTv: TextView
    private lateinit var tempTv: TextView
    private lateinit var popupWindow: CommonPopupWindow
    private var clickPos = 0

    override fun initWidgets() {
        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
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
        initPopupWindow()
        dialog = AlertDialog.Builder(this)
        am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        initTv()
        requestPermission(Manifest.permission.READ_SMS, granted = { initDeviceInfo() }, denied = { toast("无权限,请重试") })
        allAdapter = AppListAdapter(this, data, {
            clickPos = it
            popupWindow.show(window.decorView)
        })
        app_rv.adapter = allAdapter
        setAllAppList()
    }

    private fun initPopupWindow() {
        popupWindow = CommonPopupWindow(this, View.OnClickListener {
            when (it.id) {
                R.id.tv_1 -> {
                    dialog.setTitle("提取Apk文件")
                            .setMessage("待提取:${data[clickPos].sourceDir}")
                            .setPositiveButton("嗯!") { _, _ ->
                                val arr = data[clickPos].packageName.split(".")
                                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        granted = { saveApk(data[clickPos].sourceDir, arr[arr.size - 1]) },
                                        denied = { toast("没有权限,请重试") })
                            }
                            .setNegativeButton("啥?") { _, _ -> }
                            .show()
                }
                R.id.tv_2 -> {
                    val packageManager = packageManager
                    val intent = packageManager.getLaunchIntentForPackage(data[clickPos].packageName)
                    startActivity(intent)
                }
                R.id.tv_3 -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package", data[clickPos].packageName, null)
                    startActivity(intent)
                }
                R.id.tv_4 -> {
                    val packageURI = Uri.parse("package:${data[clickPos].packageName}")
                    val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
                    startActivity(uninstallIntent)
                }
            }
            popupWindow.dismiss()
        })
        popupWindow.setBtnText("提取", "打开", "详情", "卸载")
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
            toast("$outPath/$name.apk提取成功")
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

    @SuppressLint("SetTextI18n", "MissingPermission", "HardwareIds")
    private fun initDeviceInfo() {
        val deviceInfoMap = hashMapOf<String, String>()
        val memoryInfo = ActivityManager.MemoryInfo()
        val mTm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val wn = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val macAddress = wn.connectionInfo.macAddress
        val ipAddress = wn.connectionInfo.ipAddress
        val curWifi = wn.connectionInfo.ssid.replace("\"", "")
        am.getMemoryInfo(memoryInfo)
        val memSize = memoryInfo.availMem
        val totalSize = memoryInfo.totalMem
        val restMemSize = Formatter.formatFileSize(baseContext, memSize)
        val talMemSize = Formatter.formatFileSize(baseContext, totalSize)
        mTm.line1Number?.let { deviceInfoMap.put("手机号", mTm.line1Number) }
        deviceInfoMap.put("是否ROOT", isRoot())
        deviceInfoMap.put("总内存", talMemSize)
        deviceInfoMap.put("剩余内存", restMemSize)
        deviceInfoMap.put("MAC地址", macAddress)
        deviceInfoMap.put("当前WIFI", curWifi)
        deviceInfoMap.put("IP地址", intToIp(ipAddress))
        deviceInfoMap.put("手机品牌", android.os.Build.BRAND)
        deviceInfoMap.put("手机型号", android.os.Build.MODEL)
        deviceInfoMap.put("屏幕", "${resources.displayMetrics.widthPixels} x ${resources.displayMetrics.heightPixels}")
        deviceInfoMap.forEach { (k, v) ->
            val tv = TextView(this)
            tv.text = "$k: $v"
            tv.setPadding(dp2px(5), dp2px(3), dp2px(5), dp2px(3))
            tv.setTextColor(Color.WHITE)
            info_container_ll.addView(tv)
        }
    }

    private fun isRoot(): String {
        return (!File("/system/bin/su").exists() && !File("/system/xbin/su").exists())
                .yes { "未获取" }.no { "已获取" }
    }

    private fun initTv() {
        tempTv = TextView(this)
        voltageTv = TextView(this)
        voltageStatusTv = TextView(this)
        batteryTv = TextView(this)
        batteryStatusTv = TextView(this)
        arrayOf(tempTv, voltageTv, voltageStatusTv, batteryTv, batteryStatusTv).forEach {
            it.setPadding(dp2px(5), dp2px(3), dp2px(5), dp2px(3))
            it.setTextColor(Color.WHITE)
            info_container_ll.addView(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTv(batteryN: String, batteryStatus: String, batteryV: String, batteryTemp: String, batteryT: String) {
        batteryTv.text = "电量: $batteryN%"
        batteryStatusTv.text = "电池状态: $batteryStatus"
        voltageTv.text = "电压: ${batteryV}mV"
        voltageStatusTv.text = "电池健康: $batteryTemp"
        tempTv.text = "电池温度: $batteryT℃"
    }

    private fun intToIp(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)
    }

    override fun onDestroy() {
        unregisterReceiver(mBatInfoReceiver)
        super.onDestroy()
    }

    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var batteryStatus = ""
            var batteryTemp = ""
            val action = intent.action
            if (Intent.ACTION_BATTERY_CHANGED == action) {
                val batteryN = intent.getIntExtra("level", 0)
                val batteryV = intent.getIntExtra("voltage", 0)
                val batteryT = intent.getIntExtra("temperature", 0)
                when (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> batteryStatus = "充电中"
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> batteryStatus = "放电中"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> batteryStatus = "未充电"
                    BatteryManager.BATTERY_STATUS_FULL -> batteryStatus = "充满电"
                    BatteryManager.BATTERY_STATUS_UNKNOWN -> batteryStatus = "未知"
                }

                when (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                    BatteryManager.BATTERY_HEALTH_UNKNOWN -> batteryTemp = "未知错误"
                    BatteryManager.BATTERY_HEALTH_GOOD -> batteryTemp = "状态良好"
                    BatteryManager.BATTERY_HEALTH_DEAD -> batteryTemp = "电池没电"
                    BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> batteryTemp = "电池电压过高"
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> batteryTemp = "电池过热"
                }
                setTv(batteryN.toString(), batteryStatus, batteryV.toString(), batteryTemp, (batteryT * 0.1).toString())
            }
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.app_manager_activity
}