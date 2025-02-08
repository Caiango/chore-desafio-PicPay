package com.picpay.desafio.android.compose.fakedata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.picpay.domain.user.User

class UserFakeData: PreviewParameterProvider<User> {
    val user = User(
        img = "",
        name = "Caio Valença",
        id = 0,
        username = "Caiango"
    )

    override val values: Sequence<User>
        get() = sequenceOf(
            user,
            user.copy(name = "José", username = "Zé da Manga")
        )
}