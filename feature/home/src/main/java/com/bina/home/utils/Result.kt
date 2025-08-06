package com.bina.home.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: UserError): Result<Nothing>()
}