package studio.eyesthetics.tetaarchitecturelesson.data.models.responses

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("news_id")
    val id: String,
    @SerializedName("news_title")
    val title: String,
    @SerializedName("news_description")
    val description: String,
    @SerializedName("news_image_url")
    val imageUrl: String
)