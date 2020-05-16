package com.major.beauty.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import android.widget.LinearLayout
import com.major.beauty.R
import com.major.beauty.bean.SmartItem

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui.view
 * ProjectName: Beauty
 * Date: 2019/8/15 16:59
 */
class SmartItemView : LinearLayout {
    private lateinit var mItem: SmartItem

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        addView(View.inflate(getContext(), R.layout.view_smart_item, null))
    }

    fun setType(type: Int) {
        mItem = SmartItem(SmartItem.ITEM_DEFAULT)
        mItem.type = type
    }

    fun setItem(item: SmartItem) {
        mItem = item
        reload()
    }

    // 根据 type 装载数据
    private fun reload() {
        when (mItem.type) {
            SmartItem.Companion.ITEM_EDIT -> {
                val stub = findViewById<ViewStub?>(R.id.vs_edit)
                stub!!.inflate()
            }
            SmartItem.Companion.ITEM_TIMMER -> {
                val stub = findViewById<ViewStub?>(R.id.vs_timer)
                stub!!.inflate()
            }
            SmartItem.Companion.ITEM_SPINNER -> {
                val stub = findViewById<ViewStub?>(R.id.vs_spinner)
                stub!!.inflate()
            }
            else -> {
            }
        }
    }
}