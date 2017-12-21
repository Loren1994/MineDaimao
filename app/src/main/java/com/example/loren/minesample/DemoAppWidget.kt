package com.example.loren.minesample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import pers.victor.ext.spGetString
import pers.victor.ext.toast


/**
 * Implementation of App Widget functionality.
 */
class DemoAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        toast("拜拜了您嘞~")
        super.onDeleted(context, appWidgetIds)
    }

    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }


    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = setOpenTime(context)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    /**
     * 只有在intent中含有extras且key为AppWidgetManager.EXTRA_APPWIDGET_IDS的值不为空时调用
     * 当我们的广播不含有这个extras的时候，onReceive会调用，onUpdate不会
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val remoteView = setOpenTime(context!!)
        val manager = AppWidgetManager.getInstance(context)
        val component = ComponentName(context, DemoAppWidget::class.java)
        manager.updateAppWidget(manager.getAppWidgetIds(component), remoteView)
    }

    private fun setOpenTime(context: Context): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.demo_app_widget)
        val time = spGetString("open_time", "未打开")
        views.setTextViewText(R.id.appwidget_text, "上次打开: $time")
        val intentClick = Intent(context, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intentClick, 0)
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
        return views
    }
}

