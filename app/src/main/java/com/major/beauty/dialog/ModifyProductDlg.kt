package com.major.beauty.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.beauty.R
import com.major.beauty.bean.Product
import com.major.beauty.dao.ProductDao
import com.major.beauty.ui.ProductsActivity
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.ui.vm.ProductsVM
import kotlinx.android.synthetic.main.dlg_modify_product.*

/**
 * @desc: 新增，修改 产品 dialog
 * @author: Major
 * @since: 2019/9/8 23:24
 */
class ModifyProductDlg(private val mContext: Context) : AlertDialog(mContext) {

    private val mTies by lazy { listOf(tet_name, tet_price, tet_unit, tet_comment) }

    private var mProduct: Product? = null
    private val mDao: ProductDao by lazy { ProductDao() }

    constructor(context: Context, product: Product?) : this(context) {
        mProduct = product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_modify_product)

        setCanceledOnTouchOutside(false)
        val params: WindowManager.LayoutParams? = window?.attributes
        params?.softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE // 显示dialog的时候,就显示软键盘
        params?.flags =
            WindowManager.LayoutParams.FLAG_DIM_BEHIND // 就是这个属性导致不能获取焦点,默认的是FLAG_NOT_FOCUSABLE,故名思义不能获取输入焦点,
        window?.attributes = params

        mProduct?.let {
            mTies[0].setText(it.name)
            mTies[1].setText(it.price.toString())
            mTies[2].setText(it.unit)
            mTies[3].setText(it.comment)
        }

        mTies[0].requestFocus()
        mb_dialog_commit.setOnClickListener { v ->
            when (v.id) {
                R.id.mb_dialog_commit -> {
                    val name: String = mTies[0].text.toString()
                    val price: String = mTies[1].text.toString()
                    val unit: String = mTies[2].text.toString()
                    val comment: String = mTies[3].text.toString()
                    if (CommonUtil.isEmpty(name)) {
                        mTies[0].error = "产品名称不能为空"
                        return@setOnClickListener
                    }
                    if (mProduct == null) {
                        // 新增
                        mProduct = Product()
                    }
                    mProduct?.run {
                        this.name = name
                        this.price = toDouble(price)
                        this.unit = unit
                        this.comment = comment

                        val rst = mDao.insertOrUpdate(this)
                        LogUtil.i("rst $rst")
                        if (rst != -1L) {
                            dismiss()
                            val updateLD: MutableLiveData<Int> =
                                ViewModelProviders.of(mContext as ProductsActivity)
                                    .get(ProductsVM::class.java).update
                            updateLD.postValue(CustomersVM.ADD)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    companion object {
        fun toDouble(str: String): Double {
            return try {
                str.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }
    }

}