package com.major.beauty.dao

import com.litesuits.orm.db.assit.QueryBuilder
import com.major.base.log.LogUtil
import com.major.beauty.bean.Customer

/**
 * @desc: 顾客数据库操作
 * @author: Major
 * @since: 2019/9/8 16:31
 */
class CustomerDao : BaseDao<Customer>() {
    override fun query(): ArrayList<Customer> {
        val where: QueryBuilder<Customer> = QueryBuilder(Customer::class.java).where("isDel=?", 0)
        LogUtil.w("statement " + where.createStatement().toString());
        return liteOrm.query(where)
    }

    fun delById(cid: Long): Int {
        val customer: Customer? = BaseDao.Companion.liteOrm.queryById(cid, Customer::class.java)
        if (customer != null) {
            // 软删除
            customer.isDel = 1
            return BaseDao.Companion.liteOrm.update(customer)
        }
        return -1
    }

    fun queryByNameOrPhone(text: String?): MutableList<Customer> {
        // 参考 https://blog.csdn.net/lisiben/article/details/84466463
        val qb: QueryBuilder<Customer> =
            QueryBuilder(Customer::class.java).where("(name like '%'||?||'%'", text)
                .whereOr("phone like '%'||?||'%')", text)
                .whereAnd("isDel=?", 0)
        LogUtil.w("statement " + qb.createStatement().toString())
        return liteOrm.query(qb)
    }
}