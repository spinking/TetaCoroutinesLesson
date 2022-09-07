package studio.eyesthetics.tetacoroutineslesson.data.repositories.news

import kotlinx.coroutines.flow.Flow
import studio.eyesthetics.tetacoroutineslesson.data.database.dao.NewsDao
import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetacoroutineslesson.data.mappers.NewsResponseToNewsEntityMapper
import studio.eyesthetics.tetacoroutineslesson.data.network.api.INewsApi

class NewsRepository(
    private val newsMapper: NewsResponseToNewsEntityMapper,
    private val newsDao: NewsDao,
    private val newsApi: INewsApi
) : INewsRepository {
    override suspend fun getNews(): Flow<List<NewsEntity>> {
        return newsDao.getNews()
    }

    override suspend fun getNewsFromNetwork(page: Int) {
        val news = newsApi.getNews(page).data?.news ?: emptyList()
        newsDao.upsert(newsMapper.mapFromListEntity(news))
    }

    override fun isNewsEmpty(): Boolean {
        return newsDao.getNewsCount() == 0
    }

    override fun clearNews() {
        newsDao.clearNews()
    }
}