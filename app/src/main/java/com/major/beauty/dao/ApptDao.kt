package com.major.beauty.dao

import com.major.beauty.bean.Appointment

/**
 * @desc: TODO
 * @author: Major
 * @since: 2019/11/2 8:46
 */
class ApptDao : BaseDao<Appointment>() {

    override fun query(): ArrayList<Appointment>? {
        return liteOrm.query(Appointment::class.java)
    }
}