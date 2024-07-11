package com.example.exrate

import org.koin.core.context.startKoin

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(createApplicationModule())
        }
    }
}