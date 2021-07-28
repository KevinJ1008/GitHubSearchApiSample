package com.kevin1008.githubsearchapisample

import android.app.Application
import com.kevin1008.githubsearchapisample.di.dataSourceModule
import com.kevin1008.githubsearchapisample.di.repositoryModule
import com.kevin1008.githubsearchapisample.di.useCaseModule
import com.kevin1008.githubsearchapisample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(dataSourceModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}