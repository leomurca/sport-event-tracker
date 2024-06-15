package xyz.leomurca.sporteventtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import xyz.leomurca.sporteventtracker.ui.home.HomeScreen
import xyz.leomurca.sporteventtracker.ui.home.HomeViewModel
import xyz.leomurca.sporteventtracker.ui.theme.SportEventTrackerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportEventTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(viewModel = viewModel, modifier = Modifier.padding(it))
                }
            }
        }
    }
}