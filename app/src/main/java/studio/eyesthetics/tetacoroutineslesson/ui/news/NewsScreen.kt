package studio.eyesthetics.tetacoroutineslesson.ui.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import studio.eyesthetics.tetacoroutineslesson.MainViewModel
import studio.eyesthetics.tetacoroutineslesson.R

@Composable
fun NewsScreen(viewModel: MainViewModel) {
    val news = viewModel.news.collectAsState(initial = emptyList())

    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.getNewsFromNetwork()
        },
        indicator = { _, _ -> },
    ) {
        LazyColumn(
            reverseLayout = true,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = news.value, key = { it.id }) {
                NewsItem(item = it)
                if (news.value.last() != it)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                    )
            }
            if (news.value.isNotEmpty()) {
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
}