package com.bingo.linglong.system.repository

import com.bingo.linglong.system.entity.User
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher

interface UserRepository : KRepository<User, Long> {
    fun findPage(index: Int, size: Int, fetcher: Fetcher<User>): Page<User> =
        sql.createQuery(User::class) {
            select(table.fetch(fetcher))
        }.fetchPage(index, size)
}