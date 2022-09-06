package studio.eyesthetics.tetaarchitecturelesson.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.tetaarchitecturelesson.data.NetworkMonitor
import studio.eyesthetics.tetaarchitecturelesson.data.network.errors.NoNetworkError

class NetworkStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (NetworkMonitor.isConnected.not()) {
            NetworkMonitor.isConnected = true
            throw NoNetworkError()
        }
        return chain.proceed(chain.request())
    }
}