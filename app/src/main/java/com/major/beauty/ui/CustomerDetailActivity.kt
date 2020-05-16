package com.major.beauty.ui

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.major.base.log.LogUtil
import com.major.base.util.CommonUtil
import com.major.base.util.KeyboardUtil
import com.major.beauty.R
import com.major.beauty.base.App
import com.major.beauty.base.BaseActivity
import com.major.beauty.base.Constant
import com.major.beauty.bean.Customer
import com.major.beauty.dao.CustomerDao
import com.major.beauty.ui.vm.CustomersVM
import com.major.beauty.util.setViewsListener
import kotlinx.android.synthetic.main.act_customer_detail.*
import java.util.*

/**
 * @desc: 客户详细资料及修改界面
 * @author: Major
 * @since: 2019/6/3 21:03
 */
class CustomerDetailActivity : BaseActivity() {
    // R.id.tet_name, R.id.tet_phone,             R.id.tet_sex, R.id.tet_height,
    //            R.id.tet_weight, R.id.tiet_birthday,
    //            R.id.tet_lunar_birthday, R.id.tet_wedding_day,
    //            R.id.tet_skin_type, R.id.tet_nursing_needs,
    //            R.id.tet_available_time,
    // R.id.tet_comment
    val mTies: List<TextInputEditText> by lazy { listOf(tet_name, tet_phone, tet_comment) }

    private var mIsEditMode = false // 是否是编辑状态 = false
    private var calendar: Calendar = Calendar.getInstance()
    private var mCustomer: Customer? = null
    private val mDao: CustomerDao = CustomerDao()

    override fun getRootView(): Int = R.layout.act_customer_detail

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = getSupportActionBar()
        actionBar?.run {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val cid: Long = intent.getLongExtra(Constant.Companion.EXTRA_CID, -1L)
        if (cid == -1L) {
            // 新增
            mIsEditMode = true
            editable(true)
            ctl_customer_detail.setTitle("个人档案")
        } else {
            mCustomer = mDao.queryById(cid, Customer::class.java)
            if (mCustomer == null) {
                LogUtil.e("query error $cid")
                return
            }
            ctl_customer_detail.setTitle(mCustomer!!.name + "个人档案")
            mTies[0].setText(mCustomer!!.name)
            mTies[1].setText(mCustomer!!.phone)
            //            mTies.get(2).setText(mCustomer.getSex());
//            mTies.get(3).setText(mCustomer.getHeight() + "cm");
//            mTies.get(4).setText(mCustomer.getWeight() + "kg");
//            mTies.get(5).setText(mCustomer.getBirthday());
//            mTies.get(6).setText(mCustomer.getLunarBirthday());
//            mTies.get(7).setText(mCustomer.getWeddingDay());
//            mTies.get(8).setText(mCustomer.getSkinType());
//            mTies.get(9).setText(mCustomer.getNursingNeeds());
//            mTies.get(10).setText(mCustomer.getAvailableTime());
            mTies[2].setText(mCustomer!!.comment)
            editable(false)
        }
    }

    override fun onBackPressed() {
        if (mIsEditMode) {
            // TODO: 2019/6/7 如果有修改未保存,提示
            mIsEditMode = false
            return
        }
        super.onBackPressed()
    }

    private fun editable(b: Boolean) {
        for (mTY in mTies) {
            mTY.setEnabled(b)
            if (b) {
                // 编辑模式
                mTY.setTextColor(getResources().getColor(R.color.colorAccent))
                fab_customer_detail.setImageResource(R.drawable.ic_done)
            } else {
                // 查看模式
                mTY.setTextColor(getResources().getColor(R.color.primary_text))
                mTY.setHighlightColor(Color.GREEN)
                fab_customer_detail.setImageResource(R.drawable.ic_edit)
            }
        }
        setViewsListener(listener, fab_customer_detail, mb_cost)
    }

    var listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.fab_customer_detail -> {
                if (mIsEditMode) {
                    // 提交数据
                    KeyboardUtil.hideKeyboard(this, view)
                    var tip = "修改成功"
                    if (mCustomer == null) {
                        mCustomer = Customer(App.instance.mAvatar.name)
                        tip = "添加成功"
                    }
                    val name: String = mTies[0].getText().toString()
                    if (CommonUtil.isEmpty(name)) {
                        mTies[0].setError("姓名不能为空")
                        mTies[0].requestFocus()
                        return@OnClickListener
                    }
                    mCustomer?.name = name
                    val phone: String = mTies[1].getText().toString()
                    if (CommonUtil.isEmpty(phone)) {
                        mTies[1].setError("电话不能为空")
                        mTies[1].requestFocus()
                        return@OnClickListener
                    }
                    mCustomer?.phone = phone
                    val update = mDao.insertOrUpdate(mCustomer!!)
                    LogUtil.i("update $update")
                    if (update != -1L) {
                        Snackbar.make(view, tip, Snackbar.LENGTH_SHORT).show()
                        editable(false)

                        // 如果是新增，通知其他界面更新
                        val updateLD: MutableLiveData<Int> =
                            ViewModelProviders.of(this).get(CustomersVM::class.java).update
                        updateLD.postValue(CustomersVM.ADD)
                    }
                } else {
                    // 设置为编辑状态
                    editable(true)
                }
                mIsEditMode = !mIsEditMode
            }
            R.id.mb_cost -> {
                // 充值，消费详细记录
                Snackbar.make(view, "消费详细记录", Snackbar.LENGTH_SHORT).show()
                skipIntent(CostRecordActivity::class.java)
            }
        }
    }
}