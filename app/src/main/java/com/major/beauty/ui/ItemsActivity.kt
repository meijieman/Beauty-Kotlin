package com.major.beauty.ui

import android.content.Intent
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.adapter.ItemAdapter
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.BaseAdapter
import com.major.beauty.base.Constant
import com.major.beauty.bean.Item
import com.major.beauty.dao.ItemDao
import com.major.beauty.ui.ItemsActivity
import com.major.beauty.ui.behavior.HideButtonBehavior
import com.major.beauty.ui.decoration.SpaceDecoration
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.ui.vm.ItemsVM
import kotlinx.android.synthetic.main.act_items.*

/**
 * Desc: 项目列表
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui
 * ProjectName: Beauty
 * Date: 2019/6/4 12:45
 */
class ItemsActivity : BaseActivity() {

    private val mAdapter: ItemAdapter = ItemAdapter(this)
    private val mDao: ItemDao = ItemDao()

    override fun getRootView(): Int = R.layout.act_items

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = getSupportActionBar()
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("项目档案")
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val manager = LinearLayoutManager(this)
        manager.setOrientation(LinearLayoutManager.VERTICAL)
        rv_management.setLayoutManager(manager)
        val itemDecoration = SpaceDecoration(14)
        rv_management.addItemDecoration(itemDecoration)
        rv_management.setAdapter(mAdapter)
        ViewModelProviders.of(this).get(ItemsVM::class.java).update
            .observe(this, Observer { integer ->
                if (integer == ItemsVM.Companion.ADD) {
                    ToastUtil.showShort("收到更新")
                    val items = mDao.query()
                    if (CommonUtil.isNotEmpty(items)) {
                        mAdapter.setData(items)
                    } else {
                        LogUtil.i("no data")
                    }
                }
            })
        mAdapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<Item> {
            override fun onItemClick(pos: Int, bean: Item, view: View?) {
                val intent = Intent(this@ItemsActivity, ItemDetailActivity::class.java)
                intent.putExtra(Constant.Companion.EXTRA_IID, bean.id)
                startActivity(intent)
            }

            override fun onItemLongClick(pos: Int, bean: Item, view: View?) {
                AlertDialog.Builder(this@ItemsActivity)
                    .setTitle("提示")
                    .setMessage(String.format("\n要删除项目[%s]吗？", bean.name))
                    .setPositiveButton("确定") { dialogInterface, i ->
                        // 更新数据库
                        val rst = mDao.delById(bean.id)
                        LogUtil.v("删除项目 " + bean.id + ", rst " + rst)
                        mAdapter.del(pos)
                        dialogInterface.dismiss()

                        // 更新项目
                        val updateLD: MutableLiveData<Int> =
                            ViewModelProviders.of(this@ItemsActivity)
                                .get(ItemsVM::class.java).update
                        updateLD.postValue(CustomersVM.DEL)
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        })
        val query = mDao.query()
        if (CommonUtil.isNotEmpty(query)) {
            mAdapter.setData(query)
        } else {
            LogUtil.i("no data")
        }
        val cLayout: CoordinatorLayout.LayoutParams =
            fab_management_add.getLayoutParams() as CoordinatorLayout.LayoutParams
        val myBehavior = HideButtonBehavior()
        cLayout.setBehavior(myBehavior)

        fab_management_add.setOnClickListener { view ->
            when (view.id) {
                R.id.fab_management_add -> skipIntent(ItemDetailActivity::class.java)
                else -> {
                }
            }
        }
    }

}