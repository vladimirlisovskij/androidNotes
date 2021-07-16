package com.example.notes.application

import android.app.Application
import com.example.notes.data.di.DaggerDataComponent
import com.example.notes.data.di.DataComponent
import com.example.notes.domain.di.DaggerUseCaseComponent
import com.example.notes.domain.di.UseCaseComponent
import com.example.notes.presenter.di.*

class MainApplication: Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    val dataComponent: DataComponent by lazy {
        DaggerDataComponent.create()
    }

    val useCaseComponent: UseCaseComponent by lazy {
        DaggerUseCaseComponent.create()
    }

    val presenterComponent: PresenterComponent by lazy {
        DaggerPresenterComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}