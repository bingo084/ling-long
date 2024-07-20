package com.bingo.linglong.interceptor

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class SaTokenConfigure : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaInterceptor() { StpUtil.checkLogin() })
            .addPathPatterns("/**")
            .excludePathPatterns("/system/auth/login", "/openapi.yml", "/ts.zip")
    }
}