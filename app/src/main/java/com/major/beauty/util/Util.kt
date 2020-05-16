package com.major.beauty.util

import android.view.View

/**
 * @desc: TODO
 * @author: Major
 * @since: 2020/4/19 12:00
 */

fun setViewsListener(listener: View.OnClickListener, vararg views: View) {
    for (view in views) {
        view.setOnClickListener(listener)
    }
}