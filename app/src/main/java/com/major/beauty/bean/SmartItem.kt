package com.major.beauty.bean

/**
 * Desc: md 自定义输入控件
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.bean
 * ProjectName: Beauty
 * Date: 2019/8/15 16:47
 */
data class SmartItem(var type: Int) {

    var title: String? = null // 标题
    var subTitle: String? = null // 二级标题
    var error: String? = null // 录入错误显示
    var data: String? = null // 单条数据
    var isEditable = false // 是否可编辑
    var spinnerShow: MutableList<String?>? = null
    var datas: MutableList<SmartItem?>? = null // TODO 子控件

    companion object {
        const val ITEM_DEFAULT = 0
        const val ITEM_EDIT = 1
        const val ITEM_SPINNER = 2
        const val ITEM_TIMMER = 3
        const val ITEM_GROUP = 4
    }

}