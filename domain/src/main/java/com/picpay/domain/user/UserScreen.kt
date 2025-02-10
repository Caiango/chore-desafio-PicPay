package com.picpay.domain.user

import android.os.Parcelable
import com.picpay.domain.screen.ScreenStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserScreen(
    val userList: List<User>? = null,
    val status: ScreenStatus = ScreenStatus.Loading,
) : Parcelable
