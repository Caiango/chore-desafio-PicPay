package com.picpay.desafio.android.presentation.compose.fakedata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.picpay.domain.screen.ScreenStatus
import com.picpay.domain.user.UserScreen

class UserScreenFakeData : PreviewParameterProvider<UserScreen> {
    val readyUserScreen = UserScreen(
        status = ScreenStatus.Ready,
        userList = UserFakeData().values.toList()
    )

    val loadingUserScreen = UserScreen(
        status = ScreenStatus.Loading,
        userList = UserFakeData().values.toList()
    )

    val errorUserScreen = UserScreen(
        status = ScreenStatus.Error(t = Throwable(message = "Nao foi possível carregar a tela")),
        userList = UserFakeData().values.toList()
    )

    override val values: Sequence<UserScreen>
        get() = sequenceOf(
            readyUserScreen,
            loadingUserScreen,
            errorUserScreen
        )
}