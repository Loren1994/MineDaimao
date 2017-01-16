package com.example.loren.minesample

import android.content.pm.ShortcutManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ShortCutsActivity : AppCompatActivity() {

    lateinit var shortCutsManager: ShortcutManager
    lateinit var mAdapter: ShortcutAdapter
    val mList = arrayListOf("张三 18661881639", "李四 18612341234", "王五 18661881234")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_cuts)
//        recycler.layoutManager = GridLayoutManager(this, 1)
//        mAdapter = ShortcutAdapter(this, mList)
//        recycler.adapter = mAdapter
//        shortCutsManager = getSystemService(ShortcutManager::class.java)
//        val shortcutInfo = arrayListOf<ShortcutInfo>()
//        (0..shortCutsManager.maxShortcutCountPerActivity).forEach {
//            val intent = Intent(this, LorenActivity::class.java).putExtra("message", "联系人:${mAdapter.mdata[it]}")
//            intent.action = Intent.ACTION_VIEW
//            val info = ShortcutInfo.Builder(this, "id" + it)
//                    .setShortLabel(mAdapter.mdata[it])
//                    .setLongLabel("联系人:${mAdapter.mdata[it]}")
//                    .setIntent(intent)
//                    .build()
//            shortcutInfo.add(it, info)
//        }
//        shortCutsManager.dynamicShortcuts = shortcutInfo
    }
}
