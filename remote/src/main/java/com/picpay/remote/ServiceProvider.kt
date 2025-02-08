package com.picpay.remote

import retrofit2.Response
import retrofit2.http.GET


interface ServiceProvider {

    @GET("users")
    suspend fun getUsers(): Response<List<UserRemote>>
}