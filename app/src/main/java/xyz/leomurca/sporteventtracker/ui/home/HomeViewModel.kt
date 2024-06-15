package xyz.leomurca.sporteventtracker.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val text = "Hello SportEventTracker app!"
}

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier) {
    Text(viewModel.text, modifier = modifier)
}