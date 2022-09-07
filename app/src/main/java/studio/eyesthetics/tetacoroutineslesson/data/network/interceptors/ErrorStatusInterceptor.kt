package studio.eyesthetics.tetacoroutineslesson.data.network.interceptors

import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.tetacoroutineslesson.data.network.errors.ApiError
import studio.eyesthetics.tetacoroutineslesson.data.network.errors.ErrorBody
import javax.inject.Inject

class ErrorStatusInterceptor @Inject constructor(
    private val gson: Gson
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())

        if (res.isSuccessful) return res

        var error = try {
            gson.fromJson(res.body!!.string(), ErrorBody::class.java)
        } catch (e: JsonParseException) {
            ErrorBody(e.message ?: DEFAULT_ERROR_MESSAGE)
        }

        if (error == null) error = ErrorBody(DEFAULT_ERROR_MESSAGE)

        when (res.code) {
            400 -> throw ApiError.BadRequest(error.message)
            401 -> throw ApiError.Unauthorized(error.message)
            403 -> throw ApiError.Forbidden(error.message)
            404 -> throw ApiError.NotFound(error.message)
            500 -> throw ApiError.InternalServerError(error.message)
            else -> throw ApiError.UnknownError(error.message)
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Undefined"
    }
}