package com.bina.home.utils

import com.bina.home.data.service.PicPayService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

internal class RetrofitService {
    companion object {
        val service: PicPayService

        init {
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            service = retrofit.create(PicPayService::class.java)
        }
    }
}