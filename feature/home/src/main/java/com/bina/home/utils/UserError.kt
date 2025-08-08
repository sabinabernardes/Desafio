package com.bina.home.utils

internal sealed class UserError {
    object Network : UserError()
    object NotFound : UserError()
    object Unauthorized : UserError()
    object Unknown : UserError()
    data class Custom(val message: String) : UserError()
}