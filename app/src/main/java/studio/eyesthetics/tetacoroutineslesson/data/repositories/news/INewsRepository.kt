package studio.eyesthetics.tetacoroutineslesson.data.repositories.news

import kotlinx.coroutines.flow.Flow
import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity

interface INewsRepository {
    suspend fun getNews(): Flow<List<NewsEntity>>
    suspend fun getNewsFromNetwork(page: Int)

    fun isNewsEmpty(): Boolean
    fun clearNews()
}