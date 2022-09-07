package studio.eyesthetics.tetaarchitecturelesson

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import studio.eyesthetics.tetaarchitecturelesson.application.base.BaseViewModel
import studio.eyesthetics.tetaarchitecturelesson.application.base.IViewModelFactory
import studio.eyesthetics.tetaarchitecturelesson.application.base.Notification
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetaarchitecturelesson.data.repositories.news.INewsRepository
import javax.inject.Inject

class MainViewModel(
    private val newsRepository: INewsRepository,
    private val notificationsChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : BaseViewModel(notificationsChannel, loadingChannel) {

    private val _news = MutableSharedFlow<List<NewsEntity>>()
    val news: SharedFlow<List<NewsEntity>> = _news.asSharedFlow()

    fun getNews() {
        launchSafety() {
            newsRepository.getNews().collect {
                if (newsRepository.isNewsEmpty()) getNewsFromNetwork()
                _news.emit(it)
            }
        }
    }

    fun getNewsFromNetwork() {
        launchSafety(isNeedShowLoading = true) {
            newsRepository.getNewsFromNetwork(
                if (newsRepository.isNewsEmpty()) 0 else 1
            )
        }
    }

    fun clearNews() {
        newsRepository.clearNews()
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