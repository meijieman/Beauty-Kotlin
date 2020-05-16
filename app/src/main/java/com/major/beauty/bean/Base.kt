package com.major.beauty.bean

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.enums.AssignType

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/8 22:10
 */
abstract class Base {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var id: Long = 0

}