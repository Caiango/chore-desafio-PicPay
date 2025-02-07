package com.picpay.desafio.android.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.picpay.domain.screen.ScreenStatus

@Composable
fun ScreenStatusComposable(
    modifier: Modifier = Modifier,
    status: ScreenStatus,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            status.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.testTag(ScreenStatusIDs.LOADING))
            }

            status.isError -> {
                Text(
                    modifier = Modifier.testTag(ScreenStatusIDs.ERROR),
                    text = (status as ScreenStatus.Error).t?.message.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 28.sp
                )
            }

            status.isReady -> {
                content()
            }
        }
    }
}

object ScreenStatusIDs {
    const val ERROR = "ERROR"
    const val LOADING = "LOADING"
}