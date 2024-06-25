package com.bingo.linglong.system.entity

import com.bingo.linglong.common.BaseEntity
import com.bingo.linglong.system.enums.MenuType
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany

/**
 * 菜单
 *
 * @author bingo
 */
@Entity
interface Menu : BaseEntity {
    /** 名称 */
    val name: String

    /** 权限 */
    val permission: String?

    /** 图标 */
    val icon: String?

    /** 路由 */
    val route: String?

    /** 排序 */
    val sort: Int

    /** 是否隐藏 */
    val hidden: Boolean

    /** 是否启用 */
    val enabled: Boolean

    /** 类型 */
    val type: MenuType

    /** 父菜单 */
    @ManyToOne
    val parent: Menu?

    /** 子菜单 */
    @OneToMany(mappedBy = "parent")
    val children: List<Menu>
}