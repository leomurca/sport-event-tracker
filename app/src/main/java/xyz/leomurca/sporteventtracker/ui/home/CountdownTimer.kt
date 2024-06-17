package xyz.leomurca.sporteventtracker.ui.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive

class CountdownTimer {
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

    fun cancelCountdown() {
        countdownJob?.cancel()
    }
}