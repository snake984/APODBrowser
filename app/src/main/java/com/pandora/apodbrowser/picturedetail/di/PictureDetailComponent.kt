package com.pandora.apodbrowser.picturedetail.di

import com.pandora.apodbrowser.di.ScreenScope
import com.pandora.apodbrowser.picturedetail.viewmodel.PictureDetailViewModelFactory
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [PictureDetailModule::class])
interface PictureDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(module: PictureDetailModule): PictureDetailComponent
    }

    fun pictureDetailViewModelFactory(): PictureDetailViewModelFactory
}

interface PictureDetailComponentFactoryProvider {
    fun providePictureDetailComponentFactory(): PictureDetailComponent.Factory
}