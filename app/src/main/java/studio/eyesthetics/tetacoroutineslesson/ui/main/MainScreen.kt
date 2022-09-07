package studio.eyesthetics.tetacoroutineslesson.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import studio.eyesthetics.tetacoroutineslesson.MainViewModel
import studio.eyesthetics.tetacoroutineslesson.application.base.Notification
import studio.eyesthetics.tetacoroutineslesson.ui.news.NewsScreen

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isShowLoading = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.notifications.collect { notification ->
                renderNotification(notification, scaffoldState)
            }
        }
        scope.launch {
            viewModel.loading.collect { isLoading ->
                isShowLoading.value = isLoading
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            ContentHost(viewModel = viewModel, paddingValues = it)
        },
        snackbarHost = { host ->
            SnackbarHost(
                hostState = host,
                snackbar = {
                    Snackbar(
                        backgroundColor = MaterialTheme.colors.onPrimary,
                        action = {
                            TextButton(
                                onClick = { host.currentSnackbarData?.performAction() }
                            ) {
                                Text(
                                    text = host.currentSnackbarData?.actionLabel?.uppercase() ?: "",
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        },
                        content = {
                            Text(
                                text = host.currentSnackbarData?.message ?: "",
                                color = MaterialTheme.colors.onBackground
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            )
        }
    )
    if (isShowLoading.value) ProgressBar()
}

@Composable
fun ContentHost(viewModel: MainViewModel, paddingValues: PaddingValues) {
    NewsScreen(viewModel = viewModel)
}

suspend fun renderNotification(
    notification: Notification,
    scaffoldState: ScaffoldState
) {
    val result = when(notification) {
        is Notification.ErrorMessage -> {
            scaffoldState.snackbarHostState.showSnackbar(
                message = notification.message,
                actionLabel = notification.label,
                duration = if (notification.label.isEmpty()) SnackbarDuration.Short else SnackbarDuration.Indefinite
            )
        }
    }
    when(result) {
        SnackbarResult.ActionPerformed -> {
            notification.handler?.invoke()
        }
        SnackbarResult.Dismissed -> {}
    }
}

@Composable
fun ProgressBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}