package com.example.notes.application

import android.app.Application
import com.example.notes.data.di.DaggerDataComponent
import com.example.notes.data.di.DataComponent
import com.example.notes.presenter.di.DaggerPresenterComponent
import com.example.notes.presenter.di.PresenterComponent

class MainApplication: Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    val dataInjector: DataComponent by lazy {
        DaggerDataComponent.create()
    }

    val presenterInjector: PresenterComponent by lazy {
        DaggerPresenterComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}