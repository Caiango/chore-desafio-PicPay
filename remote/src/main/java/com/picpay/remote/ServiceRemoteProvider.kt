package com.picpay.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceRemoteProvider(private val service: ServiceProvider) {
    fun getUsers(
        onSuccessCallback: (response: List<UserRemote>) -> Unit,
        onFailureCallback: (t: Throwable) -> Unit
    ) {
        service.getUsers()
            .enqueue(object : Callback<List<UserRemote>> {
                override fun onFailure(call: Call<List<UserRemote>>, t: Throwable) {
                    onFailureCallback(t)
                }

                override fun onResponse(
                    call: Call<List<UserRemote>>,
                    response: Response<List<UserRemote>>
                ) {
                    response.body()?.let {
                        onSuccessCallback(it)
                    }
                }
            })
    }
}