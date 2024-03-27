package com.pandora.apodbrowser.ui

import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun BaseLottieAnimationView(
    modifier: Modifier = Modifier,
    @RawRes animationResId: Int,
    repeatForever: Boolean = false,
) {
    val animationComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = animationResId
        )
    )

    LottieAnimation(
        modifier = modifier,
        composition = animationComposition,
        iterations = if (repeatForever) {
            LottieConstants.IterateForever
        } else {
            1
        }
    )
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    @RawRes animationResId: Int,
) {
    BaseLottieAnimationView(
        modifier = modifier,
        animationResId = animationResId,
        repeatForever = true
    )
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    @RawRes animationResId: Int,
) {
    BaseLottieAnimationView(
        modifier = modifier,
        animationResId = animationResId,
        repeatForever = false
    )
}

@Composable
fun Toast(
    modifier: Modifier = Modifier,
    @StringRes messageResId: Int,
    displayLength: Int = Toast.LENGTH_SHORT
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        Toast.makeText(
            context,
            context.getString(messageResId), displayLength
        ).show()
    }
}

@Composable
fun Fab(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary,
        onClick = {
            onClick()
        }) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription)
        )
    }
}