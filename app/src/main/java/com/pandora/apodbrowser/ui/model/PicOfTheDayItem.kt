package com.pandora.apodbrowser.ui.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.pandora.domain.model.PicOfTheDay
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class PicOfTheDayItem(
    val title: String,
    val date: String,
    val url: String,
    val explanation: String? = null,
    val hdUrl: String? = null,
) : Parcelable

fun PicOfTheDay.toItem() = PicOfTheDayItem(
    title = title,
    date = date,
    url = url,
    hdUrl = hdUrl,
    explanation = explanation,
)