package studio.eyesthetics.tetacoroutineslesson.application

import android.app.Application
import studio.eyesthetics.tetacoroutineslesson.data.NetworkMonitor
import studio.eyesthetics.tetacoroutineslesson.di.AppComponent
import studio.eyesthetics.tetacoroutineslesson.di.DaggerAppComponent

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