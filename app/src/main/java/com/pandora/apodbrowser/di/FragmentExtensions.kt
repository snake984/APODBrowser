package com.pandora.apodbrowser.di

import com.pandora.apodbrowser.APODBrowserApplication
import com.pandora.apodbrowser.MainActivity
import com.pandora.apodbrowser.home.di.HomeModule

fun MainActivity.homeComponent() =
    (application as APODBrowserApplication).provideHomeComponentFactory().create(HomeModule)