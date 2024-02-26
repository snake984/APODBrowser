package com.pandora.apodbrowser.di

import com.pandora.apodbrowser.home.di.HomeComponent
import dagger.Module

@Module(subcomponents = [HomeComponent::class])
object AppModule