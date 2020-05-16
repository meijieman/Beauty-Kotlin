package com.major.beauty.ui

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.adapter.ApptAdapter
import com.major.beauty.base.App
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.BaseAdapter
import com.major.beauty.base.Constant
import com.major.beauty.bean.Appointment
import com.major.beauty.dao.ApptDao
import com.major.beauty.ui.ApptsActivity
import com.major.beauty.ui.decoration.SpaceDecoration
import com.major.beauty.util.IntentUtil
import kotlinx.android.synthetic.main.act_appts.*
import kotlinx.android.synthetic.main.include_app_bar_layout.*

/**
 * @desc: 预约列表界面
 * @author: Major
 * @since: 2019/11/2 9:54
 */
class ApptsActivity : BaseActivity() {

    private val mAdapter: ApptAdapter by lazy { ApptAdapter(this) }
    private val mDao: ApptDao by lazy { ApptDao() }

    override fun getRootView(): Int = R.layout.act_appts

    override fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("客户预约列表")
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        rv_management.layoutManager = manager
        val itemDecoration = SpaceDecoration(14)
        rv_management.addItemDecoration(itemDecoration)
        rv_management.adapter = mAdapter

        mAdapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<Appointment> {
            override fun onItemClick(
                pos: Int,
                appt: Appointment,
                view: View?
            ) {
                // 查看详情
                val intent = Intent(this@ApptsActivity, CustomerDetailActivity::class.java)
                intent.putExtra(Constant.Companion.EXTRA_CID, appt.cid)
                startActivity(intent)
            }

            override fun onItemLongClick(
                pos: Int,
                appt: Appointment,
                view: View?
            ) {
                val opts =
                    arrayOf<String?>("已完成", "已取消", "已过期", "电话联系")
                val dialog: AlertDialog = AlertDialog.Builder(this@ApptsActivity)
                    .setTitle("操作")
                    .setSingleChoiceItems(opts, -1) { dialogInterface, i ->
                        if (i == opts.size - 1) {
                            IntentUtil.callPhone(App.instance, appt.phone)
                        } else {
                            ToastUtil.showShort(opts[i])
                        }
                        dialogInterface.dismiss()
                    }
                    .create()
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
            }
        })
        val appts = mDao.query()
        if (CommonUtil.isNotEmpty(appts)) {
            mAdapter.setData(appts)
        } else {
            LogUtil.i("no data")
        }
    }
}