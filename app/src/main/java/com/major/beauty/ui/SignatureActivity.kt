package com.major.beauty.ui

import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import kotlinx.android.synthetic.main.act_signature.*

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui
 * ProjectName: Beauty
 * Date: 2019/7/26 10:05
 */
class SignatureActivity : BaseActivity() {

    override fun getRootView(): Int = R.layout.act_signature

    override fun init() {

        btn_clear.setOnClickListener { view ->
            when (view.id) {
                R.id.btn_clear -> {
                    sv_signature.clear()
                    surfaceview.clean()
                    signature_pad.clear()
                }
                else -> {
                }
            }
        }
    }

}