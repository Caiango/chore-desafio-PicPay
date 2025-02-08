package com.picpay.domain.providers

import com.picpay.domain.user.UserScreen

interface RemoteProvider {
    suspend fun getUsers(): UserScreen
}