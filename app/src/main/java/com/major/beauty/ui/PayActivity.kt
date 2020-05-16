package com.major.beauty.ui

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.android.material.snackbar.Snackbar
import com.major.base.rx.rxtask.RxTask
import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import com.major.beauty.bean.Customer
import com.major.beauty.dao.CustomerDao
import com.major.beauty.dialog.SearchCustomDialog
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.act_pay.*
import kotlinx.android.synthetic.main.include_app_bar_layout.*
import kotlinx.android.synthetic.main.include_search_customer.*
import java.util.concurrent.TimeUnit

/**
 * @desc: 护理划卡界面
 * @author: Major
 * @since: 2019/6/7 22:12
 */
class PayActivity : BaseActivity() {

    private val mCustomerDao: CustomerDao = CustomerDao()
    private var mCustomer: Customer? = null

    override fun getRootView(): Int = R.layout.act_pay

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("护理划卡")
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setViewsListener(listener, mb_customer_query, mb_pay_confirm)
    }

    val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mb_customer_query -> {
                val search = SearchCustomDialog(this)
                search.setResultListener(object : SearchCustomDialog.ResultListener {
                    override fun onResult(customer: Customer) {
                        tiet_customer_info.setText(customer.name)
                        mCustomer = customer
                    }
                })

                search.show()
            }
            R.id.mb_pay_confirm -> {
                val snackbar: Snackbar = Snackbar.make(view, "提交成功", Snackbar.LENGTH_SHORT)
                snackbar.getView().setBackgroundColor(Color.BLUE)
                snackbar.show()
                RxTask.doOnUIThreadDelay(this::finish, 2, TimeUnit.SECONDS)
            }
            else -> {
            }
        }
    }
}