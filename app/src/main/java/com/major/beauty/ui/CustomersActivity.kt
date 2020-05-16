package com.major.beauty.ui

import android.content.Intent
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.adapter.CustomerAdapter
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.BaseAdapter
import com.major.beauty.base.Constant
import com.major.beauty.bean.Customer
import com.major.beauty.dao.CustomerDao
import com.major.beauty.ui.behavior.HideButtonBehavior
import com.major.beauty.ui.decoration.SpaceDecoration
import com.major.beauty.ui.vm.CustomersVM
import kotlinx.android.synthetic.main.act_customers.*

/**
 * @desc: 客户列表
 * @author: Major
 * @since: 2019/6/3 21:03
 */
class CustomersActivity : BaseActivity() {

    private val mAdapter: CustomerAdapter = CustomerAdapter(this)

    private val mDao: CustomerDao = CustomerDao()

    override fun getRootView(): Int = R.layout.act_customers

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("顾客管理")
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        rv_management.layoutManager = manager
        val itemDecoration = SpaceDecoration(14)
        rv_management.addItemDecoration(itemDecoration)
        rv_management.adapter = mAdapter
        mAdapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<Customer> {
            override fun onItemClick(
                pos: Int,
                item: Customer,
                view: View?
            ) {
                // 转场动画
                animateActivity(item.id, view)
            }

            override fun onItemLongClick(
                pos: Int,
                bean: Customer,
                view: View?
            ) {
                AlertDialog.Builder(this@CustomersActivity)
                    .setTitle("提示")
                    .setMessage(String.format("\n要删除用户[%s]吗？", bean.name))
                    .setPositiveButton("确定") { dialogInterface, i ->
                        // 更新数据库
                        val rst = mDao.delById(bean.id)
                        LogUtil.v("删除用户 " + bean.id + ", rst " + rst)
                        mAdapter.del(pos)
                        dialogInterface.dismiss()

                        // 更新顾客数量
                        val updateLD: MutableLiveData<Int> =
                            ViewModelProviders.of(this@CustomersActivity)
                                .get(CustomersVM::class.java).update
                        updateLD.postValue(CustomersVM.DEL)
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        })
        val customers = mDao.query()
        if (CommonUtil.isNotEmpty(customers)) {
            mAdapter.setData(customers)
        } else {
            LogUtil.i("no data")
        }
        val cLayout = fab_management_add.layoutParams as CoordinatorLayout.LayoutParams
        val myBehavior = HideButtonBehavior()
        cLayout.behavior = myBehavior
        ViewModelProviders.of(this).get(CustomersVM::class.java).update
            .observe(this, Observer { integer ->
                if (integer == CustomersVM.ADD) {
                    ToastUtil.showShort("收到更新")
                    val customers1 = mDao.query()
                    if (CommonUtil.isNotEmpty(customers1)) {
                        mAdapter.setData(customers1)
                    } else {
                        LogUtil.i("no data")
                    }
                }
            })

        fab_management_add.setOnClickListener { view ->
            when (view.id) {
                R.id.fab_management_add -> skipIntent(CustomerDetailActivity::class.java)
                else -> {
                }
            }
        }
    }

    fun animateActivity(cid: Long, appIcon: View?) {
        val intent = Intent(this, CustomerDetailActivity::class.java)
        intent.putExtra(Constant.Companion.EXTRA_CID, cid)

        val args: Array<androidx.core.util.Pair<View, String>> =
            arrayOf<androidx.core.util.Pair<View, String>>(
                androidx.core.util.Pair.create(fab_management_add, "fab"),
                androidx.core.util.Pair.create(appIcon, "appIcon")
            )
        val transitionActivityOptions: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, *args

            )
        startActivity(intent, transitionActivityOptions.toBundle())
    }
}