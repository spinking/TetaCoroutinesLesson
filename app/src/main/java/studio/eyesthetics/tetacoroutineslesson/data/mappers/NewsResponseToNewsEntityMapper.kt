package studio.eyesthetics.tetacoroutineslesson.data.mappers

import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetacoroutineslesson.data.models.responses.SingleNewsResponse

class NewsResponseToNewsEntityMapper : Mapper<SingleNewsResponse, NewsEntity> {
    override fun mapFromEntity(type: SingleNewsResponse?): NewsEntity {
        return NewsEntity(
            id = type?.id ?: "",
            title = type?.title ?: "",
            description = type?.description ?: "",
            imageUrl = type?.imageUrl ?: ""
        )
    }

    override fun mapFromListEntity(type: List<SingleNewsResponse>): List<NewsEntity> {
        return type.map { mapFromEntity(it) }
    }
}