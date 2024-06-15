package xyz.leomurca.sporteventtracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    sportEventsRepository: SportEventsRepository
) : ViewModel() {
    val uiState: StateFlow<UiState> =
        sportEventsRepository.fetchSports().map {
            UiState.Loaded(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    sealed interface UiState {
        data object Loading : UiState

        data class Loaded(val sports: List<Sport>) : UiState
    }
}