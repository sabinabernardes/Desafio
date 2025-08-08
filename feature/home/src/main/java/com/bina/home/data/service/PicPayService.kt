package com.bina.home.data.service

import com.bina.home.data.model.UserDto
import retrofit2.http.GET

internal interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}