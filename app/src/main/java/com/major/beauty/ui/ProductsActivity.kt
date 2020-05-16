package com.major.beauty.ui

import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.ToastUtil
import com.major.beauty.R
import com.major.beauty.adapter.ProductAdapter
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.BaseAdapter
import com.major.beauty.bean.Product
import com.major.beauty.dao.ProductDao
import com.major.beauty.dialog.ModifyProductDlg
import com.major.beauty.ui.behavior.HideButtonBehavior
import com.major.beauty.ui.decoration.SpaceDecoration
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.ui.vm.ProductsVM
import kotlinx.android.synthetic.main.act_products.*

/**
 * Desc: 产品列表界面
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui
 * ProjectName: Beauty
 * Date: 2019/6/4 12:45
 */
class ProductsActivity : BaseActivity() {

    private val mDao: ProductDao by lazy { ProductDao() }

    override fun getRootView(): Int = R.layout.act_products

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "产品档案"
        }

        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        rv_management.layoutManager = manager
        val itemDecoration = SpaceDecoration(14)
        rv_management.addItemDecoration(itemDecoration)

        val adapter = ProductAdapter(this)
        rv_management.setAdapter(adapter)
        ViewModelProviders.of(this).get(ProductsVM::class.java).update
            .observe(this, Observer { integer ->
                ToastUtil.showShort("收到更新")
                if (integer == CustomersVM.ADD) {
                    val product = mDao.query()
                    if (CommonUtil.isNotEmpty(product)) {
                        adapter.setData(product)
                    } else {
                        LogUtil.i("no data")
                    }
                }
            })

        adapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<Product> {
            override fun onItemClick(pos: Int, bean: Product, view: View?) {
                // 转场动画
//            animateActivity(item, view);
                val dialog = ModifyProductDlg(this@ProductsActivity, bean)
                dialog.show()
            }

            override fun onItemLongClick(pos: Int, bean: Product, view: View?) {
                AlertDialog.Builder(this@ProductsActivity)
                    .setTitle("提示")
                    .setMessage(String.format("\n要删除产品[%s]吗？", bean.name))
                    .setPositiveButton("确定") { dialogInterface, _ ->
                        // 更新数据库
                        val rst = mDao.delById(bean.id)
                        LogUtil.v("删除产品 " + bean.id + ", rst " + rst)
                        adapter.del(pos)
                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        })
        val query = mDao.query()
        if (CommonUtil.isNotEmpty(query)) {
            adapter.setData(query)
        }
        val cLayout: CoordinatorLayout.LayoutParams =
            fab_management_add.layoutParams as CoordinatorLayout.LayoutParams
        val myBehavior = HideButtonBehavior()
        cLayout.behavior = myBehavior

        fab_management_add.setOnClickListener { view ->
            when (view.id) {
                R.id.fab_management_add -> {
                    val dialog = ModifyProductDlg(this)
                    dialog.show()
                }
                else -> {
                }
            }
        }
    }

}