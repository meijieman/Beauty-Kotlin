package com.major.beauty.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/11/6 21:31
 */
object IntentUtil {
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phone 电话号码
     */
    fun callPhone(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phone")
        intent.data = data
        context.startActivity(intent)
    }
}