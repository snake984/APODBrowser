package com.pandora.apodbrowser.di

import androidx.fragment.app.Fragment
import com.pandora.apodbrowser.APODBrowserApplication
import com.pandora.apodbrowser.home.view.HomeFragment
import com.pandora.apodbrowser.home.di.HomeModule

fun Fragment.injector() = (requireActivity().application as APODBrowserApplication)
fun HomeFragment.component() = injector().provideHomeComponentFactory().create(HomeModule)