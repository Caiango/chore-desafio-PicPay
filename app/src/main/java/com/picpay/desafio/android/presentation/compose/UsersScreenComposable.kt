package com.picpay.desafio.android.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.picpay.domain.user.UserScreen
import com.picpay.desafio.android.presentation.compose.fakedata.UserScreenFakeData
import desafio_android.R

@Composable
fun UsersScreenComposable(
    modifier: Modifier = Modifier,
    screen: UserScreen,
) {

    ScreenStatusComposable(
        modifier = modifier,
        status = screen.status
    ) {
        Text(
            modifier = Modifier
                .testTag(ScreenTestIDs.TITLE)
                .fillMaxWidth(),
            text = stringResource(R.string.title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        screen.userList?.let {
            LazyColumn(
                modifier = Modifier
                    .testTag(ScreenTestIDs.LIST)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(it) { user ->
                    UserItemComposable(
                        user = user
                    )
                }
            }
        }
    }
}


@Composable
@Preview
private fun UsersScreenComposablePreview(
    @PreviewParameter(UserScreenFakeData::class) data: UserScreen
) {
    MaterialTheme {
        UsersScreenComposable(
            modifier = Modifier
                .background(color = Color.Black)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            screen = data
        )
    }
}

object ScreenTestIDs {
    const val TITLE = "TITLE"
    const val LIST = "LIST"
}