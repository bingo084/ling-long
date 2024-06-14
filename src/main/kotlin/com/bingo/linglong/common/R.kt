package com.bingo.linglong.common

/**
 * 通用返回结果类
 *
 * @author bingo
 */
data class R<T>(val success: Boolean, val message: String, val data: T?) {
    companion object {
        fun <T> ok(data: T?): R<T> {
            return R(true, "", data)
        }

        fun fail(message: String): R<Nothing> {
            return R(false, message, null)
        }
    }
}