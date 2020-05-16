package com.major.beauty.bean

import com.litesuits.orm.db.annotation.Table

/**
 * @desc: 预约 appointment 英文缩写 appt
 * @author: Major
 * @since: 2019/11/2 8:42
 */
@Table("appointment")
data class Appointment(var cid: Long, var name: String) : Base() {
    // cid 顾客id，name [Customer.name] 顾客姓名

    constructor() : this(0, "")

    /**
     * [Customer.phone]
     */
    lateinit var phone: String // 手机号
    var startTime: Long = 0 // 开始时间
    var endTime: Long = 0 // 结束时间
    var createTime: Long = 0 // 预约创建时间
    var comment: String? = null // 备注
    var expire = 0// 是否过期: 0 未过期， 1 已完成， 2 取消预约， 3 已过期 = 0

    override fun toString(): String {
        return "Appointment(id=$id, cid=$cid, name='$name', phone='$phone', startTime=$startTime, endTime=$endTime, createTime=$createTime, comment=$comment, expire=$expire)"
    }

}