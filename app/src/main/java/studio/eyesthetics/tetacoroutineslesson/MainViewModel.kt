package studio.eyesthetics.tetacoroutineslesson

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import studio.eyesthetics.tetacoroutineslesson.application.base.BaseViewModel
import studio.eyesthetics.tetacoroutineslesson.application.base.IViewModelFactory
import studio.eyesthetics.tetacoroutineslesson.application.base.Notification
import studio.eyesthetics.tetacoroutineslesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetacoroutineslesson.data.repositories.news.INewsRepository
import javax.inject.Inject

class MainViewModel(
    private val newsRepository: INewsRepository,
    private val notificationsChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : BaseViewModel(notificationsChannel, loadingChannel) {

    init {
        getNews()
        initNews()
    }

    private val _news = MutableSharedFlow<List<NewsEntity>>()
    val news: SharedFlow<List<NewsEntity>> = _news.asSharedFlow()

    fun getNewsFromNetwork() {
        val errorHandler: (Throwable) -> Unit =  { err ->
            notify(Notification.ErrorMessage(
                message = err.message ?: "Something wrong",
                label = "try again",
                handler = { getNewsFromNetwork() }
            ))
        }
        launchSafety(errHandler = errorHandler, isNeedShowLoading = true) {
            newsRepository.getNewsFromNetwork(
                if (newsRepository.isNewsEmpty()) 0 else 1
            )
        }
    }

    fun clearNews() {
        newsRepository.clearNews()
    }

    private fun getNews() {
        launchSafety() {
            newsRepository.getNews().collect {
                _news.emit(it)
            }
        }
    }

    private fun initNews() {
        if (newsRepository.isNewsEmpty()) getNewsFromNetwork()
    }
}

class MainViewModelFactory @Inject constructor(
    private val newsRepository: INewsRepository,
    private val notificationsChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : IViewModelFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(newsRepository, notificationsChannel, loadingChannel)
    }
}