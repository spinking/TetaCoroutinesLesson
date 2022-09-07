package studio.eyesthetics.tetaarchitecturelesson.data.mappers

import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.NewResponse

class NewsResponseToNewsEntityMapper : Mapper<NewResponse, NewsEntity> {
    override fun mapFromEntity(type: NewResponse?): NewsEntity {
        return NewsEntity(
            id = type?.id ?: "",
            title = type?.title ?: "",
            description = type?.description ?: "",
            imageUrl = type?.imageUrl ?: ""
        )
    }

    override fun mapFromListEntity(type: List<NewResponse>): List<NewsEntity> {
        return type.map { mapFromEntity(it) }
    }
}