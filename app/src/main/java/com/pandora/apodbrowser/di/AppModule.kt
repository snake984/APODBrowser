package com.pandora.apodbrowser.di

import android.content.Context
import com.pandora.apodbrowser.home.di.HomeComponent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module(subcomponents = [HomeComponent::class])
class AppModule(private val applicationContext: Context) {

    @Provides
    fun provideApplicationContext(): Context = applicationContext

    @Provides
    @Named(IO_BACKGROUND_COROUTINE_CONTEXT_KEY)
    fun provideIOCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Named(DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY)
    fun provideDefaultBackgroundCoroutineContext(): CoroutineContext = Dispatchers.Default

    companion object {
        const val DEFAULT_BACKGROUND_COROUTINE_CONTEXT_KEY = "Default"
        const val IO_BACKGROUND_COROUTINE_CONTEXT_KEY = "IO"
    }
}