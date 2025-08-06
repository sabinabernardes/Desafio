package com.bina.home.utils

sealed class UserError {
    object Network : UserError()
    object NotFound : UserError()
    object Unauthorized : UserError()
    object Unknown : UserError()
    data class Custom(val message: String) : UserError()
}