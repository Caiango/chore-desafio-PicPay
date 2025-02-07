package com.picpay.desafio.android.di

import com.picpay.desafio.android.presentation.MainViewModel
import com.picpay.remote.ServiceRemoteProvider
import com.pipcpay.local.room.UserRepository
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MainViewModel(
            userRepository = get(),
            service = get()
        )
    }
}

val repositoryModule = module {
    single { UserRepository(get()) }
}

val serviceModule = module {
    single { ServiceRemoteProvider(get()) }
}