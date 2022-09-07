package studio.eyesthetics.tetacoroutineslesson.data.models.responses

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T? = null
)