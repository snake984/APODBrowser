package com.pandora.apodbrowser.di

import com.pandora.apodbrowser.APODBrowserApplication
import com.pandora.apodbrowser.MainActivity
import com.pandora.apodbrowser.home.di.HomeModule
import com.pandora.apodbrowser.picturedetail.di.PictureDetailModule

fun MainActivity.homeComponent() =
    (application as APODBrowserApplication).provideHomeComponentFactory().create(HomeModule)

fun MainActivity.pictureDetailComponent() =
    (application as APODBrowserApplication).providePictureDetailComponentFactory()
        .create(PictureDetailModule)