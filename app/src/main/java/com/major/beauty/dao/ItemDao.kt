package com.major.beauty.dao

import com.litesuits.orm.db.assit.QueryBuilder
import com.major.base.log.LogUtil
import com.major.beauty.bean.Item
import java.util.*

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/8 22:44
 */
class ItemDao : BaseDao<Item>() {
    override fun query(): ArrayList<Item>? {
        val where: QueryBuilder<Item> =
            QueryBuilder(Item::class.java).where("isDel=?", 0)
        LogUtil.w("statement " + where.createStatement().toString())
        val query: ArrayList<Item>? = BaseDao.Companion.liteOrm.query(where)
        if (query != null) {
            for (item in query) {
                // TODO: 2019/9/9 级联查询
//                item.setProductCounts();
            }
        }
        return query
    }

    fun delById(id: Long): Int {
        val item: Item? = liteOrm.queryById(id, Item::class.java)
        if (item != null) {
            // 软删除
            item.isDel = 1

            return liteOrm.update(item)
        }
        return -1
    }
}