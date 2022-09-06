package studio.eyesthetics.tetaarchitecturelesson.data.network.api

import retrofit2.http.GET
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.BaseResponse
import studio.eyesthetics.tetaarchitecturelesson.data.models.responses.NewsResponse

interface INewsApi {
    @GET("News")
    suspend fun getNews(): BaseResponse<List<NewsResponse>>
}