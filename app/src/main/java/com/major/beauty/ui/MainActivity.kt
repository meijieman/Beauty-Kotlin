package com.major.beauty.ui

import android.Manifest
import android.os.Environment
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.litesuits.orm.LiteOrm
import com.litesuits.orm.db.DataBaseConfig
import com.major.base.log.LogUtil
import com.major.beauty.R
import com.major.beauty.base.App
import com.major.beauty.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : BaseActivity() {
    val mNavView: BottomNavigationView by lazy { nav_view }

    private var mDailyFgt: DailyFgt? = null
    private var mAnalyzeFgt: AnalyzeFgt? = null
    private var mManagementFgt: ManagementFgt? = null

    override fun getRootView(): Int = R.layout.activity_main

    override fun init() {
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = getSupportActionBar()

        actionBar?.run {
//        actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        mNavView.setOnNavigationItemSelectedListener { item: MenuItem ->
            onNavigationItemSelected(item)
        }
        mNavView.setSelectedItemId(R.id.navigation_home)

//        skipIntent(CustomersActivity.class);
//        skipIntent(CustomerDetailActivity.class);
//        skipIntent(LoginActivity.class);
//        findViewById(R.id.navigation_notifications).performClick();
//        skipIntent(ItemDetailActivity.class); // 自定义控件
        requestPerms()
    }

    @AfterPermissionGranted(100)
    private fun requestPerms() {
        LogUtil.i("requestPerms")
        val perms: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // kt 调用 java 的可变参数，需要在前面加上*
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // 初始化数据库
            LogUtil.i("requestPerms 111")
            val path = (Environment.getExternalStorageDirectory().absolutePath
                    + File.separator + "beauty" + File.separator)
            val config = DataBaseConfig(this, path + "mei_beauty.db")
            config.dbVersion = 1
            //        mLiteOrm = LiteOrm.newSingleInstance(config);
            // 有级联操作，需要使用这个
            val mLiteOrm: LiteOrm = LiteOrm.newCascadeInstance(config)
            mLiteOrm.setDebugged(true)
            App.liteOrm = mLiteOrm
            LogUtil.i("requestPerms 222 $config")
        } else {
            LogUtil.i("requestPerms 333")
            //    public static void requestPermissions(
            //            @NonNull Activity host, @NonNull String rationale,
            //            int requestCode, @Size(min = 1) @NonNull String... perms)
            EasyPermissions.requestPermissions(this, "本应用需要获取写外存的权限，请允许！", 100, *perms)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (mDailyFgt != null) {
            transaction.hide(mDailyFgt!!)
        }
        if (mAnalyzeFgt != null) {
            transaction.hide(mAnalyzeFgt!!)
        }
        if (mManagementFgt != null) {
            transaction.hide(mManagementFgt!!)
        }
        when (item.itemId) {
            R.id.navigation_home -> {
                if (mDailyFgt == null) {
                    mDailyFgt = DailyFgt()
                    transaction.add(R.id.fl_container, mDailyFgt!!)
                }
                transaction.show(mDailyFgt!!)
            }
            R.id.navigation_dashboard -> {
                if (mManagementFgt == null) {
                    mManagementFgt = ManagementFgt()
                    transaction.add(R.id.fl_container, mManagementFgt!!)
                }
                transaction.show(mManagementFgt!!)
            }
            R.id.navigation_notifications -> {
                if (mAnalyzeFgt == null) {
                    mAnalyzeFgt = AnalyzeFgt()
                    transaction.add(R.id.fl_container, mAnalyzeFgt!!)
                }
                transaction.show(mAnalyzeFgt!!)
            }
        }
        transaction.commit()
        return true
    }
}