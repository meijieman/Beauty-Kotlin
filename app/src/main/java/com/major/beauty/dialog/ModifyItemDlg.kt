package com.major.beauty.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.beauty.R
import com.major.beauty.adapter.ProductCountAdapter
import com.major.beauty.base.App
import com.major.beauty.bean.Item
import com.major.beauty.bean.ProductCount
import com.major.beauty.dao.ItemDao
import com.major.beauty.ui.ItemsActivity
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.ui.vm.ItemsVM
import kotlinx.android.synthetic.main.dlg_modify_item.*
import java.util.*

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/9 23:15
 */
class ModifyItemDlg(private val mContext: Context) : AlertDialog(mContext) {

    val mTies: List<TextInputEditText> by lazy { listOf(tet_name, tet_price, tet_comment) }

    private val mDao: ItemDao by lazy { ItemDao() }
    private var mItem: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_modify_item)

        setCanceledOnTouchOutside(false)
        val params: WindowManager.LayoutParams = getWindow().getAttributes()
        params.softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE // 显示dialog的时候,就显示软键盘
        params.flags =
            WindowManager.LayoutParams.FLAG_DIM_BEHIND // 就是这个属性导致不能获取焦点,默认的是FLAG_NOT_FOCUSABLE,故名思义不能获取输入焦点,
        getWindow().setAttributes(params)
        mTies[0].requestFocus()
        val adapter = ProductCountAdapter(context)
        rv_item.adapter = adapter
        val manager = LinearLayoutManager(context)
        rv_item.layoutManager = manager
        if (mItem != null) {
            mTies[0].setText(mItem!!.name)
            val productCounts = mItem!!.productCounts
            adapter.setData(productCounts)
        } else {
            val productCounts: MutableList<ProductCount> = ArrayList()
            productCounts.add(ProductCount())
            adapter.setData(productCounts)
        }

        mb_dialog_commit?.setOnClickListener { v ->
            when (v.id) {
                R.id.mb_dialog_commit -> {
                    val name: String = mTies[0].text.toString()
                    val comment: String = mTies[2].text.toString()
                    if (CommonUtil.isEmpty(name)) {
                        mTies[0].error = "项目名称不能为空"
                        return@setOnClickListener
                    }
                    if (mItem == null) {
                        // 新增
                        mItem = Item(App.instance.mAvatar.name)
                    }
                    mItem!!.name = name
                    val rst = mDao.insertOrUpdate(mItem!!)
                    LogUtil.i("rst $rst")
                    if (rst != -1L) {
                        dismiss()
                        val updateLD: MutableLiveData<Int> =
                            ViewModelProviders.of(mContext as ItemsActivity)
                                .get(ItemsVM::class.java).update
                        updateLD.postValue(CustomersVM.Companion.ADD)
                    }
                }
                else -> {
                }
            }
        }
    }

}