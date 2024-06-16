package xyz.leomurca.sporteventtracker.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import xyz.leomurca.sporteventtracker.ui.components.ExpandableChevronIconToggleButton
import xyz.leomurca.sporteventtracker.ui.components.FilledCircleIcon
import xyz.leomurca.sporteventtracker.ui.extension.shimmerEffect

@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier) {
    val state = viewModel.uiState.collectAsState()
    val activeSportSwitchesIds = viewModel.activeSportSwitchesIds.collectAsState()

    when (val value = state.value) {
        is HomeViewModel.UiState.Loaded.Success -> {
            LazyColumn(modifier) {
                items(value.sports) { sport ->
                    ExpandableSportItem(
                        sport = sport,
                        isSwitchActive = activeSportSwitchesIds.value.contains(sport.id),
                        onAddEventToFavorites = { id -> viewModel.addFavorite(id) },
                        onRemoveEventFromFavorites = { event -> viewModel.removeFavorite(event) },
                        onFilterFavorites = { sportId, shouldFilter -> viewModel.filterFavorites(sportId, shouldFilter) },
                        onStartCountDown = { startTime -> viewModel.startCountdown(startTime) }
                    )
                }
            }
        }

        is HomeViewModel.UiState.Loaded.Error -> {
            Text(text = value.message)
        }

        is HomeViewModel.UiState.Loading -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(7) {
                    LoadingPlaceholder()
                }
            }
        }
    }
}

@Composable
private fun ExpandableSportItem(
    sport: Sport,
    isSwitchActive: Boolean,
    onAddEventToFavorites: (eventId: String) -> Unit,
    onRemoveEventFromFavorites: (event: SportEvent) -> Unit,
    onFilterFavorites: (sportId: String, shouldFilter: Boolean) -> Unit,
    onStartCountDown: (startTime: Int) -> Flow<Int>
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var favoriteSwitchState by rememberSaveable { mutableStateOf(isSwitchActive) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(top = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier
                .weight(1F)
                .padding(start = 6.dp)) {
                FilledCircleIcon()
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = sport.name,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Switch(
                checked = favoriteSwitchState,
                colors = SwitchDefaults.colors().copy(
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedIconColor = MaterialTheme.colorScheme.tertiary,
                    uncheckedIconColor = Color.White,
                    uncheckedThumbColor = Color.Gray
                ),
                thumbContent = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onCheckedChange = {
                    favoriteSwitchState = !favoriteSwitchState
                    onFilterFavorites.invoke(sport.id, favoriteSwitchState)
                }
            )

            ExpandableChevronIconToggleButton(
                isExpanded = isExpanded,
                onClick = { isExpanded = !isExpanded }
            )
        }

        if (isExpanded) {
            if (sport.activeEvents.isNotEmpty()) {
                TwoColumnSportEventsGrid(
                    items = sport.activeEvents,
                    onAddEventToFavorites = onAddEventToFavorites,
                    onRemoveEventFromFavorites = onRemoveEventFromFavorites,
                    onStartCountDown = onStartCountDown
                )
            } else {
                NoActiveEventsPlaceholder()
            }
        }
    }
}

@Composable
private fun TwoColumnSportEventsGrid(
    items: List<SportEvent>,
    onAddEventToFavorites: (eventId: String) -> Unit,
    onRemoveEventFromFavorites: (event: SportEvent) -> Unit,
    onStartCountDown: (startTime: Int) -> Flow<Int>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        for (i in items.indices step 2) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val item1 = items.getOrNull(i)
                val item2 = items.getOrNull(i + 1)

                item1?.let {
                    ActiveSportEventItem(
                        activeSportEvent = it,
                        onAddEventToFavorites = onAddEventToFavorites,
                        onRemoveEventFromFavorites = onRemoveEventFromFavorites,
                        onStartCountDown = onStartCountDown,
                        modifier = Modifier.weight(1f)
                    )
                }

                item2?.let {
                    ActiveSportEventItem(
                        activeSportEvent = it,
                        onAddEventToFavorites = onAddEventToFavorites,
                        onRemoveEventFromFavorites = onRemoveEventFromFavorites,
                        onStartCountDown = onStartCountDown,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (item2==null) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Space between rows
        }
    }
}

@Composable
private fun ActiveSportEventItem(
    activeSportEvent: SportEvent,
    onAddEventToFavorites: (eventId: String) -> Unit,
    onRemoveEventFromFavorites: (event: SportEvent) -> Unit,
    onStartCountDown: (startTime: Int) -> Flow<Int>,
    modifier: Modifier
) {
    var countdownSeconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        onStartCountDown(activeSportEvent.remainingSecondsToStart).collect {
            countdownSeconds = it
        }
    }
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .padding(8.dp)
            .width(190.dp)
            .height(200.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatTime(totalSeconds = countdownSeconds),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = activeSportEvent.competitors.first,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = "VS",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = activeSportEvent.competitors.second,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )

            IconButton(onClick = {
                if (activeSportEvent.isFavorite) onRemoveEventFromFavorites.invoke(activeSportEvent)
                else onAddEventToFavorites.invoke(activeSportEvent.id)
            }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = if (activeSportEvent.isFavorite) MaterialTheme.colorScheme.primary else Color.Gray,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingPlaceholder() {
    Row(Modifier.padding(top = 15.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .shimmerEffect()
        )
    }
}

@Composable
private fun NoActiveEventsPlaceholder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "There is no active events!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

private fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "%02dh:%02dm:%02ds".format(hours, minutes, seconds)
}
