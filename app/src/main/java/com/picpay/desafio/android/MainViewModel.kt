package com.picpay.desafio.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.domain.providers.LocalProvider
import com.picpay.domain.providers.RemoteProvider
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.User
import com.picpay.domain.user.UserScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val local: LocalProvider,
    private val remote: RemoteProvider
) : ViewModel(), KoinComponent {

    private val _screen = MutableLiveData(UserScreen())
    val screen: LiveData<UserScreen> get() = _screen

    init {
        viewModelScope.launch { getData() }
    }

    suspend fun getData() {
        getUsers(_screen.value)
    }

    private suspend fun getUsers(currentState: UserScreen?) {
        _screen.postValue(currentState?.copy(status = ScreenStatus.Loading))

        val result: UserScreen = remote.getUsers()

        if (result.status.isReady) {
            _screen.postValue(result)
            insertUser(result.userList.orEmpty())
        } else {
            val users = getAllLocalUsers()

            val status = if (users.isNotEmpty()) {
                ScreenStatus.Ready
            } else {
                result.status
            }

            _screen.postValue(
                currentState?.copy(
                    status = status,
                    userList = users
                )
            )
        }
    }

    private fun insertUser(users: List<User>) {
        viewModelScope.launch {
            local.insertUsers(users)
        }
    }

    private suspend fun getAllLocalUsers(): List<User> {
        return local.getAllUsers()
    }
}