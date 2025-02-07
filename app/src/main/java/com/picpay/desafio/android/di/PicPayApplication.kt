package com.picpay.desafio.android.di

import android.app.Application
import com.picpay.remote.di.networkModule
import com.pipcpay.local.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PicPayApplication)
            modules(
                networkModule,
                presentationModule,
                databaseModule,
                repositoryModule,
                serviceModule
            )
        }
    }
}