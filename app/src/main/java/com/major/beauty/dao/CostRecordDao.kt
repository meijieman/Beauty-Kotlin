package com.major.beauty.dao

import com.litesuits.orm.db.assit.QueryBuilder
import com.major.base.log.LogUtil
import com.major.beauty.bean.CostRecord

/**
 * Desc: TODO
 *
 *
 * Author: meijie
 * PackageName: com.major.beauty.dao
 * ProjectName: Beauty
 * Date: 2019/10/28 10:47
 */
class CostRecordDao : BaseDao<CostRecord>() {

    override fun query(): ArrayList<CostRecord> {
        val where = QueryBuilder(CostRecord::class.java).where("isDel=?", 0)
        LogUtil.w("statement ${where.createStatement().toString()}")
        return liteOrm.query(where)
    }
}