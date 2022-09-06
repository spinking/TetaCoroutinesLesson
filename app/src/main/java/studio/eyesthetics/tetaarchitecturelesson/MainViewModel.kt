package studio.eyesthetics.tetaarchitecturelesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaarchitecturelesson.application.base.IViewModelFactory
import studio.eyesthetics.tetaarchitecturelesson.application.base.Notification
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity
import studio.eyesthetics.tetaarchitecturelesson.data.repositories.news.INewsRepository
import javax.inject.Inject

class MainViewModel(
    private val newsRepository: INewsRepository,
    private val notificationChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : ViewModel() {
    val notifications
        get() = notificationChannel.receiveAsFlow()
    val loading
        get() = loadingChannel.receiveAsFlow()

    private val _news = MutableSharedFlow<List<NewsEntity>>()
    val news: SharedFlow<List<NewsEntity>> = _news.asSharedFlow()

    fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews().collect {
                _news.emit(it)
            }
        }
    }
}

class MainViewModelFactory @Inject constructor(
    private val newsRepository: INewsRepository,
    private val notificationChannel: Channel<Notification>,
    private val loadingChannel: Channel<Boolean>
) : IViewModelFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(newsRepository, notificationChannel, loadingChannel)
    }
}