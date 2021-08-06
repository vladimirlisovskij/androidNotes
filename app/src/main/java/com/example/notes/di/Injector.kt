package com.example.notes.di

import android.app.Activity
import com.example.notes.presenter.di.ActivityModule

object Injector {
    var component: MainComponent? = null

    fun Activity.initInjector() {
        component = DaggerMainComponent
            .builder()
            .activityModule(ActivityModule(this))
            .build()
    }

}