package com.bingo.linglong.system.enums

import org.babyfish.jimmer.sql.runtime.ScalarProvider
import org.postgresql.util.PGobject
import org.springframework.stereotype.Component

@Component
class MenuTypeScalarProvider : ScalarProvider<MenuType, PGobject> {
    override fun toScalar(sqlValue: PGobject): MenuType {
        return MenuType.valueOf(sqlValue.value!!)
    }

    override fun toSql(scalarValue: MenuType): PGobject {
        return PGobject().apply {
            type = "menu_type"
            value = scalarValue.name
        }
    }
}