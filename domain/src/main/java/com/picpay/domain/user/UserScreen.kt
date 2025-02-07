package com.picpay.domain.user

import com.picpay.domain.screen.ScreenStatus
import java.io.Serializable


data class UserScreen(
    val userList: List<User>? = null,
    val status: ScreenStatus = ScreenStatus.Loading,
) : Serializable
