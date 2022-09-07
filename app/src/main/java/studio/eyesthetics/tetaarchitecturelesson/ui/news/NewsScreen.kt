package studio.eyesthetics.tetaarchitecturelesson.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaarchitecturelesson.MainViewModel
import studio.eyesthetics.tetaarchitecturelesson.R

@Composable
fun NewsScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val news = viewModel.news.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.getNews()
        }
    }

    LaunchedEffect(key1 = news.value) {
        if (news.value.isNotEmpty()) {
            lazyListState.animateScrollToItem(news.value.size - 1)
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.getNewsFromNetwork()
        },
        indicator = { _, _ -> }
    ) {
        LazyColumn(reverseLayout = true) {
            items(items = news.value, key = { it.id }) {
                NewsItem(item = it)
                if (news.value.last() != it)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                    )
            }
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = {
                            viewModel.clearNews()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.news_clear).uppercase()
                        )
                    }
                }
            }
        }
    }
}