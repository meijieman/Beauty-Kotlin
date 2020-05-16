package com.major.beauty.util

import android.content.Context
import android.content.SharedPreferences
import com.major.beauty.base.App

/**
 * @desc: SharedPreferences 工具类
 * @author: Major
 * @since: 2019/3/2 12:05
 */
object SPUtil {

    val sSp: SharedPreferences by lazy {
        App.instance.getSharedPreferences("sp_beauty", Context.MODE_PRIVATE)
    }

    fun getString(key: String): String? {
        return sSp.getString(key, null)
    }

    fun putString(key: String, value: String?) {
        sSp.edit().putString(key, value).apply()
    }
}