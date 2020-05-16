package com.major.beauty.bean

import com.litesuits.orm.db.annotation.Table

/**
 * @desc: 产品
 * @author: Major
 * @since: 2019/6/7 23:20
 */
@Table("product")
data class Product(var name: String) : Base() {
    // name 产品名称

    constructor() : this("")

    var instruction: String? = null // 说明
    var price = 0.0
    var comment: String? = null
    var unit: String? = null // 单位
    var isDel = 0

}