package com.picpay.desafio.android.di

import com.picpay.desafio.android.MainViewModel
import com.picpay.domain.providers.LocalProvider
import com.picpay.domain.providers.RemoteProvider
import com.picpay.remote.UserRemoteProvider
import com.pipcpay.local.room.UserLocalProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MainViewModel(
            local = get(),
            remote = get()
        )
    }
}

val repositoryModule = module {
    single<LocalProvider> { UserLocalProvider(get()) }
}

val serviceModule = module {
    single<RemoteProvider> { UserRemoteProvider(get(), get()) }
}