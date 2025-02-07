package com.picpay.remote

import retrofit2.Call
import retrofit2.http.GET


interface ServiceProvider {

    @GET("users")
    fun getUsers(): Call<List<UserRemote>>
}