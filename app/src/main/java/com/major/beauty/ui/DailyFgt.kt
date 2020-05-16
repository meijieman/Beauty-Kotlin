package com.major.beauty.ui

import android.content.Intent
import android.view.View
import com.major.beauty.R
import com.major.beauty.base.BaseFragment
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.fgt_daily.*

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.vm
 * ProjectName: Beauty
 * Date: 2019/6/3 12:42
 */
class DailyFgt : BaseFragment() {

    override val rootView: Int = R.layout.fgt_daily

    override fun init() {

        setViewsListener(
            listener,
            mb_daily_confirm,
            mb_daily_pay,
            mb_daily_sales,
            mb_daily_appointment_list
        )
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mb_daily_confirm -> startActivity(Intent(context, ApptActivity::class.java))
            R.id.mb_daily_pay -> startActivity(Intent(context, PayActivity::class.java))
            R.id.mb_daily_sales -> startActivity(Intent(context, SalesActivity::class.java))
            R.id.mb_daily_appointment_list -> skipIntent(ApptsActivity::class.java)
        }
    }
}