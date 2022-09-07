package studio.eyesthetics.tetaarchitecturelesson.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity

@Dao
interface NewsDao : BaseDao<NewsEntity> {
    @Transaction
    suspend fun upsert(list: List<NewsEntity>) {
        insert(list)
            .mapIndexed { index, recordId -> if(recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Query("SELECT COUNT(id) FROM news")
    fun getNewsCount(): Int

    @Query("DELETE FROM news")
    fun clearNews()
}