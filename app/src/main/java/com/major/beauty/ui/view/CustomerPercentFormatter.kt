package com.major.beauty.ui.view

import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui.view
 * ProjectName: Beauty
 * Date: 2019/6/25 9:18
 */
class CustomerPercentFormatter : ValueFormatter {

    constructor(data: ChartData<*>) : super() {
        val dataSets: MutableList<*> = data.dataSets
    }

    private val mFormat: DecimalFormat by lazy { DecimalFormat("###,###,##0.0") }

    override fun getFormattedValue(value: Float): String {
        return mFormat.format(value.toDouble()) + " %"
    }

}