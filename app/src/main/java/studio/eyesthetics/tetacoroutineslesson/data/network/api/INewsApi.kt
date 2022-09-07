package studio.eyesthetics.tetacoroutineslesson.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import studio.eyesthetics.tetacoroutineslesson.data.models.responses.BaseResponse
import studio.eyesthetics.tetacoroutineslesson.data.models.responses.NewsResponse

interface INewsApi {
    @GET("News")
    suspend fun getNews(
        @Query("page") page: Int
    ): BaseResponse<NewsResponse>
}