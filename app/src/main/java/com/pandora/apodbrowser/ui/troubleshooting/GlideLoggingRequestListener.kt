package com.pandora.apodbrowser.ui.troubleshooting

import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

internal class GlideLoggingRequestListener: RequestListener<Any> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Any>,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Any,
        model: Any,
        target: Target<Any>?,
        dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        Log.d(GLIDE_LOG_TAG, "Loading picture $model from $dataSource")
        return true
    }

    companion object {
        const val GLIDE_LOG_TAG = "Glide"
    }
}