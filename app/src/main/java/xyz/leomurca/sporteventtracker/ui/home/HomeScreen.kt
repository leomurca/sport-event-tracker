package xyz.leomurca.sporteventtracker.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier) {
    val state = viewModel.uiState.collectAsState()

    when (val value = state.value) {
        is HomeViewModel.UiState.Loaded.Success -> {
            LazyColumn(modifier) {
                items(value.sports) {
                    ExpandableSportItem(sport = it)
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

@Composable
private fun ExpandableSportItem(sport: Sport) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier.weight(1F)) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = sport.name,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Switch(
                checked = true,
                thumbContent = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onCheckedChange = {}
            )

            ExpandableChevronIcon(
                isExpanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                sport.activeEvents.map {
                    ActiveSportEventItem(activeSportEvent = it)
                }
            }
        }
    }
}

@Composable
private fun ExpandableChevronIcon(isExpanded: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "")

    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = { onClick() },
        content = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation)
            )
        }
    )
}

@Composable
private fun ActiveSportEventItem(activeSportEvent: SportEvent) {
    Text(
        text = activeSportEvent.name,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
}
