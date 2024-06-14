package com.bingo.linglong.system.entity

import com.bingo.linglong.common.BaseEntity
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany

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
    val desc: String?

    /** 父部门 */
    @ManyToOne
    val parent: Dept?

    /** 子部门 */
    @OneToMany(mappedBy = "parent")
    val children: List<Dept>
}