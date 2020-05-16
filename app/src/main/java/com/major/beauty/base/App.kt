package com.major.beauty.base

import android.app.Application
import com.litesuits.orm.LiteOrm
import com.major.base.CommonConfig
import com.major.base.crash.CrashHandler
import com.major.beauty.bean.Avatar

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/2/17 7:54
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashHandler.getInstance().init(this, "crash", true)

        CommonConfig.Build()
            .setApplication(this)
            .setLogUtil("tag_beauty", true, false)
            .build()
        test()
    }

    lateinit var mAvatar: Avatar

    private fun test() {
        // 用户角色
        mAvatar = Avatar().apply {
            name = "major"
            grade = 1
        }

        // 创建用户数据

        // 创建产品数据

        // 创建项目数据
    }

    companion object {
        lateinit var instance: App
            private set

        lateinit var liteOrm: LiteOrm

    }
}