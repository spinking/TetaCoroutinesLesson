package studio.eyesthetics.tetaarchitecturelesson

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import studio.eyesthetics.tetaarchitecturelesson.application.App
import studio.eyesthetics.tetaarchitecturelesson.application.base.SavedStateViewModelFactory
import studio.eyesthetics.tetaarchitecturelesson.ui.main.MainScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    init {
        App.instance.appComponent.inject(this@MainActivity)
    }

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        SavedStateViewModelFactory(mainViewModelFactory, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme() {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}