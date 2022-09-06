package studio.eyesthetics.tetaarchitecturelesson.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.tetaarchitecturelesson.data.mappers.NewsResponseToNewsEntityMapper

@Module
class MapperModule {
    @Provides
    fun provideNewsResponseToNewsEntityMapper(): NewsResponseToNewsEntityMapper = NewsResponseToNewsEntityMapper()
}