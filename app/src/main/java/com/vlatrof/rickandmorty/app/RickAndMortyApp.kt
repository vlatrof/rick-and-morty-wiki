package com.vlatrof.rickandmorty.app

import android.app.Application
import com.vlatrof.rickandmorty.app.di.AppComponent
import com.vlatrof.rickandmorty.app.di.ApplicationModule
import com.vlatrof.rickandmorty.app.di.DaggerAppComponent

class RickAndMortyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(applicationContext = this))
            .build()
    }
}
