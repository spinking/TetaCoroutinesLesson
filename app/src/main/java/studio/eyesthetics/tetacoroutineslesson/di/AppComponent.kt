package studio.eyesthetics.tetacoroutineslesson.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import studio.eyesthetics.tetacoroutineslesson.MainActivity
import studio.eyesthetics.tetacoroutineslesson.di.modules.*
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