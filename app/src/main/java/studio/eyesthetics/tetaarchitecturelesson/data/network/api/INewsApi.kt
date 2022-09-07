package studio.eyesthetics.tetaarchitecturelesson.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.BaseResponse
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.NewsResponse

interface INewsApi {
    @GET("News")
    suspend fun getNews(
        @Query("page") page: Int
    ): BaseResponse<NewsResponse>
}