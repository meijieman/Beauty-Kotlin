package com.major.beauty.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.base
 * ProjectName: Beauty
 * Date: 2019/6/3 11:35
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getRootView())
        init()
    }

    protected abstract fun getRootView(): Int
    protected abstract fun init()

    override fun onDestroy() {
        super.onDestroy()
    }

    // intent 跳转
    fun skipIntent(clazz: Class<*>?) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}