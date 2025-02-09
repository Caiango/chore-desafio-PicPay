package com.picpay.desafio.android.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.picpay.desafio.android.StatusListener
import com.picpay.domain.screen.ScreenStatus
import desafio_android.R
import kotlinx.coroutines.launch

@Composable
fun ScreenStatusComposable(
    modifier: Modifier = Modifier,
    status: ScreenStatus,
    listener: StatusListener,
    content: @Composable () -> Unit,
) {
    val corroutineScope = rememberCoroutineScope(

    )
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

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        corroutineScope.launch { listener.onButtonClick() }
                    },
                ) {
                    Text(text = stringResource(R.string.try_again))
                }
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