package studio.eyesthetics.tetacoroutineslesson.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.eyesthetics.tetacoroutineslesson.BuildConfig
import studio.eyesthetics.tetacoroutineslesson.data.database.dao.NewsDao
import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity

@Database(
    entities = [
        NewsEntity::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID + ".db"
        const val DATABASE_VERSION = 1
    }

    abstract fun newsDao(): NewsDao
}