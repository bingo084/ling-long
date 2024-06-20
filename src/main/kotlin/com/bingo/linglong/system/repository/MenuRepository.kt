package com.bingo.linglong.system.repository

import com.bingo.linglong.system.entity.Menu
import com.bingo.linglong.system.entity.parentId
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.babyfish.jimmer.sql.kt.ast.expression.isNull

interface MenuRepository : KRepository<Menu, Long> {
    fun findRoot(fetcher: Fetcher<Menu>): List<Menu> =
        sql.createQuery(Menu::class) {
            where(table.parentId.isNull())
            select(table.fetch(fetcher))
        }.execute();
}