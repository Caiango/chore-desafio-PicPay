package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.presentation.MainViewModel
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.User
import com.picpay.domain.user.UserScreen
import com.picpay.remote.ServiceRemoteProvider
import com.picpay.remote.UserRemote
import com.picpay.remote.toRemote
import com.pipcpay.local.room.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var service: ServiceRemoteProvider
    private lateinit var viewModel: MainViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk(relaxed = true)
        service = mockk(relaxed = true)

        viewModel = MainViewModel(userRepository, service)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenGetUsersIsSuccessfulStatusShouldBeReadyAndUserListPopulated() = runTest {
        val userList = listOf(
            User(img = "url1", name = "John", id = 1, username = "john123"),
            User(img = "url2", name = "Jane", id = 2, username = "jane456")
        )

        every { service.getUsers(captureLambda(), any()) } answers {
            lambda<(List<UserRemote>) -> Unit>().invoke(userList.map { it.toRemote() })
        }

        val observer = mockk<Observer<UserScreen>>(relaxed = true)
        viewModel.screen.observeForever(observer)

        viewModel.getData()

        verify { observer.onChanged(UserScreen(userList = userList, status = ScreenStatus.Ready)) }
        coVerify { userRepository.insertUser(userList) }
    }

    @Test
    fun whenGetUsersFailsAndLocalDataExistsStatusShouldBeReady() = runTest {
        val localUsers = listOf(
            User(img = "url1", name = "John", id = 1, username = "john123")
        )

        every { service.getUsers(any(), captureLambda()) } answers {
            lambda<(Throwable) -> Unit>().invoke(Throwable("Service error"))
        }

        coEvery { userRepository.getAllUsers() } returns localUsers

        val observer = mockk<Observer<UserScreen>>(relaxed = true)
        viewModel.screen.observeForever(observer)

        viewModel.getData()

        verify {
            observer.onChanged(
                UserScreen(
                    userList = localUsers,
                    status = ScreenStatus.Ready
                )
            )
        }
    }

    @Test
    fun whenGetUsersFailsAndnoLocalDatasStatusShouldBeError() = runTest {
        every { service.getUsers(any(), captureLambda()) } answers {
            lambda<(Throwable) -> Unit>().invoke(Throwable("Service error"))
        }

        coEvery { userRepository.getAllUsers() } returns emptyList()

        val observer = mockk<Observer<UserScreen>>(relaxed = true)
        viewModel.screen.observeForever(observer)

        viewModel.getData()

        verify { observer.onChanged(match { it.status is ScreenStatus.Error }) }
    }

}
