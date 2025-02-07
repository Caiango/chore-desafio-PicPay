package com.pipcpay.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.domain.user.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
