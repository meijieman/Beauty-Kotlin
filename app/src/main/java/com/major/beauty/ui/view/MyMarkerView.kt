package com.major.beauty.ui.view

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.major.beauty.R

/**
 * Desc: 自定义覆盖物
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui.view
 * ProjectName: Beauty
 * Date: 2019/6/6 18:26
 */
class MyMarkerView(context: Context?, layoutResource: Int) :

    MarkerView(context, layoutResource) {

    private val tvContent: TextView

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        when (e) {
            is CandleEntry -> tvContent.text = Utils.formatNumber(e.high, 0, true)
            is BarEntry -> tvContent.text = Utils.formatNumber(e.y, 0, true)
            else -> tvContent.text = Utils.formatNumber(e!!.x, 0, true)
        }
    }

    override fun getOffset(): MPPointF? {
        val mpPointF = MPPointF()
        mpPointF.x = -getWidth() / 2f
        mpPointF.y = -getHeight().toFloat()
        return mpPointF
    }

    init {
        tvContent = findViewById(R.id.tvContent)
    }
}