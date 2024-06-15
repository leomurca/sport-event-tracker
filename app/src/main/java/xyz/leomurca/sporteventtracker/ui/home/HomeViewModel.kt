package xyz.leomurca.sporteventtracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportResult
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    sportEventsRepository: SportEventsRepository
) : ViewModel() {

    val uiState: StateFlow<UiState> =
        sportEventsRepository.fetchSports().map {
            when (it) {
                is SportResult.Success -> UiState.Loaded.Success(it.data)
                is SportResult.Error -> UiState.Loaded.Error(it.message)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )

    sealed interface UiState {
        data object Loading : UiState

        sealed class Loaded : UiState {
            data class Success(val sports: List<Sport>) : Loaded()
            data class Error(val message: String) : Loaded()
        }
    }
}