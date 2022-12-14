package studio.eyesthetics.tetacoroutineslesson.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import studio.eyesthetics.tetacoroutineslesson.data.database.AppDatabase
import studio.eyesthetics.tetacoroutineslesson.data.database.dao.NewsDao
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }
}