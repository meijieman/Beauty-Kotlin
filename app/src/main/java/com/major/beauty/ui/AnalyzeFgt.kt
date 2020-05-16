package com.major.beauty.ui

import android.graphics.Color
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.major.beauty.R
import com.major.beauty.base.BaseFragment
import kotlinx.android.synthetic.main.fgt_analyze.*
import java.util.*

/**
 * Desc: 分析视图
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.vm
 * ProjectName: Beauty
 * Date: 2019/6/3 12:42
 */
class AnalyzeFgt : BaseFragment() {

    private val mBarChart: BarChart by lazy { bc_analyze }
    private val mPieChart: PieChart by lazy { pc_analyze }
    private val mLineChart: LineChart by lazy { lc_analyze }
    private val mRadarChart: RadarChart by lazy { rc_analyze }

    override val rootView: Int = R.layout.fgt_analyze

    override fun init() {
        initBarChart()
        showBarChart(datas, "净资产收益率（%）", resources.getColor(R.color.colorAccent))
        initPieChart()
        initLineChart()
        initRadarChart()
    }

    // 折线图
    private fun initLineChart() {
        mLineChart.setScaleEnabled(false)
        //设置样式
        val rightAxis: YAxis = mLineChart.axisRight
        //设置图表右边的y轴禁用
        rightAxis.isEnabled = false
        val leftAxis: YAxis = mLineChart.axisLeft
        //设置图表左边的y轴禁用
//        leftAxis.setEnabled(false);
        //设置x轴
        mLineChart.xAxis.run {
            textColor = Color.parseColor("#333333")
            textSize = 11f
            axisMinimum = 0f
            setDrawAxisLine(true) //是否绘制轴线
            setDrawGridLines(false) //设置x轴上每个点对应的线
            setDrawLabels(true) //绘制标签  指x轴上的对应数值
            position = XAxis.XAxisPosition.BOTTOM //设置x轴的显示位置
            granularity = 1f //禁止放大后x轴标签重绘
        }

        //透明化图例
        mLineChart.legend.run {
            form = Legend.LegendForm.NONE
            textColor = Color.WHITE
        }

        //隐藏x轴描述
        val description = Description()
        description.isEnabled = false

        mLineChart.description = description

        //1.设置x轴和y轴的点
        val entries: MutableList<Entry> = ArrayList<Entry>()
        for (i in 0..11) {
            entries.add(Entry(i * 1f, Random().nextInt(300) * 1f))
        }
        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        dataSet.apply {
            color = Color.parseColor("#7d7d7d") //线条颜色
            setCircleColor(Color.parseColor("#7d7d7d")) //圆点颜色
            lineWidth = 1f //线条宽度
        }
        //3.chart设置数据
        val lineData = LineData(dataSet)
        mLineChart.data = lineData
    }

    // 雷达图
    private fun initRadarChart() {
        val yAxis: YAxis = mRadarChart.yAxis
        yAxis.axisMinimum = 0f
        val radarEntries: MutableList<RadarEntry> = ArrayList<RadarEntry>()
        for (i in 0..3) {
            radarEntries.add(RadarEntry((i + 1) * 10f))
        }
        val dataSet = RadarDataSet(radarEntries, "")
        dataSet.color = Color.RED
        val radarData = RadarData(dataSet)
        mRadarChart.animateXY(1000, 1000)
        mRadarChart.data = radarData
    }

    // 饼图
    private fun initPieChart() {

        mPieChart.description = Description().apply { text = "" }

        // 实心饼图
//        mPieChart.setHoleRadius(0f);
//        mPieChart.setTransparentCircleRadius(0f);

        val data = arrayListOf(PieEntry(30f, "耗材a"), PieEntry(60f, "耗材b"), PieEntry(10f, "其他"))
        val dataSet = PieDataSet(data, "").apply {
            setDrawValues(true)
            valueFormatter = PercentFormatter()

        }

        dataSet.colors = ArrayList<Int>().apply {
            add(resources.getColor(R.color.colorAccent))
            add(resources.getColor(R.color.colorPrimary))
            add(resources.getColor(R.color.colorPrimaryDark))
        }

        mPieChart.data = PieData(dataSet).apply {
            setDrawValues(true)
        }
    }

    private val datas: MutableList<Float> = MutableList(10) {
        Random().nextFloat() * 100f
    }

    private fun showBarChart(dateValueList: MutableList<Float>, name: String, color: Int) {
        val entries: ArrayList<BarEntry> = ArrayList<BarEntry>()
        for (i in dateValueList.indices) {
            val barEntry = BarEntry(i * 1f, dateValueList[i], "xa")
            entries.add(barEntry)
        }
        // 每一个 BarDataSet 代表一类柱状图
        val barDataSet = BarDataSet(entries, name)
        initBarDataSet(barDataSet, color)
        val data = BarData(barDataSet)
        //        data.setValueFormatter(new CustomerPercentFormatter(data));
        mBarChart.data = data
    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private fun initBarDataSet(barDataSet: BarDataSet, color: Int) {
        barDataSet.apply {
            this.color = color
            // 是否绘制数据值
            setDrawValues(true)
//            valueFormatter = object : ValueFormatter() {
//                override fun getFormattedValue(value: Float): String {
//                    return "$value 单位";
//                }
//            };
            formLineWidth = 1f
            formSize = 11f
            valueTextSize = 7f
            valueTextColor = color
        }
    }

    /**
     * 初始化BarChart图表
     */
    private fun initBarChart() {
        // 右下角描述内容
        mBarChart.description = Description().apply { text = "2019-06" }


//        mBarChart.setNoDataText("暂无数据");
//        mBarChart.setDoubleTapToZoomEnabled(false);
//        mBarChart.setDrawBorders(false);
        mBarChart.setScaleEnabled(false)
        //        mBarChart.setTouchEnabled(false);
        mBarChart.setDrawBorders(true)
        mBarChart.setDrawBarShadow(true)
        //背景颜色
        mBarChart.setBackgroundColor(Color.WHITE)
        //不显示图表网格
        mBarChart.setDrawGridBackground(false)
        //背景阴影
        mBarChart.setDrawBarShadow(false)
        mBarChart.isHighlightFullBarEnabled = false
        //显示边框
        mBarChart.setDrawBorders(false)
        //设置动画效果
        mBarChart.animateY(1000, Easing.Linear)
        mBarChart.animateX(1000, Easing.Linear)
        mBarChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                Toast.makeText(
                    context,
                    "${e.x.toString()}  ${e.getData()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected() {}
        })
        //X轴
        mBarChart.xAxis.run {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = Color.BLACK
//            axisMinimum = 0f;
            // 粒度
            isGranularityEnabled = false
            granularity = 1f
        }

        //自定义 MarkerView
//        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
//        mBarChart.setMarker(mv);

        //左侧Y轴
        val leftAxis: YAxis = mBarChart.axisLeft
        leftAxis.setDrawGridLines(false)
        // 限制线
        leftAxis.setDrawLimitLinesBehindData(false)
        val limitLine = LimitLine(62f, "合格线")
        limitLine.lineColor = resources.getColor(R.color.colorPrimary)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP //文字颜色、大小
        limitLine.textSize = 8f
        limitLine.lineWidth = 1f
        limitLine.enableDashedLine(5f, 3f, 0f) //设置虚线
        leftAxis.addLimitLine(limitLine)
        // 保证Y轴从0开始，不然会上移一点
//        leftAxis.setAxisMinimum(0f);

        //右侧Y轴
        val rightAxis: YAxis = mBarChart.axisRight
        rightAxis.axisMinimum = 0f
        rightAxis.isEnabled = false // 不显示右侧 y 轴

        //图例
        val legend: Legend = mBarChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textSize = 11f
        //显示位置
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //是否绘制在图表里面
        legend.setDrawInside(false)
    }
}