package com.major.beauty.bean

import com.litesuits.orm.db.annotation.Mapping
import com.litesuits.orm.db.annotation.NotNull
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.Relation

/**
 * @desc: 项目
 * @author: Major
 * @since: 2019/6/7 23:20
 */
@Table("item")
data class Item(var operator: String) : Base() {
    // operator 最后操作者

    constructor() : this("")

    @NotNull
    var name: String? = null // 项目名称

    @Mapping(Relation.OneToMany)
    var productCounts: MutableList<ProductCount>? = null // 包含的产品及其数量

    var createTime: Long = System.currentTimeMillis() // 创建时间
    var isDel = 0
    var comment: String? = null


}