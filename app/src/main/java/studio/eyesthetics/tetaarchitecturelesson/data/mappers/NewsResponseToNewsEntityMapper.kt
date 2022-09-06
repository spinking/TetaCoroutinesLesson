package studio.eyesthetics.tetaarchitecturelesson.data.mappers

import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.NewsResponse

class NewsResponseToNewsEntityMapper : Mapper<NewsResponse, NewsEntity> {
    override fun mapFromEntity(type: NewsResponse?): NewsEntity {
        return NewsEntity(
            id = type?.id ?: "",
            title = type?.title ?: "",
            description = type?.description ?: "",
            imageUrl = type?.imageUrl ?: ""
        )
    }

    override fun mapFromListEntity(type: List<NewsResponse>): List<NewsEntity> {
        return type.map { mapFromEntity(it) }
    }
}