package com.major.beauty.bean

import com.litesuits.orm.db.annotation.Table

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/10 23:08
 */
@Table("product_count")
data class ProductCount(var pid: Long) : Base() {
    // pid Product 的 id

    constructor() : this(0)

    var productName: String? = null // Product 的 name
    var productUnit: String? = null // Product 的 单位
    var count = 0 // 数量 = 0

}