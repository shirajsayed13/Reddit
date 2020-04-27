package com.shiraj.reddit.di.component

import com.shiraj.reddit.di.module.AppModule
import com.shiraj.reddit.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

}