package com.picpay.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.picpay.domain.user.User
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserRemote(
    @SerializedName("img")
    val img: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?
) : Parcelable

fun UserRemote.toDomain(): User =
    User(
        img = img.orEmpty(),
        name = name.orEmpty(),
        username = username.orEmpty(),
        id = id ?: 0
    )