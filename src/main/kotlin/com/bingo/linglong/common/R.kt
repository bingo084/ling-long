package com.bingo.linglong.common

/**
 * 通用返回结果类
 *
 * @author bingo
 */
data class R<T>(val success: Boolean, val message: String, val data: T?) {
    companion object {
        fun ok(message: String): R<Unit> {
            return R(true, message, null)
        }

        fun <T> ok(message: String, data: T?): R<T> {
            return R(true, message, data)
        }

        fun fail(message: String): R<Unit> {
            return R(false, message, null)
        }
    }
}