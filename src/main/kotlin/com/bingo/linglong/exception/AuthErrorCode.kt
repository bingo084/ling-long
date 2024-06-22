package com.bingo.linglong.exception

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class AuthErrorCode {
    USERNAME_OR_PASSWORD_ERROR,
    ACCOUNT_BANNED,
    NOT_LOGIN
}