package studio.eyesthetics.tetacoroutineslesson.data.mappers

import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetacoroutineslesson.data.models.responses.NewResponse

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