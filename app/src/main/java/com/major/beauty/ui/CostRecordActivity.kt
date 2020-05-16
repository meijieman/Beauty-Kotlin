package com.major.beauty.ui

import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.beauty.R
import com.major.beauty.adapter.CostRecordAdapter
import com.major.beauty.base.BaseActivity
import com.major.beauty.dao.CostRecordDao
import com.major.beauty.ui.decoration.SpaceDecoration
import kotlinx.android.synthetic.main.act_cost_record.*
import kotlinx.android.synthetic.main.include_app_bar_layout.*

/**
 * Desc: 消费记录
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui
 * ProjectName: Beauty
 * Date: 2019/10/28 10:25
 */
class CostRecordActivity : BaseActivity() {

    private val mDao: CostRecordDao = CostRecordDao()

    override fun getRootView(): Int = R.layout.act_cost_record

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("消费记录")
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        val manager = LinearLayoutManager(this)
        manager.setOrientation(LinearLayoutManager.VERTICAL)
        rv_cost_record.setLayoutManager(manager)
        val itemDecoration = SpaceDecoration(14)
        rv_cost_record.addItemDecoration(itemDecoration)
        val adapter = CostRecordAdapter(this)
        rv_cost_record.setAdapter(adapter)
        val query = mDao.query()
        if (CommonUtil.isNotEmpty(query)) {
            adapter.setData(query)
        } else {
            LogUtil.d("no data")
        }
    }
}