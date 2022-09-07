package studio.eyesthetics.tetacoroutineslesson.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.tetacoroutineslesson.data.mappers.NewsResponseToNewsEntityMapper

@Module
class MapperModule {
    @Provides
    fun provideNewsResponseToNewsEntityMapper(): NewsResponseToNewsEntityMapper = NewsResponseToNewsEntityMapper()
}