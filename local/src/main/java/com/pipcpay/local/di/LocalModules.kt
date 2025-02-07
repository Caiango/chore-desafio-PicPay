package com.pipcpay.local.di

import androidx.room.Room
import com.pipcpay.local.room.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    single { get<UserDatabase>().userDao() }
}