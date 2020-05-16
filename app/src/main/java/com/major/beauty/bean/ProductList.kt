package com.major.beauty.bean

import com.litesuits.orm.db.annotation.Table

/**
 * @desc: 产品库存清单
 * @author: Major
 * @since: 2019/6/7 23:30
 */
@Table("product_list")
data class ProductList(var productName: String) : Base() {
    // productName 对应 Product 的 name

    constructor() : this("")

    var count = 0 // 相同产品的数量
    var productionDate: String? = null // 生产日期
    var expiryDate: String? = null // 过期时间

}