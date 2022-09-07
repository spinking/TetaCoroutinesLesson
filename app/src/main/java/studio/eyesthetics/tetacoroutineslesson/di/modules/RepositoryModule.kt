package studio.eyesthetics.tetacoroutineslesson.di.modules

import dagger.Module
import dagger.Provides
import studio.eyesthetics.tetacoroutineslesson.data.database.dao.NewsDao
import studio.eyesthetics.tetacoroutineslesson.data.mappers.NewsResponseToNewsEntityMapper
import studio.eyesthetics.tetacoroutineslesson.data.network.api.INewsApi
import studio.eyesthetics.tetacoroutineslesson.data.repositories.news.INewsRepository
import studio.eyesthetics.tetacoroutineslesson.data.repositories.news.NewsRepository

@Module
class RepositoryModule {
    @Provides
    fun provideNewsRepository(
        newsMapper: NewsResponseToNewsEntityMapper,
        newsDao: NewsDao,
        newsApi: INewsApi
    ): INewsRepository = NewsRepository(newsMapper, newsDao, newsApi)
}