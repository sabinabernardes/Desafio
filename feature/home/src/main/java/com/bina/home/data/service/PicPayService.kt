package com.bina.home.data.service

import com.bina.home.data.model.User
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<User>
}