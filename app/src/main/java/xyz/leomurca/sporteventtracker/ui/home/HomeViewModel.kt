package xyz.leomurca.sporteventtracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import xyz.leomurca.sporteventtracker.data.model.SportResult
import xyz.leomurca.sporteventtracker.data.repository.FavoriteRepository
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sportEventsRepository: SportEventsRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _activeSportSwitchesIds = MutableStateFlow<List<String>>(emptyList())
    val activeSportSwitchesIds = _activeSportSwitchesIds.asStateFlow()

    private var countdownJob: Job? = null
    fun startCountdown(totalSeconds: Int): Flow<Int> = flow {
        var remainingSeconds = totalSeconds
        while (remainingSeconds >= 0 && currentCoroutineContext().isActive) {
            emit(remainingSeconds)
            delay(1000)
            remainingSeconds--
        }
    }.onStart {
        emit(totalSeconds)
    }.onCompletion {
        countdownJob?.cancel()
    }.flowOn(Dispatchers.Default)

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }

    init {
        viewModelScope.launch {
            fetchSportsWithFavorites()
        }
    }

    fun filterFavorites(sportId: String, shouldFilter: Boolean) {
        viewModelScope.launch {
            if (shouldFilter) _activeSportSwitchesIds.value += sportId
            else _activeSportSwitchesIds.value = _activeSportSwitchesIds.value.filter { it!=sportId }
            _uiState.value = sportEventsRepository.fetchSports().combine(favoriteRepository.allFavorites) { sportsResult, favorites ->
                when (sportsResult) {
                    is SportResult.Success -> UiState.Loaded.Success(
                        sportsResult.data.filterSportsByActiveEventFavorites(favorites.map { it.id }, sportId)
                    )

                    is SportResult.Error -> UiState.Loaded.Error(sportsResult.message)
                }
            }.first()
        }
    }

    fun addFavorite(eventId: String) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(eventId)
            fetchSportsWithFavorites()
        }
    }

    fun removeFavorite(event: SportEvent) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(event.id)
            val isCurrentSportSwitchOn = _activeSportSwitchesIds.value.contains(event.sportId)
            if (isCurrentSportSwitchOn) filterFavorites(event.sportId, true)
            else fetchSportsWithFavorites()
        }
    }

    private suspend fun fetchSportsWithFavorites() {
        _uiState.value = sportEventsRepository.fetchSports().combine(favoriteRepository.allFavorites) { sportsResult, favorites ->
            when (sportsResult) {
                is SportResult.Success -> {
                    UiState.Loaded.Success(
                        sportsResult.data.sportsWithFavorites(favorites.map { it.id })
                    )
                }

                is SportResult.Error -> {
                    UiState.Loaded.Error(sportsResult.message)
                }
            }
        }.first()
    }

    private fun List<Sport>.filterSportsByActiveEventFavorites(favoriteIds: List<String>, sportId: String): List<Sport> {
        return map { sport ->
            val isCurrentSportSwitchOn = _activeSportSwitchesIds.value.contains(sport.id)
            val shouldFilterFavorites = (sport.id==sportId && isCurrentSportSwitchOn) || isCurrentSportSwitchOn
            if (shouldFilterFavorites) {
                sport.copyWithFavoritesOnly(favoriteIds)
            } else {
                sport.copyWithUpdatedEventFavorites(favoriteIds)
            }
        }
    }

    private fun List<Sport>.sportsWithFavorites(favoriteIds: List<String>): List<Sport> {
        return map { sport ->
            sport.copyWithUpdatedEventFavorites(favoriteIds)
        }
    }

    private fun Sport.copyWithUpdatedEventFavorites(favoriteIds: List<String>) = copy(
        activeEvents = activeEvents.map { event ->
            event.copy(
                isFavorite = favoriteIds.contains(event.id)
            )
        }
    )

    private fun Sport.copyWithFavoritesOnly(favoriteIds: List<String>) = copy(
        activeEvents = activeEvents.map { event ->
            event.copy(
                isFavorite = favoriteIds.contains(event.id)
            )
        }.favoritesOnly()
    )

    private fun List<SportEvent>.favoritesOnly() = filter { it.isFavorite }

    sealed interface UiState {
        data object Loading : UiState

        sealed class Loaded : UiState {
            data class Success(val sports: List<Sport>) : Loaded()
            data class Error(val message: String) : Loaded()
        }
    }
}