package studio.eyesthetics.tetaarchitecturelesson.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import studio.eyesthetics.tetaarchitecturelesson.application.base.Notification
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideNotificationChannel(): Channel<Notification> = Channel()

    @Singleton
    @Provides
    fun provideLoadingChannel(): Channel<Boolean> = Channel()
}