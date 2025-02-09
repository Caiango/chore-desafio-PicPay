package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.domain.providers.LocalProvider
import com.picpay.domain.providers.RemoteProvider
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.User
import com.picpay.domain.user.UserScreen
import io.mockk.coEvery
import io.mockk.coVerify
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

    private lateinit var local: LocalProvider
    private lateinit var remote: RemoteProvider
    private lateinit var viewModel: MainViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        local = mockk(relaxed = true)
        remote = mockk(relaxed = true)

        viewModel = MainViewModel(local, remote)
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
        val userScreen = UserScreen(userList = userList, status = ScreenStatus.Ready)

        coEvery { remote.getUsers() } returns userScreen

        val observer = mockk<Observer<UserScreen>>(relaxed = true)
        viewModel.screen.observeForever(observer)

        viewModel.getData()

        verify { observer.onChanged(userScreen) }
        coVerify { local.insertUsers(userList) }
    }

    @Test
    fun whenGetUsersFailsAndLocalDataExistsStatusShouldBeReady() = runTest {
        val localUsers = listOf(
            User(img = "url1", name = "John", id = 1, username = "john123")
        )
        val userScreenError = UserScreen(status = ScreenStatus.Error())

        coEvery { remote.getUsers() } returns userScreenError
        coEvery { local.getAllUsers() } returns localUsers

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
    fun whenGetUsersFailsAndNoLocalDataStatusShouldBeError() = runTest {
        val userScreenError = UserScreen(status = ScreenStatus.Error())

        coEvery { remote.getUsers() } returns userScreenError
        coEvery { local.getAllUsers() } returns emptyList()

        val observer = mockk<Observer<UserScreen>>(relaxed = true)
        viewModel.screen.observeForever(observer)

        viewModel.getData()

        verify {
            observer.onChanged(
                UserScreen(
                    userList = emptyList(),
                    status = ScreenStatus.Error()
                )
            )
        }
    }

}
