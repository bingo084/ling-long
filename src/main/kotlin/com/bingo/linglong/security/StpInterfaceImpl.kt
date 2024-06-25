package com.bingo.linglong.security

import cn.dev33.satoken.stp.StpInterface
import com.bingo.linglong.system.entity.User
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.repository.UserRepository
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.stereotype.Component

@Component
class StpInterfaceImpl(val userRepository: UserRepository) : StpInterface {
    override fun getPermissionList(loginId: Any?, loginType: String?): MutableList<String> {
        val user = userRepository.findById(loginId.toString().toLong(), PERMISSION_USER)
        return user.map { it.permissions.toMutableList() }.orElse(mutableListOf())
    }

    override fun getRoleList(loginId: Any?, loginType: String?): MutableList<String> {
        val user = userRepository.findById(loginId.toString().toLong(), PERMISSION_USER)
        return user.map { it -> it.roles.stream().map { it.name }.toList() }.orElse(mutableListOf())
    }

    companion object {
        val PERMISSION_USER = newFetcher(User::class).by {
            roles {
                name()
                menus { permission() }
            }
            permissions()
        }
    }
}