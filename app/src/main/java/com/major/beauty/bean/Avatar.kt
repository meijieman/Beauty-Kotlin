package com.major.beauty.bean

/**
 * @desc: app 用户角色
 * @author: Major
 * @since: 2019/9/8 17:19
 */
data class Avatar(var name: String) {
    // name 名称

    constructor():this("")

    var password: String? = null // 登录密码
    var grade = NO_GRADE // 级别
    var createTime: Long = 0

    companion object {
        private const val NO_GRADE = -1
    }

    override fun toString(): String {
        return "Avatar(name='$name', password=$password, grade=$grade, createTime=$createTime)"
    }

}