package com.shiraj.reddit

import android.app.Application
import com.shiraj.reddit.di.component.AppComponent
import com.shiraj.reddit.di.component.DaggerAppComponent
import com.shiraj.reddit.di.module.AppModule

class RedditApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}