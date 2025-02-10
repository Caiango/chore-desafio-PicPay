package com.pipcpay.local.di

import androidx.room.Room
import com.picpay.domain.providers.LocalProvider
import com.pipcpay.local.room.UserDatabase
import com.pipcpay.local.room.UserLocalProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    single { get<UserDatabase>().userDao() }

    single<LocalProvider> { UserLocalProvider(get()) }
}
