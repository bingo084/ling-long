package com.bingo.linglong.system.entity

import com.bingo.linglong.common.BaseEntity
import org.babyfish.jimmer.sql.*

/**
 * 部门
 *
 * @author bingo
 */
@Entity
interface Dept : BaseEntity {
    /** 名称 */
    val name: String

    /** 描述 */
    val description: String?

    /** 父部门 */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val parent: Dept?

    /** 子部门 */
    @OneToMany(mappedBy = "parent")
    val children: List<Dept>
}