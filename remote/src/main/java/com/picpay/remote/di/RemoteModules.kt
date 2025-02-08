package com.picpay.remote.di

import com.google.gson.GsonBuilder
import com.picpay.remote.ServiceProvider
import com.picpay.remote.PicPayServiceRemoteEndpoints
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {

    single { GsonBuilder().create() }

    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(PicPayServiceRemoteEndpoints.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(ServiceProvider::class.java)
    }
}