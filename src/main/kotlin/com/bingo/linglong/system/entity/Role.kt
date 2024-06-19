package com.bingo.linglong.system.entity

import com.bingo.linglong.common.BaseEntity
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToMany

/**
 * 角色
 *
 * @author bingo
 */
@Entity
interface Role : BaseEntity {
    /** 名称 */
    val name: String

    /** 描述 */
    val description: String?

    /** 菜单 */
    @ManyToMany
    val menus: List<Menu>
}