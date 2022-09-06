package studio.eyesthetics.tetaarchitecturelesson.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaarchitecturelesson.MainViewModel
import studio.eyesthetics.tetaarchitecturelesson.application.base.Notification
import studio.eyesthetics.tetaarchitecturelesson.ui.news.NewsScreen

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
                            TextButton(onClick = { host.currentSnackbarData?.performAction() }) {
                                Text(
                                    text = host.currentSnackbarData?.actionLabel?.uppercase() ?: "",
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold
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
            scaffoldState.snackbarHostState.showSnackbar(notification.message)
        }
    }
    when(result) {
        SnackbarResult.ActionPerformed -> {
            notification.handler.invoke()
        }
        SnackbarResult.Dismissed -> {}
    }
}