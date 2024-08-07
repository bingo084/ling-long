package com.bingo.linglong.system.entity

import com.bingo.linglong.common.BaseEntity
import com.bingo.linglong.system.enums.UserState
import org.babyfish.jimmer.Formula
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * 用户
 *
 * @author bingo
 */
@Entity
@Table(name = "sys_user")
interface User : BaseEntity {
    /** 用户名 */
    val username: String

    /** 邮箱 */
    val email: String?

    /** 手机号 */
    val phone: String?

    /** 密码 */
    val password: String

    /** 真实姓名 */
    val realName: String?

    /** 昵称 */
    val nickname: String?

    /** 头像 */
    val avatar: String?

    /** 部门 */
    @ManyToOne
    val dept: Dept?

    /** 状态 */
    val state: UserState

    /** 角色 */
    @ManyToMany
    val roles: List<Role>

    /** 权限 */
    @Formula(dependencies = ["roles.menus.permission"])
    val permissions: List<String>
        get() = roles.flatMap { it.menus }.mapNotNull { it.permission }.distinct()
}