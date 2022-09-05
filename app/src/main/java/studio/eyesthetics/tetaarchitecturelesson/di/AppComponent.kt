package studio.eyesthetics.tetaarchitecturelesson.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import studio.eyesthetics.tetaarchitecturelesson.MainActivity
import studio.eyesthetics.tetaarchitecturelesson.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        MapperModule::class,
        DatabaseModule::class
    ]
)

interface AppComponent {
    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    interface ComponentProvider {
        val appComponent: AppComponent
    }

    fun inject(activity: MainActivity)
}