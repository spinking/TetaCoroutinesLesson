package studio.eyesthetics.tetaarchitecturelesson.data.repositories.news

import kotlinx.coroutines.flow.Flow
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity

interface INewsRepository {
    suspend fun getNews(): Flow<List<NewsEntity>>
    suspend fun getNewsFromNetwork()
}