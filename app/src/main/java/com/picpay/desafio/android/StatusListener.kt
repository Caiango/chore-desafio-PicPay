package com.picpay.desafio.android

interface StatusListener {
    suspend fun onButtonClick()
}

object DummyStatusListener : StatusListener {
    override suspend fun onButtonClick() = Unit
}