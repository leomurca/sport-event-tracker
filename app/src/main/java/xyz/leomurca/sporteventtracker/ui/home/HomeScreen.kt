package xyz.leomurca.sporteventtracker.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier) {
    val state = viewModel.uiState.collectAsState()

    when (val value = state.value) {
        is HomeViewModel.UiState.Loaded.Success -> {
            LazyColumn(modifier) {
                items(value.sports) {
                    Text(text = it.name)
                }
            }
        }

        is HomeViewModel.UiState.Loaded.Error -> {
            Text(text = value.message)
        }

        is HomeViewModel.UiState.Loading -> {
            Text("Loading...")
        }
    }
}
