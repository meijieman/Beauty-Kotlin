package com.major.beauty.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.rx.rxtask.RxTask
import com.major.base.util.CommonUtil
import com.major.beauty.R
import com.major.beauty.adapter.SearchCustomerAdapter
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.Customer
import com.major.beauty.dao.CustomerDao
import kotlinx.android.synthetic.main.dlg_search_custom.*

/**
 * @desc: 根据顾客名称，电话号码搜索
 * @author: Major
 * @since: 2019/11/2 9:09
 */
class SearchCustomDialog(context: Context) : AlertDialog(context) {

    private val mDao: CustomerDao by lazy { CustomerDao() }

    private val mAdapter: SearchCustomerAdapter by lazy { SearchCustomerAdapter(getContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_search_custom)
        setCanceledOnTouchOutside(false)
        val params: WindowManager.LayoutParams = window!!.attributes
        params.softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE // 显示dialog的时候,就显示软键盘
        params.flags =
            WindowManager.LayoutParams.FLAG_DIM_BEHIND // 就是这个属性导致不能获取焦点,默认的是FLAG_NOT_FOCUSABLE,故名思义不能获取输入焦点,
        window!!.setAttributes(params)

        rv_item.adapter = mAdapter
        val manager = LinearLayoutManager(context)
        rv_item.layoutManager = manager

        mAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener<Customer> {
            override fun onItemClick(pos: Int, bean: Customer, view: View?) {
                mListener?.onResult(bean)
                dismiss()
            }
        })


        RxTask.doTask(object : RxTask.Task<MutableList<Customer>?> {
            override fun onIOThread(): MutableList<Customer>? {
                return mDao.query()
            }

            override fun onUIThread(customers: MutableList<Customer>?) {
                if (CommonUtil.isEmpty(customers)) {
                    LogUtil.d("customers is empty.")
                } else {
                    mAdapter.setData(customers!!)
                }
            }
        })
        mb_dialog_commit.setOnClickListener { v ->
            when (v!!.id) {
                R.id.mb_dialog_commit ->
                    RxTask.doTask(object : RxTask.Task<MutableList<Customer>?> {
                        override fun onIOThread(): MutableList<Customer>? {
                            val text: String = tet_search_text.text.toString()
                            return if (CommonUtil.isEmpty(text)) {
                                mDao.query()
                            } else mDao.queryByNameOrPhone(text)
                        }

                        override fun onUIThread(customers: MutableList<Customer>?) {
                            if (CommonUtil.isEmpty(customers)) {
                                LogUtil.d("customers is empty.")
                                tet_search_text.setError("搜索结果为空", null)
                            }
                            mAdapter.setData(customers!!)
                        }
                    })
                else -> {

                }
            }
        }
    }

    private var mListener: ResultListener? = null

    fun setResultListener(listener: ResultListener?) {
        mListener = listener
    }

    interface ResultListener {
        fun onResult(customer: Customer)
    }
}