package com.pandora.apodbrowser.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CompletableDeferred

interface PermissionManager {
    suspend fun requestWriteExternalStoragePermission(): Boolean
}

internal class PermissionManagerImpl(private val activity: ComponentActivity) : PermissionManager {

    private var result = CompletableDeferred<Boolean>()
    private val permissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                result.complete(true)
            } else {
                //TODO - Display message
                result.complete(false)
            }
        }

    override suspend fun requestWriteExternalStoragePermission(): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            return true
        }

        result = CompletableDeferred()
        return when {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> true


            ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                //TODO - Show rationale
                false
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                result.await()
            }
        }
    }
}