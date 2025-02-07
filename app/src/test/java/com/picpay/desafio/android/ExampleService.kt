//package com.picpay.desafio.android
//
//import com.picpay.remote.ServiceRemoteProvider
//import com.picpay.remote.UserRemote
//
//class ExampleService(
//    private val service: ServiceRemoteProvider
//) {
//
//    fun example(): List<UserRemote> {
//        val users = service.getUsers().execute()
//
//        return users.body() ?: emptyList()
//    }
//}