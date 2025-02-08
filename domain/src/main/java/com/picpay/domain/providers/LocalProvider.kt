package com.picpay.domain.providers

import com.picpay.domain.user.User

interface LocalProvider {
    suspend fun insertUsers(users: List<User>)

    suspend fun getAllUsers(): List<User>
}