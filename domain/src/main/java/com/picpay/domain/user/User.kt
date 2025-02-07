package com.picpay.domain.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val id: Int,
    val img: String,
    val name: String,
    val username: String,
)
