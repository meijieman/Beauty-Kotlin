package com.major.beauty.ui

import com.major.beauty.R
import com.major.beauty.base.BaseActivity
import kotlinx.android.synthetic.main.act_login.*

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.ui
 * ProjectName: Beauty
 * Date: 2019/6/5 9:19
 */
class LoginActivity : BaseActivity() {

    override fun getRootView(): Int = R.layout.act_login

    override fun init() {
        animation_view.playAnimation()
    }
}