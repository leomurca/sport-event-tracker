package xyz.leomurca.sporteventtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background))
                {
                    Scaffold(
                        modifier = Modifier.padding(it),
                        topBar = {
                            CustomTopAppBar()
                        }
                    ) { paddingValues ->
                        HomeScreen(viewModel = viewModel, modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBar() {
    Row(
        modifier = Modifier
            .height(70.dp)
            .padding(0.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_sportstracker),
            contentDescription = "Logo",
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}