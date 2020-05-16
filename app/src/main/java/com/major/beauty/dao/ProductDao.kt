package com.major.beauty.dao

import com.litesuits.orm.db.assit.QueryBuilder
import com.major.base.log.LogUtil
import com.major.beauty.bean.Product

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/9/8 22:44
 */
class ProductDao : BaseDao<Product>() {
    override fun query(): ArrayList<Product>? {
        val where: QueryBuilder<Product> = QueryBuilder(Product::class.java).where("isDel=?", 0)
        LogUtil.w("statement " + where.createStatement().toString())
        return BaseDao.Companion.liteOrm.query(where)
    }

    fun delById(id: Long): Int {
        val product: Product? = BaseDao.Companion.liteOrm.queryById(id, Product::class.java)
        if (product != null) {
            // 软删除
            product.isDel = 1
            return BaseDao.Companion.liteOrm.update(product)
        }
        return -1
    }
}