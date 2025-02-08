package com.pipcpay.local.room

import com.picpay.domain.providers.LocalProvider
import com.picpay.domain.user.User

class UserLocalProvider(private val userDao: UserDao) : LocalProvider {

    override suspend fun insertUsers(users: List<User>) {
        users.forEach {
            userDao.insert(it)
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}
