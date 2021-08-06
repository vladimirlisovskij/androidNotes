package com.example.notes.presenter.di

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    fun provideContext() = activity.applicationContext
}