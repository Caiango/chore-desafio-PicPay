package com.picpay.domain.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val id: Int,
    val img: String,
    val name: String,
    val username: String,
) : Parcelable
