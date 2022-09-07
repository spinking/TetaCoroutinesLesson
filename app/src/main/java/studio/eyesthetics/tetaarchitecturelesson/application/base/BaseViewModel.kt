package studio.eyesthetics.tetaarchitecturelesson.application.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel(
    private val notificationsChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : ViewModel() {

    val notifications
        get() = notificationsChannel.receiveAsFlow()
    val loading
        get() = loadingChannel.receiveAsFlow()

    fun notify(event: Notification) {
        notificationsChannel.trySend(event)
    }

    private fun handleLoading(isLoading: Boolean) {
        loadingChannel.trySend(isLoading)
    }

    protected open fun launchSafety(
        errHandler: ((Throwable) -> Unit)? = null,
        compHandler: ((Throwable?) -> Unit)? = null,
        isNeedShowLoading: Boolean = false,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val errHand = CoroutineExceptionHandler { _, err ->
            errHandler?.invoke(err) ?: notify(Notification.ErrorMessage(
                message = err.message ?: "Something wrong",
                label = "try again"
            ))
        }

        (viewModelScope + errHand).launch {
            if (isNeedShowLoading) handleLoading(true)
            block()
        }.invokeOnCompletion {
            if (isNeedShowLoading) handleLoading(false)
            compHandler?.invoke(it)
        }
    }
}