package com.major.beauty.bean

import com.litesuits.orm.db.annotation.NotNull
import com.litesuits.orm.db.annotation.Table

/**
 * Desc: 消费记录
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.bean
 * ProjectName: Beauty
 * Date: 2019/10/28 10:12
 */
@Table("cost_record")
data class CostRecord(var cid: Long) : Base() {
    // cid  顾客 id

    constructor():this(0)

    @NotNull
    var createTime: Long = System.currentTimeMillis() // 消费日期

    var name: String? = null // 顾客姓名
    var phone: String? = null // 顾客手机号
    var products: MutableList<Product>? = null // 产品
    var items: MutableList<Item>? = null // 项目
    var duration = 0 // 耗时，单位分钟 = 0

    @NotNull
    var pay = 0.0 // 消费总金额 = 0.0

}