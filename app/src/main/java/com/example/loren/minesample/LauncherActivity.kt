package com.example.loren.minesample

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
import pers.victor.ext.findColor

/**
 * Copyright (c) 16-9-19 by loren
 * ._       _____    ____    _____   __   _
 * | |     /  _  \ |  _  \  | ____| |  \ | |
 * | |     | | | | | |_| |  | |__   |   \| |
 * | |     | | | | |  _  /  |  __|  | |\   |
 * | |___  | |_| | | | \ \  | |___  | | \  |
 * |_____| \_____/ |_|  \_\ |_____| |_|  \_|
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
        bottom_navigation!!.setOnTabSelectedListener(AHBottomNavigation.OnTabSelectedListener { position, wasSelected ->
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
            Snackbar.make(window.decorView, "this is 胜利哥.png", 3000).show()
            draw_layout!!.closeDrawer(Gravity.START)
        }
        initBottomNavigation()
        initViewpager()
        startWindowService()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.launcher_activity

    var titleArr = arrayOf("首页", "工具", "个人")
    private lateinit var mAdapter: VpAdapter
    var frag = arrayOf(HomeFragment(), UtilsFragment(), UserFragment())
    lateinit var materialDrawable: MaterialMenuDrawable
    var isDrawerOpened = false


    private fun startWindowService() {
        startService(Intent(this, WindowsService::class.java))
    }

    private fun initBottomNavigation() {
        val item1 = AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home, R.color.purple_dark)
        val item2 = AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_util, R.color.purple_dark)
        val item3 = AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_person, R.color.purple_dark)
        bottom_navigation!!.addItem(item1)
        bottom_navigation!!.addItem(item2)
        bottom_navigation!!.addItem(item3)
        bottom_navigation!!.defaultBackgroundColor = findColor(R.color.gray_dark)
        bottom_navigation!!.inactiveColor = findColor(R.color.match)
        bottom_navigation!!.accentColor = findColor(R.color.text_white)
        bottom_navigation!!.isForceTint = true
        bottom_navigation!!.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottom_navigation!!.isBehaviorTranslationEnabled = true
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
