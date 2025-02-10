package com.picpay.desafio.android.di

import com.picpay.desafio.android.MainViewModel
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
