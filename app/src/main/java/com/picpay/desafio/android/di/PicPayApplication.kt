package com.picpay.desafio.android.di

import android.app.Application
import com.picpay.remote.di.remoteModule
import com.pipcpay.local.di.localModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PicPayApplication)
            modules(
                remoteModule,
                presentationModule,
                localModule,
            )
        }
    }
}