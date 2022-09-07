package studio.eyesthetics.tetaarchitecturelesson.data.network.interceptors

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import studio.eyesthetics.tetaarchitecturelesson.BuildConfig
import studio.eyesthetics.tetaarchitecturelesson.application.extensions.readTextFromAsset
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    private val endPointsList: List<Endpoints> = listOf(
        Endpoints.News,
        Endpoints.News1
    )

    enum class ResponseCode {
        success, error
    }

    enum class Endpoints {
        News,
        News1
    }

    private fun getAssetName (
        endpoint: Endpoints,
        method: String,
        code: ResponseCode,
        variant: String = ""
    ): String {
        return arrayOf(endpoint.name, method, code.name, variant).filter { it.isNotEmpty() }.joinToString( separator = "_").plus(".json")
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        var ret = chain.proceed(chain.request())
        var requestDelay: Long = 0

        if (BuildConfig.DEBUG && BuildConfig.MOCK_ENABLED) {
            val uri = chain.request().url.toUri().toString()

            val match = endPointsList.filter { uri.contains(it.name) }

            if (match.isNotEmpty()) {
                requestDelay = 1000
                val responseCode = 200
                val responseBody = when {
                    uri.contains("${Endpoints.News.name}?page=0") -> context.readTextFromAsset("json/mock/news/" +
                            getAssetName(
                                Endpoints.News,
                                chain.request().method.toLowerCase(Locale.ROOT),
                                ResponseCode.success
                            ))
                    uri.contains("${Endpoints.News.name}?page=1") -> context.readTextFromAsset("json/mock/news/" +
                            getAssetName(
                                Endpoints.News1,
                                chain.request().method.toLowerCase(Locale.ROOT),
                                ResponseCode.success
                            ))
                    else -> null
                }

                responseBody?.let {
                    ret = Response.Builder()
                        .code(responseCode)
                        .message("mock data")
                        .body(
                            it.toResponseBody("application/json; charset=UTF-8".toMediaType())
                        )
                        .request(chain.request())
                        .addHeader("content-type", "application/json")
                        .protocol(Protocol.HTTP_1_0)
                        .build()

                    val logText = "Request: ${chain.request()}\nResponse: $ret"
                    Log.d("MockInterceptor", logText)
                }
            }
        }

        try {
            Thread.sleep(requestDelay)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return ret
    }
}