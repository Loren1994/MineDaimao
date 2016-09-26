package com.example.loren.minesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.zz_bee_layout.*

/**
 *                            _ooOoo_
 *                           o8888888o
 *                           88" . "88
 *                           (| -_- |)
 *                            O\ = /O
 *                        ____/`---'\____
 *                      .   ' \\| |// `.
 *                       / \\||| : |||// \
 *                     / _||||| -:- |||||- \
 *                       | | \\\ - /// | |
 *                     | \_| ''\---/'' | |
 *                      \ .-\__ `-` ___/-. /
 *                   ___`. .' /--.--\ `. . __
 *                ."" '< `.___\_<|>_/___.' >'"".
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *                 \ \ `-. \_ __\ /__ _/ .-` / /
 *         ======`-.____`-.___\_____/___.-`____.-'======
 *                            `=---='
 *
 *         .............................................
 *                  佛祖保佑             永无BUG
 *
 *                Copyright (c) 16-9-23 by loren
 */
class ZzBeeLayoutActivity : AppCompatActivity() {

    private var drawArr = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private var urlArr = arrayOf("http://img0.imgtn.bdimg.com/it/u=3273293410,2667810732&fm=11&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3271149477,3744926107&fm=11&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4186654312,3515491732&fm=23&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1192715945,443541801&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2016448457,3556493612&fm=23&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2155062783,607707723&fm=23&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2404996392,2561119365&fm=23&gp=0.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.zz_bee_layout)
//        bee.setImageRes(drawArr)
        bee.setImageUrls(urlArr)
        bee.setOnImageClickListener { svgImageView, i -> Toast.makeText(this, "you click" + i, Toast.LENGTH_SHORT).show() }
    }
}