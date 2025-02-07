package com.picpay.desafio.android.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.picpay.domain.user.User
import com.picpay.desafio.android.presentation.compose.fakedata.UserFakeData
import desafio_android.R

@Composable
fun UserItemComposable(
    modifier: Modifier = Modifier,
    user: User,
) {
    val isImageLoading = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (imgRef, progressRef, nameRef, usernameRef) = createRefs()

        CircularProgressIndicator(
            modifier = Modifier
                .testTag(UserItemIDs.PROGRESS)
                .constrainAs(progressRef) {
                    top.linkTo(imgRef.top)
                    start.linkTo(imgRef.start)
                    bottom.linkTo(imgRef.bottom)

                    width = Dimension.value(52.dp)
                    height = Dimension.value(52.dp)

                    visibility = if (isImageLoading.value) {
                        Visibility.Visible
                    } else {
                        Visibility.Gone
                    }
                }
        )

        Image(
            modifier = Modifier
                .testTag(UserItemIDs.IMG)
                .constrainAs(imgRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.value(52.dp)
                    height = Dimension.value(52.dp)

                    visibility = if (isImageLoading.value.not()) {
                        Visibility.Visible
                    } else {
                        Visibility.Gone
                    }
                }
                .clip(CircleShape),
            painter = safePainterResource(
                url = user.img,
                isImageLoading = isImageLoading
            ),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .testTag(UserItemIDs.USERNAME)
                .constrainAs(usernameRef) {
                    if (isImageLoading.value.not()) {
                        top.linkTo(imgRef.top, 4.dp)
                        start.linkTo(imgRef.end, 16.dp)
                    } else {
                        top.linkTo(progressRef.top, 4.dp)
                        start.linkTo(progressRef.end, 16.dp)
                    }
                },
            text = user.username,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .testTag(UserItemIDs.USER)
                .constrainAs(nameRef) {
                    if (isImageLoading.value.not()) {
                        top.linkTo(usernameRef.bottom)
                        start.linkTo(imgRef.end, 16.dp)
                    } else {
                        top.linkTo(usernameRef.bottom)
                        start.linkTo(progressRef.end, 16.dp)
                    }
                },
            text = user.name,
            textAlign = TextAlign.Start,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun safePainterResource(url: String, isImageLoading: MutableState<Boolean>): Painter {
    return if (LocalInspectionMode.current) {
        painterResource(id = R.drawable.ic_round_account_circle)
    } else {
        rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
                error(R.drawable.ic_round_account_circle)
                placeholder(null)
                listener(
                    onStart = {
                        isImageLoading.value = true
                    },
                    onSuccess = { _, _ ->
                        isImageLoading.value = false
                    },
                    onError = { _, _ ->
                        isImageLoading.value = false
                    },
                    onCancel = { _ ->
                        isImageLoading.value = false
                    }
                )
            }
        )
    }
}

@Composable
@Preview
private fun UserItemComposablePreview(
    @PreviewParameter(UserFakeData::class) data: User
) {
    MaterialTheme {
        UserItemComposable(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxWidth()
                .wrapContentHeight(),
            user = data
        )
    }
}

object UserItemIDs {
    const val USER = "USER"
    const val USERNAME = "USERNAME"
    const val IMG = "IMG"
    const val PROGRESS = "PROGRESS"
}