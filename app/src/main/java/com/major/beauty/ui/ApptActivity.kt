package com.major.beauty.ui

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.major.base.log.LogUtil
import com.major.base.util.KeyboardUtil
import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import com.major.beauty.bean.Appointment
import com.major.beauty.bean.Customer
import com.major.beauty.dao.ApptDao
import com.major.beauty.dialog.SearchCustomDialog
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.act_appt.*
import kotlinx.android.synthetic.main.include_app_bar_layout.*
import kotlinx.android.synthetic.main.include_search_customer.*
import java.text.DateFormat
import java.util.*

/**
 * @desc: 客户预约界面
 * @author: Major
 * @since: 2019/9/9 23:40
 */
class ApptActivity : BaseActivity() {

    private var calendar: Calendar? = null
    private val mDao: ApptDao by lazy { ApptDao() }
    private var mStartTime: Long = 0
    private var mEndTime: Long = 0
    private var mCustomer: Customer? = null

    override fun getRootView(): Int = R.layout.act_appt

    override fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("客户预约")
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        calendar = Calendar.getInstance()

        setViewsListener(
            listener,
            mb_customer_query,
            mb_appt_time_start,
            mb_appt_time_end,
            mb_appt_add,
            mb_appt_time_date
        )
    }

    var listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mb_customer_query -> {
                // 弹出客户搜索框
                val dialog = SearchCustomDialog(this)
                dialog.setResultListener(object : SearchCustomDialog.ResultListener {
                    override fun onResult(customer: Customer) {
                        tiet_customer_info.setText(customer.name)
                        mCustomer = customer
                    }
                })
//                dialog.setResultListener { customer: Customer ->
//                    mCustomerInfo.text = customer.name
//                    mCustomer = customer
//                }

                dialog.show()
            }
            R.id.mb_appt_time_date -> {
                // 选择日期
                val dates =
                    arrayOf<String?>("今天", "明天", "后天", "2019/11/9")
                val dialog2: AlertDialog = AlertDialog.Builder(this)
                    .setTitle("选择项目")
                    .setSingleChoiceItems(dates, 0) { dialogInterface, i ->
                        mb_appt_time_date.setText(dates[i])
                        dialogInterface.dismiss()
                    }
                    .create()
                dialog2.setCanceledOnTouchOutside(false)
                dialog2.show()
            }
            R.id.mb_appt_time_start -> {
                val tpd = TimePickerDialog(
                    this,
                    OnTimeSetListener { timePicker: TimePicker?, i: Int, i1: Int ->
                        calendar!![Calendar.HOUR_OF_DAY] = i
                        calendar!![Calendar.MINUTE] = i1
                        val time =
                            DateFormat.getTimeInstance(DateFormat.SHORT)
                                .format(calendar!!.time)
                        mb_appt_time_start.setText(time)
                        mStartTime = calendar!!.timeInMillis
                    },
                    calendar!![Calendar.HOUR_OF_DAY],
                    calendar!![Calendar.MINUTE],
                    true
                )
                tpd.show()
            }
            R.id.mb_appt_time_end -> {
                val tpdEnd = TimePickerDialog(
                    this,
                    OnTimeSetListener { timePicker: TimePicker?, i: Int, i1: Int ->
                        calendar!![Calendar.HOUR_OF_DAY] = i
                        calendar!![Calendar.MINUTE] = i1
                        val time =
                            DateFormat.getTimeInstance(DateFormat.SHORT)
                                .format(calendar!!.time)
                        mb_appt_time_end.setText(time)
                        mEndTime = calendar!!.timeInMillis
                    },
                    calendar!![Calendar.HOUR_OF_DAY],
                    calendar!![Calendar.MINUTE],
                    true
                )
                tpdEnd.show()
            }
            R.id.mb_appt_add -> {
                if (mCustomer == null) {
                    Snackbar.make(view, "请选择需要预约的客户", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                KeyboardUtil.hideKeyboard(this, view)
                val appt = Appointment()
                mCustomer?.run {
                    appt.cid = id
                    appt.name = name
                    appt.phone = phone
                }
                appt.startTime = mStartTime
                appt.endTime = mEndTime
                appt.createTime = System.currentTimeMillis()
                appt.comment = tie_appt_comment.getText().toString()
                val update = mDao.insertOrUpdate(appt)
                LogUtil.i("update $update")
                if (update != -1L) {
                    Snackbar.make(view, "预约成功", Snackbar.LENGTH_SHORT).show()
                    finish()
                }
            }
            else -> {
            }
        }
    }

}