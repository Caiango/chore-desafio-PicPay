package com.picpay.remote

import com.picpay.domain.providers.RemoteProvider
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.UserScreen

class UserRemoteProvider(private val service: ServiceProvider): RemoteProvider {
    override suspend fun getUsers(): UserScreen {
        val response = service.getUsers()

        return if (response.isSuccessful) {
            UserScreen(
                userList = response.body()?.map { it.toDomain() },
                status = ScreenStatus.Ready
            )
        } else {
            UserScreen(
                userList = null,
                status = ScreenStatus.Error(Throwable(message = response.message()))
            )
        }
    }
}