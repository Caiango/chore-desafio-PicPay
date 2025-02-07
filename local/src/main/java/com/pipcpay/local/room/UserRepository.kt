package com.pipcpay.local.room

import com.picpay.domain.user.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(users: List<User>) {
        users.forEach {
            userDao.insert(it)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}
