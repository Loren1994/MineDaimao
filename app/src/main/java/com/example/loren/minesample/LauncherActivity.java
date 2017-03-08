package com.example.loren.minesample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.loren.minesample.fragment.HomeFragment;
import com.example.loren.minesample.fragment.UserFragment;
import com.example.loren.minesample.fragment.UtilsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 16-9-19 by loren
 * ._       _____    ____    _____   __   _
 * | |     /  _  \ |  _  \  | ____| |  \ | |
 * | |     | | | | | |_| |  | |__   |   \| |
 * | |     | | | | |  _  /  |  __|  | |\   |
 * | |___  | |_| | | | \ \  | |___  | | \  |
 * |_____| \_____/ |_|  \_\ |_____| |_|  \_|
 */

public class LauncherActivity extends AppCompatActivity {

    @BindView(R.id.ah_vp)
    ViewPager ahVp;
    @BindView(R.id.menu_container_ll)
    LinearLayout menuLl;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation navigation;
    VpAdapter mAdapter;
    String[] titleArr = {"首页", "工具", "个人"};
    Fragment[] frag = {new HomeFragment(), new UtilsFragment(), new UserFragment()};
    Context mContext;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    MaterialMenuDrawable materialDrawable;
    boolean isDrawerOpened = false;
    @BindView(R.id.head_ll)
    LinearLayout headLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        ButterKnife.bind(this);
        mContext = this;
        toolBar.setTitle(titleArr[0]);
        setSupportActionBar(toolBar);
        toolBar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_dark));
        toolBar.setTitleTextColor(Color.WHITE);
        materialDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolBar.setNavigationIcon(materialDrawable);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawLayout.isDrawerOpen(Gravity.START)) {
                    drawLayout.closeDrawer(Gravity.START);
                } else {
                    drawLayout.openDrawer(Gravity.START);
                }
            }
        });
        drawLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialDrawable.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) {
                        materialDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        materialDrawable.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }
        });
        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (drawLayout.isDrawerVisible(Gravity.START)) {
                    drawLayout.closeDrawer(Gravity.START);
                    return false;
                }
                ahVp.setCurrentItem(position);
                toolBar.setTitle(titleArr[position]);
                return true;
            }
        });
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
            ((TextView) view.findViewById(R.id.item_tv)).setText("item " + (i + 1));
            menuLl.addView(view);
        }
        headLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getWindow().getDecorView(), "this is 胜利哥.png", 3000).show();
                drawLayout.closeDrawer(Gravity.START);
            }
        });
        initBottomNavigation();
        initViewpager();
        startWindowService();
    }

    private void startWindowService() {
        startService(new Intent(this, WindowsService.class));
    }

    private void initBottomNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home, R.color.purple_dark);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_util, R.color.purple_dark);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_person, R.color.purple_dark);
        navigation.addItem(item1);
        navigation.addItem(item2);
        navigation.addItem(item3);
        navigation.setDefaultBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_dark));
        navigation.setInactiveColor(ContextCompat.getColor(mContext, R.color.match));
        navigation.setAccentColor(ContextCompat.getColor(mContext, R.color.text_white));
        navigation.setForceTint(true);
        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        navigation.setBehaviorTranslationEnabled(true);
    }

    private void initViewpager() {
        mAdapter = new VpAdapter(getSupportFragmentManager());
        ahVp.setAdapter(mAdapter);
        ahVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = frag[position];
            Bundle bundle = new Bundle();
            bundle.putString("title", titleArr[position]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titleArr.length;
        }
    }
}
