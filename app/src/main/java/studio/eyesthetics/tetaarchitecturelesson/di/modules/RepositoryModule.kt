package studio.eyesthetics.tetaarchitecturelesson.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.tetaarchitecturelesson.data.database.dao.NewsDao
import studio.eyesthetics.tetaarchitecturelesson.data.mappers.NewsResponseToNewsEntityMapper
import studio.eyesthetics.tetaarchitecturelesson.data.network.api.INewsApi
import studio.eyesthetics.tetaarchitecturelesson.data.repositories.news.INewsRepository
import studio.eyesthetics.tetaarchitecturelesson.data.repositories.news.NewsRepository

@Module
class RepositoryModule {
    @Provides
    fun provideNewsRepository(
        newsMapper: NewsResponseToNewsEntityMapper,
        newsDao: NewsDao,
        newsApi: INewsApi
    ): INewsRepository = NewsRepository(newsMapper, newsDao, newsApi)
}