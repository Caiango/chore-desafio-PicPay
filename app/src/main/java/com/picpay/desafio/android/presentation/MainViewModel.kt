package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.User
import com.picpay.domain.user.UserScreen
import com.picpay.remote.ServiceRemoteProvider
import com.picpay.remote.toDomain
import com.pipcpay.local.room.UserRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val userRepository: UserRepository,
    private val service: ServiceRemoteProvider
) : ViewModel(), KoinComponent {

    private val _screen = MutableLiveData(UserScreen())
    val screen: LiveData<UserScreen> get() = _screen

    init {
        getData()
    }

    fun getData() {
        getUsers(_screen.value)
    }

    private fun getUsers(currentState: UserScreen?) {
        _screen.postValue(currentState?.copy(status = ScreenStatus.Loading))

        service.getUsers(
            onFailureCallback = { t ->
                viewModelScope
                    .launch {
                        val users = getAllLocalUsers()

                        val status = if (users.isNotEmpty()) {
                            ScreenStatus.Ready
                        } else {
                            ScreenStatus.Error(t)
                        }

                        _screen.postValue(
                            currentState?.copy(
                                status = status,
                                userList = users
                            )
                        )
                    }
            },
            onSuccessCallback = { list ->
                _screen.postValue(
                    currentState?.copy(
                        userList = list.map { it.toDomain() },
                        status = ScreenStatus.Ready
                    )
                )

                insertUser(list.map { it.toDomain() })
            }
        )
    }

    private fun insertUser(users: List<User>) {
        viewModelScope.launch {
            userRepository.insertUser(users)
        }
    }

    private suspend fun getAllLocalUsers(): List<User> {
        return userRepository.getAllUsers()
    }
}