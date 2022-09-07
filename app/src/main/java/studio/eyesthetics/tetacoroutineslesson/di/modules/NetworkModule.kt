package studio.eyesthetics.tetacoroutineslesson.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import studio.eyesthetics.tetacoroutineslesson.BuildConfig
import studio.eyesthetics.tetacoroutineslesson.data.network.api.INewsApi
import studio.eyesthetics.tetacoroutineslesson.data.network.interceptors.ErrorStatusInterceptor
import studio.eyesthetics.tetacoroutineslesson.data.network.interceptors.MockInterceptor
import studio.eyesthetics.tetacoroutineslesson.data.network.interceptors.NetworkStatusInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class CoroutineScopeIO

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setLenient().create()

    @Provides
    fun provideNetworkStatusInterceptor() = NetworkStatusInterceptor()

    @Provides
    fun provideErrorStatusInterceptor(
        gson: Gson,
    ) = ErrorStatusInterceptor(gson)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        errorStatusInterceptor: ErrorStatusInterceptor,
        mockInterceptor: MockInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(errorStatusInterceptor)
            .also { httpClient ->
                if (BuildConfig.DEBUG) {
                    httpClient.addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY))
                    if (BuildConfig.MOCK_ENABLED) {
                        httpClient.addInterceptor(mockInterceptor)
                    }
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): INewsApi = retrofit.create(INewsApi::class.java)
}