package com.example.loren.minesample.base.ext

import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus

/**
 * Created by Victor on 2017/6/29. (ง •̀_•́)ง
 */


//fun ImageView.url(url: String, holder: Int = R.drawable.ic_launcher, error: Int = R.drawable.ic_launcher) = Glide.with(this.context)
//        .load(url)
//        .into(this)!!

//inline fun <reified T> parseObject(json: String?) = Gson().fromJson(json, T::class.java)!!
//fun <T> parseObject(json: String?, type: Type): T = Gson().fromJson(json, type)

fun postEvent(event: Any) = EventBus.getDefault().post(event)

fun postStickyEvent(event: Any) = EventBus.getDefault().postSticky(event)

fun log(log: Any?) = Logger.i(log.toString())
fun json(json: Any?) = Logger.json(json.toString())
fun err(error: Any?) = Logger.e(error.toString())


