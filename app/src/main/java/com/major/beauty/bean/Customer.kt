package com.major.beauty.bean

import com.litesuits.orm.db.annotation.NotNull
import com.litesuits.orm.db.annotation.Table

/**
 * Desc: 顾客资料
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.bean
 * ProjectName: Beauty
 * Date: 2019/6/3 16:37
 */
@Table("customer")
data class Customer(val operator: String, var name: String, var phone: String) : Base() {
    // 基本资料 operator  最近操作员工，name 姓名，phone 手机号

    constructor() : this("")
    constructor(operator: String) : this(operator, "", "")

    // 详细资料
    var sex: String? = null // 性别
    var height: Int = 0 // 身高（cm） = 0
    var weight: Float = 0f // 体重（kg） = 0f
    var company: String? = null // 职业, 工作单位
    var marrided = 0 // 0 未知，1 未婚，2 已婚 = 0
    var birthday: String? = null
    var lunarBirthday: String? = null // 农历生日
    var weddingDay: String? = null // 结婚纪念日
    var skinType: String? = null // 皮肤类型
    var nursingNeeds: String? = null // 调养需求
    var availableTime: String? = null // 最佳联系时间
    var comment: String? = null // 备注
    var iconUrl: String? = null // 头像 url

    @NotNull
    var createTime: Long = System.currentTimeMillis() // 记录创建时间
    var modifyTime: Long = 0 // 最近修改时间

    var isDel = 0 // 是否已删除 0 未删除，1 删除 默认 0 = 0

}