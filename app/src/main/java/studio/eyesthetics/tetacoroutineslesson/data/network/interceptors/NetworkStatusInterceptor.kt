package studio.eyesthetics.tetacoroutineslesson.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.tetacoroutineslesson.data.NetworkMonitor
import studio.eyesthetics.tetacoroutineslesson.data.network.errors.NoNetworkError

class NetworkStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (NetworkMonitor.isConnected.not()) {
            NetworkMonitor.isConnected = true
            throw NoNetworkError()
        }
        return chain.proceed(chain.request())
    }
}