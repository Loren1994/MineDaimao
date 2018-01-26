package com.example.loren.minesample

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.balysv.materialmenu.MaterialMenuDrawable
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.fragment.HomeFragment
import com.example.loren.minesample.fragment.UserFragment
import com.example.loren.minesample.fragment.UtilsFragment
import kotlinx.android.synthetic.main.launcher_activity.*
import pers.victor.ext.date
import pers.victor.ext.findColor
import pers.victor.ext.spSetString


/**
 * Copyright (c) 16-9-19 by loren
 * ._       _____    ____    _____   __   _
 * | |     /  _  \ |  _  \  | ____| |  \ | |
 * | |     | | | | | |_| |  | |__   |   \| |
 * | |     | | | | |  _  /  |  __|  | |\   |
 * | |___  | |_| | | | \ \  | |___  | | \  |
 * |_____| \_____/ |_|  \_\ |_____| |_|  \_|
 *
 * 记录:
 * 目前还没加的android适配问题
 * 1.悬浮窗:Build.VERSION.SDK_INT <= Build.VERSION_CODES.N ?
 *   WindowManager.LayoutParams.TYPE_TOAST: WindowManager.LayoutParams.TYPE_PHONE
 * 2.创建PDF动态申请权限
 * 不够kotlin的地方
 * 1.In/OutputStream的各种操作
 */

class LauncherActivity : BaseActivity() {

    override fun useTitleBar() = false

    override fun initWidgets() {
        setDoubleBackFinish()
        tool_bar!!.title = titleArr[0]
        setSupportActionBar(tool_bar)
        tool_bar!!.setBackgroundColor(findColor(R.color.gray_dark))
        tool_bar!!.setTitleTextColor(Color.WHITE)
        materialDrawable = MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN)
        tool_bar!!.navigationIcon = materialDrawable
        tool_bar!!.setNavigationOnClickListener {
            if (draw_layout!!.isDrawerOpen(Gravity.START)) {
                draw_layout!!.closeDrawer(Gravity.START)
            } else {
                draw_layout!!.openDrawer(Gravity.START)
            }
        }
        draw_layout!!.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                materialDrawable.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        if (isDrawerOpened) 2 - slideOffset else slideOffset
                )
            }

            override fun onDrawerOpened(drawerView: View) {
                isDrawerOpened = true
            }

            override fun onDrawerClosed(drawerView: View) {
                isDrawerOpened = false
            }

            override fun onDrawerStateChanged(newState: Int) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) {
                        materialDrawable.iconState = MaterialMenuDrawable.IconState.ARROW
                    } else {
                        materialDrawable.iconState = MaterialMenuDrawable.IconState.BURGER
                    }
                }
            }
        })
        bottom_navigation!!.setOnTabSelectedListener(AHBottomNavigation.OnTabSelectedListener { position, _ ->
            if (draw_layout!!.isDrawerVisible(Gravity.START)) {
                draw_layout!!.closeDrawer(Gravity.START)
                return@OnTabSelectedListener false
            }
            ah_vp!!.currentItem = position
            tool_bar!!.title = titleArr[position]
            true
        })
        for (i in 0..9) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_menu, null)
            (view.findViewById<View>(R.id.item_tv) as TextView).text = "item " + (i + 1)
            menu_container_ll!!.addView(view)
        }
        head_ll!!.setOnClickListener {
            Snackbar.make(window.decorView, "yo yo yo", 3000).show()
            draw_layout!!.closeDrawer(Gravity.START)
        }
        initBottomNavigation()
        initViewpager()
    }

    override fun onResume() {
        spSetString("open_time", System.currentTimeMillis().date()!!)
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        sendBroadcast(intent)
        super.onResume()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.launcher_activity

    var titleArr = arrayOf("首页", "工具", "个人")
    private lateinit var mAdapter: VpAdapter
    var frag = arrayOf(UserFragment(), UtilsFragment(), HomeFragment())
    lateinit var materialDrawable: MaterialMenuDrawable
    var isDrawerOpened = false

    private fun initBottomNavigation() {
        val item1 = AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home, R.color.purple_dark)
        val item2 = AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_util, R.color.purple_dark)
        val item3 = AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_person, R.color.purple_dark)
        bottom_navigation.addItem(item1)
        bottom_navigation.addItem(item2)
        bottom_navigation.addItem(item3)
        bottom_navigation.defaultBackgroundColor = findColor(R.color.gray_dark)
        bottom_navigation.inactiveColor = findColor(R.color.match)
        bottom_navigation.accentColor = findColor(R.color.text_white)
        bottom_navigation.isForceTint = true
        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottom_navigation.isBehaviorTranslationEnabled = true
        bottom_navigation.isTranslucentNavigationEnabled = true
//        bottom_navigation.isColored = true
    }

    private fun initViewpager() {
        mAdapter = VpAdapter(supportFragmentManager)
        ah_vp!!.adapter = mAdapter
        ah_vp!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottom_navigation!!.currentItem = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    internal inner class VpAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment = frag[position]
            val bundle = Bundle()
            bundle.putString("title", titleArr[position])
            fragment.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            return titleArr.size
        }
    }
}
