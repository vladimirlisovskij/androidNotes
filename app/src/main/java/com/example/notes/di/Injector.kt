package com.example.notes.di

object Injector {
    val component: MainComponent by lazy {
        DaggerMainComponent.create()
    }
}