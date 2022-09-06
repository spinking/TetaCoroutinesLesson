package studio.eyesthetics.tetaarchitecturelesson.data.repositories.news

import kotlinx.coroutines.flow.Flow
import studio.eyesthetics.tetaarchitecturelesson.data.database.dao.NewsDao
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetaarchitecturelesson.data.mappers.NewsResponseToNewsEntityMapper
import studio.eyesthetics.tetaarchitecturelesson.data.network.api.INewsApi

class NewsRepository(
    private val newsMapper: NewsResponseToNewsEntityMapper,
    private val newsDao: NewsDao,
    private val newsApi: INewsApi
) : INewsRepository {
    override suspend fun getNews(): Flow<List<NewsEntity>> {
        getNewsFromNetwork()
        return newsDao.getNews()
    }

    override suspend fun getNewsFromNetwork() {
        val news = newsApi.getNews().data ?: emptyList()
        newsDao.upsert(newsMapper.mapFromListEntity(news))
    }
}