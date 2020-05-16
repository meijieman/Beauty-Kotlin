package com.major.beauty.ui

import android.graphics.Color
import androidx.appcompat.app.ActionBar
import com.google.android.material.textfield.TextInputEditText
import com.major.base.log.LogUtil
import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.Constant
import com.major.beauty.bean.Product
import com.major.beauty.dao.ProductDao
import kotlinx.android.synthetic.main.act_product_detail.*

/**
 * @desc: 项目详情
 * @author: Major
 * @since: 2019/6/7 23:42
 */
class ProductDetailActivity : BaseActivity() {

    private val mTies: List<TextInputEditText> by lazy {
        listOf(tet_name, tet_price, tet_unit, tet_comment)
    }

    private val mDao: ProductDao = ProductDao()

    override fun getRootView(): Int = R.layout.act_product_detail

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        fab_product_detail.setImageResource(R.drawable.fab_add)
        for (tie in mTies) {
            tie.setEnabled(false)
            tie.setTextColor(getResources().getColor(R.color.primary_text))
            tie.setHighlightColor(Color.GREEN)
        }
        val pid: Long = intent.getLongExtra(Constant.Companion.EXTRA_PID, -1L)
        if (pid != -1L) {
            val product = mDao.queryById(pid, Product::class.java)
            if (product != null) {
                mTies[0].setText(product.name)
                mTies[1].setText(product.price.toString())
                mTies[2].setText(product.unit)
                mTies[3].setText(product.comment)
            } else {
                LogUtil.e("data not found $pid")
            }
        } else {
            // 新增模式
        }

        fab_product_detail.setOnClickListener { view ->
            when (view.id) {
                R.id.fab_product_detail -> {
                }
                else -> {
                }
            }
        }
    }
}