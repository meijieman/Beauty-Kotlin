package com.major.beauty.ui

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.major.base.rx.rxtask.RxTask
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import com.major.beauty.bean.Customer
import com.major.beauty.dao.ItemDao
import com.major.beauty.dao.ProductDao
import com.major.beauty.dialog.SearchCustomDialog
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.act_sales.*
import kotlinx.android.synthetic.main.include_app_bar_layout.*
import kotlinx.android.synthetic.main.include_search_customer.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @desc: 销售登记界面
 * @author: Major
 * @since: 2019/6/7 22:12
 */
class SalesActivity : BaseActivity() {

//    val mItem: TextView = tv_items
//    val mProduct: TextView = tv_products
//    val mTotal: TextView = tv_total

    private val mProductDao: ProductDao by lazy { ProductDao() }
    private val mItemDao: ItemDao by lazy { ItemDao() }

    override fun getRootView(): Int = R.layout.act_sales

    override fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("销售登记")
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setViewsListener(
            listener,
            mb_customer_query,
            mb_sales_item,
            mb_sales_product,
            mb_sales_confirm
        )
    }

    val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.mb_customer_query -> {
                val search = SearchCustomDialog(this)
                search.setResultListener(object : SearchCustomDialog.ResultListener {
                    override fun onResult(customer: Customer) {
                        tiet_customer_info.setText(customer.name)
//                        tiet_customer_info.text = customer.name
                    }
                })
                search.show()
            }
            R.id.mb_sales_item -> {
                // 弹出多选对话框
                val items = mItemDao!!.query()
                if (CommonUtil.isEmpty(items)) {
                    ToastUtil.showShort("没有可选项目")
                    return@OnClickListener
                }
                val test: MutableList<String?> =
                    ArrayList()
                var i = 0
                while (i < 20) {
                    test.add("项目 $i")
                    i++
                }
                val dialog: AlertDialog = AlertDialog.Builder(this)
                    .setTitle("选择项目")
                    .setMultiChoiceItems(test.toTypedArray(), null, null)
                    .setPositiveButton("确定") { _, _ -> }
                    .setNegativeButton("取消", null)
                    .create()
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
            }
            R.id.mb_sales_product -> {
            }
            R.id.mb_sales_confirm -> {
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