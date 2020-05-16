package com.major.beauty.dao

import com.litesuits.orm.LiteOrm
import com.major.beauty.base.App
import com.major.beauty.bean.Base

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/8 19:48
 */
abstract class BaseDao<T : Base> {

    abstract fun query(): ArrayList<T>?

    fun queryCount(): Long {
        val query = query()
        return (query?.size ?: 0).toLong()
    }

    fun queryById(id: Long, clazz: Class<T>): T? {
        return liteOrm.queryById(id, clazz)
    }

    fun insertOrUpdate(t: T): Long {
        return liteOrm.save(t)
    }

    companion object {
        var liteOrm: LiteOrm = App.liteOrm
    }
}