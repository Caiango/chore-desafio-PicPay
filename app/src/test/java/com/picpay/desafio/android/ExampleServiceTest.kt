//package com.picpay.desafio.android
//
//import com.nhaarman.mockitokotlin2.mock
//import com.nhaarman.mockitokotlin2.whenever
//import com.picpay.remote.ServiceRemoteProvider
//import com.picpay.remote.UserRemote
//import junit.framework.Assert.assertEquals
//import org.junit.Test
//import retrofit2.Call
//import retrofit2.Response
//
//class ExampleServiceTest {
//
//    private val api = mock<ServiceRemoteProvider>()
//
//    private val service = ExampleService(api)
//
//    @Test
//    fun exampleTest() {
//        // given
//        val call = mock<Call<List<UserRemote>>>()
//        val expectedUserRemotes = emptyList<UserRemote>()
//
//        whenever(call.execute()).thenReturn(Response.success(expectedUserRemotes))
//        whenever(api.getUsers()).thenReturn(call)
//
//        // when
//        val users = service.example()
//
//        // then
//        assertEquals(users, expectedUserRemotes)
//    }
//}