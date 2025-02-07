package com.picpay.domain.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ScreenStatus : Parcelable {

    data object Ready : ScreenStatus()

    data object Loading : ScreenStatus()

    data class Error(val t: Throwable? = null) : ScreenStatus()

    val isReady get() = this is Ready

    val isLoading get() = this is Loading

    val isError get() = this is Error
}