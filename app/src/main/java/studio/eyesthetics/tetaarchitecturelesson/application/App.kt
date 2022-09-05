package studio.eyesthetics.tetaarchitecturelesson.application

import android.app.Application
import studio.eyesthetics.tetaarchitecturelesson.data.NetworkMonitor
import studio.eyesthetics.tetaarchitecturelesson.di.AppComponent
import studio.eyesthetics.tetaarchitecturelesson.di.DaggerAppComponent

class App : Application(), AppComponent.ComponentProvider {

    override lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        NetworkMonitor.registerNetworkMonitor(applicationContext)

        appComponent.inject(this)
    }
}