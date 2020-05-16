package com.major.beauty.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.base.BaseFragment
import com.major.beauty.dao.CustomerDao
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.fgt_management.*

/**
 * Desc: 管理页面
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.vm
 * ProjectName: Beauty
 * Date: 2019/6/3 12:42
 */
class ManagementFgt : BaseFragment() {

    private val mCustomers: CardView by lazy { btn_management_customers }
    private val mProducts: CardView by lazy { btn_management_products }
    private val mItems: CardView by lazy { btn_management_items }
    private val mCounts: TextView by lazy { tv_count_customer }

    private val mDao: CustomerDao = CustomerDao()

    override val rootView: Int = R.layout.fgt_management

    @SuppressLint("ClickableViewAccessibility")
    override fun init() {
        mCustomers.setOnTouchListener { view: View?, motionEvent: MotionEvent? ->
            onTouch(view, motionEvent)
        }
        mProducts.setOnTouchListener { view: View?, motionEvent: MotionEvent? ->
            onTouch(view, motionEvent)
        }
        mItems.setOnTouchListener { view: View?, motionEvent: MotionEvent? ->
            onTouch(view, motionEvent)
        }
        mCounts.text = String.format("共%s位", mDao.queryCount())
        ViewModelProviders.of(this).get(CustomersVM::class.java).update
            .observe(this, Observer { integer ->
                if (integer == CustomersVM.ADD || integer == CustomersVM.DEL) {
                    ToastUtil.showShort("收到更新")
                    mCounts.text = String.format("共%s位", mDao.queryCount())
                }
            })

        setViewsListener(
            listener,
            btn_management_customers,
            btn_management_products,
            btn_management_items
        )
    }

    private fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        when (motionEvent!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val upAnim = ObjectAnimator.ofFloat(view, "translationZ", 16f)
                upAnim!!.duration = 100
                upAnim.interpolator = DecelerateInterpolator()
                upAnim.start()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0f)
                downAnim!!.duration = 100
                downAnim.interpolator = AccelerateInterpolator()
                downAnim.start()
            }
        }
        return false
    }

    val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_management_customers -> startActivity(
                Intent(mActivity, CustomersActivity::class.java)
            )
            R.id.btn_management_products -> startActivity(
                Intent(mActivity, ProductsActivity::class.java)
            )
            R.id.btn_management_items -> startActivity(Intent(mActivity, ItemsActivity::class.java))
            else -> {
            }
        }
    }
}