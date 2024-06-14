package com.bingo.linglong.common

import com.bingo.linglong.system.entity.User
import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime

@MappedSuperclass
interface BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
    val createdAt: LocalDateTime
    val modifiedAt: LocalDateTime

    @ManyToOne
    val createdBy: User

    @ManyToOne
    val modifiedBy: User
}