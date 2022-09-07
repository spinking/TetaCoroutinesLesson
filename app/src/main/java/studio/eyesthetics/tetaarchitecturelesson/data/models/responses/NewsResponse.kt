package studio.eyesthetics.tetaarchitecturelesson.data.models.responses

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("news")
    val news: List<NewResponse>
)