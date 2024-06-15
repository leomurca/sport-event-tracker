package xyz.leomurca.sporteventtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import xyz.leomurca.sporteventtracker.ui.home.HomeScreen
import xyz.leomurca.sporteventtracker.ui.home.HomeViewModel
import xyz.leomurca.sporteventtracker.ui.theme.SportEventTrackerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportEventTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    HomeScreen(viewModel = viewModel, modifier = Modifier.padding(it))
                }
            }
        }
    }
}