package com.bingo.linglong.interceptor

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import com.bingo.linglong.exception.AuthException
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class SaTokenConfigure : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaInterceptor() {
            try {
                StpUtil.checkLogin()
            } catch (e: NotLoginException) {
                throw AuthException.notLogin(e.message, e)
            }
        })
            .addPathPatterns("/**")
            .excludePathPatterns("/system/auth/login", "/openapi.yml")
    }
}