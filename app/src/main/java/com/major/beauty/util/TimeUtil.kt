package com.major.beauty.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.util
 * ProjectName: Beauty
 * Date: 2019/10/28 10:42
 */
object TimeUtil {

    private val DEFAULT_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun format(millis: Long): String {
        return DEFAULT_FORMAT.format(Date(millis))
    }
}