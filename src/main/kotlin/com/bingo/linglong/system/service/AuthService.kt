package com.bingo.linglong.system.service

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.stp.SaTokenInfo
import cn.dev33.satoken.stp.StpUtil
import com.bingo.linglong.exception.AuthException
import com.bingo.linglong.system.entity.User
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.enums.UserState
import com.bingo.linglong.system.repository.UserRepository
import com.bingo.linglong.system.vo.LoginReq
import org.babyfish.jimmer.kt.new
import org.springframework.web.bind.annotation.*

/**
 * 权限
 */
@RestController
@RequestMapping("/system/auth")
class AuthService(val userRepo: UserRepository) {
    /**
     * 登录
     */
    @PostMapping("/login")
    @Throws(AuthException.UsernameOrPasswordError::class, AuthException.AccountBanned::class)
    fun login(@RequestBody req: LoginReq): SaTokenInfo {
        val user = userRepo.findByUsername(req.username)
        when (user?.state) {
            UserState.INACTIVE -> throw AuthException.usernameOrPasswordError("User(${user.username}) is inactive")
            null -> throw AuthException.usernameOrPasswordError("User(${req.username}) not found")
            UserState.BANNED -> throw AuthException.accountBanned("User(${user.username}) is banned")
            UserState.ACTIVE -> {}
        }
        if (user.password != req.password) {
            throw AuthException.usernameOrPasswordError("User(${user.username}) password error")
        }
        StpUtil.login(user.id)
        return StpUtil.getTokenInfo()!!
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    fun logout() {
        StpUtil.logout()
    }

    /**
     * 封禁
     */
    @PostMapping("/ban/{userId}")
    @SaCheckPermission("system:auth:ban")
    fun ban(@PathVariable userId: Long) {
        userRepo.save(new(User::class).by {
            id = userId
            state = UserState.BANNED
        })
        StpUtil.kickout(userId)
    }

    /**
     * 解封
     */
    @PostMapping("/unban/{userId}")
    @SaCheckPermission("system:auth:unban")
    fun unban(@PathVariable userId: Long) {
        userRepo.save(new(User::class).by {
            id = userId
            state = UserState.ACTIVE
        })
    }
}