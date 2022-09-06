package studio.eyesthetics.tetaarchitecturelesson.ui.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaarchitecturelesson.MainViewModel

@Composable
fun NewsScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val news = viewModel.news.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.getNews()
        }
    }

    LazyColumn() {
        items(items = news.value, key = { it.id }) {
            NewsItem(item = it)
        }
    }
}