package com.bingo.linglong.system.repository

import com.bingo.linglong.system.entity.Dept
import com.bingo.linglong.system.entity.parentId
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.ast.expression.isNull

interface DeptRepository : KRepository<Dept, Long> {
    fun findRoot(fetcher: Fetcher<Dept>): List<Dept> =
        sql.createQuery(Dept::class) {
            where(table.parentId.isNull())
            select(table.fetch(fetcher))
        }.execute();
}