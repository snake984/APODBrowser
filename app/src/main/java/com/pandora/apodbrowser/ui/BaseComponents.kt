package com.pandora.apodbrowser.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pandora.apodbrowser.R


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
