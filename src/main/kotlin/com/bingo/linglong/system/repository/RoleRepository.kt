package com.bingo.linglong.system.repository

import com.bingo.linglong.system.entity.Role
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher

interface RoleRepository : KRepository<Role, Long> {
    fun findPage(index: Int, size: Int, fetcher: Fetcher<Role>): Page<Role> =
        sql.createQuery(Role::class) {
            select(table.fetch(fetcher))
        }.fetchPage(index, size)
}