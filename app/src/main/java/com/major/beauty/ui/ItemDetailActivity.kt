package com.major.beauty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.adapter.ProductCountAdapter
import com.major.beauty.base.App
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.Constant
import com.major.beauty.bean.Item
import com.major.beauty.bean.ProductCount
import com.major.beauty.bean.SmartItem
import com.major.beauty.dao.ItemDao
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.ui.vm.ItemsVM
import kotlinx.android.synthetic.main.act_item_detail.*
import java.util.*

/**
 * @desc: 项目详情
 * @author: Major
 * @since: 2019/6/7 23:42
 */
class ItemDetailActivity : BaseActivity() {

    private val mTies: List<TextInputEditText> by lazy { listOf(tet_name, tet_price, tet_comment) }

    private val mDao: ItemDao = ItemDao()
    private var mItem: Item? = null
    private var mAdapter: ProductCountAdapter = ProductCountAdapter(this)

    override fun getRootView(): Int = R.layout.act_item_detail

    override fun init() {
        val iid: Long = intent.getLongExtra(Constant.EXTRA_IID, -1)
        if (iid != -1L) {
            mItem = mDao.queryById(iid, Item::class.java)
            if (mItem == null) {
                LogUtil.e("error $iid")
            } else {
                mTies[0].setText(mItem!!.name)
            }
        } else {
            mItem = Item(App.instance.mAvatar.name)
            val productCounts: MutableList<ProductCount> = ArrayList()
            productCounts.add(ProductCount())
            mItem?.productCounts = productCounts
        }
        rv_item_detail.setLayoutManager(LinearLayoutManager(this))

//        MutilInputAdapter adapter = new MutilInputAdapter(this);
        rv_item_detail.adapter = mAdapter
        mAdapter.setData(mItem?.productCounts)

//        adapter.setData(getDatas());

        mb_item_commit.setOnClickListener { view ->
            when (view.id) {
                R.id.mb_item_commit -> {
                    val name: String = mTies[0].getText().toString()
                    val comment: String = mTies[2].getText().toString()
                    if (CommonUtil.isEmpty(name)) {
                        mTies[0].setError("项目名称不能为空")
                        return@setOnClickListener
                    }
                    mItem?.run {
                        this.name = name
                        this.productCounts = mAdapter.getData()
                        this.comment = comment
                        val rst = mDao.insertOrUpdate(this)
                        LogUtil.i("rst $rst")
                        if (rst != -1L) {
                            val updateLD: MutableLiveData<Int> =
                                ViewModelProviders.of(this@ItemDetailActivity)
                                    .get(ItemsVM::class.java).update
                            updateLD.postValue(CustomersVM.ADD)
                            ToastUtil.showShort("add success")
                            finish()
                        } else {
                            ToastUtil.showShort("add fail")
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    private val datas: MutableList<SmartItem>
        private get() {
            val list: MutableList<SmartItem> = ArrayList()
            run {
                val item = SmartItem(SmartItem.ITEM_EDIT)
                item.title = "标题"
                item.subTitle = "副标题"
                list.add(item)
            }
            run {
                val item = SmartItem(SmartItem.Companion.ITEM_EDIT)
                item.title = "标题"
                item.subTitle = "副标题"
                list.add(item)
            }
            run {
                val item = SmartItem(SmartItem.Companion.ITEM_SPINNER)
                item.title = "标题"
                item.subTitle = "副标题"
                val data: MutableList<String?> =
                    ArrayList()
                for (i in 0..9) {
                    data.add("选项 $i")
                }
                item.spinnerShow = data
                list.add(item)
            }
            run {
                val item = SmartItem(SmartItem.Companion.ITEM_SPINNER)
                item.title = "标题"
                item.subTitle = "副标题"
                list.add(item)
            }
            run {
                val item = SmartItem(SmartItem.Companion.ITEM_TIMMER)
                item.title = "标题"
                list.add(item)
            }
            return list
        }
}