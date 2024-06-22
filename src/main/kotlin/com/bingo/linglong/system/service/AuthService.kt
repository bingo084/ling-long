package com.bingo.linglong.system.service

import cn.dev33.satoken.stp.SaTokenInfo
import cn.dev33.satoken.stp.StpUtil
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
    fun login(@RequestBody req: LoginReq): SaTokenInfo {
        val user = userRepo.findByUsername(req.username)
        when (user?.state) {
            UserState.INACTIVE, null -> throw RuntimeException("用户名或密码错误")
            UserState.BANNED -> throw RuntimeException("您的账户已被禁用，请联系管理员")
            UserState.ACTIVE -> {}
        }
        if (user.password != req.password) {
            throw RuntimeException("用户名或密码错误")
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
    fun unban(@PathVariable userId: Long) {
        userRepo.save(new(User::class).by {
            id = userId
            state = UserState.ACTIVE
        })
    }
}