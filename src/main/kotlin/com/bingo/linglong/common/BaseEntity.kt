package com.bingo.linglong.common

import com.bingo.linglong.system.entity.User
import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime

@MappedSuperclass
interface BaseEntity {
    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /** 创建时间 */
    val createTime: LocalDateTime

    /** 更新时间 */
    val updateTime: LocalDateTime

    /** 创建人 */
    @ManyToOne
    val creator: User

    /** 更新人 */
    @ManyToOne
    val updater: User
}