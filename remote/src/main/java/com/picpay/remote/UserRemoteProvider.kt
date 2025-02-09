package com.picpay.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.picpay.domain.providers.RemoteProvider
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.UserScreen

class UserRemoteProvider(
    private val service: ServiceProvider,
    private val context: Context,
) : RemoteProvider {

    override suspend fun getUsers(): UserScreen {
        if (!isNetworkAvailable(context)) {
            return UserScreen(
                userList = null,
                status = ScreenStatus.Error(Throwable(GENERIC_ERROR))
            )
        }

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

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    companion object {
        private const val GENERIC_ERROR = "Houve um erro por aqui"
    }

}