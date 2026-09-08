package com.pandora.apodbrowser

import android.app.Application
import com.pandora.api.di.apiModule
import com.pandora.apodbrowser.di.appModule
import com.pandora.domain.di.domainModule
import com.pandora.repositories.di.repositoriesModule
import com.pandora.storage.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class APODBrowserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@APODBrowserApplication)
            modules(
                appModule,
                apiModule,
                storageModule,
                repositoriesModule,
                domainModule
            )
        }
    }
}