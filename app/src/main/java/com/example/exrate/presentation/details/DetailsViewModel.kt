package com.example.exrate.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exrate.domain.Coin
import com.example.exrate.domain.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailsViewModel(
    coinId: Int,
    private val repository: CoinRepository
) : ViewModel() {

    private val state = MutableStateFlow(State())
    val coinName: Flow<String> = coinNameFlow()
    val error: Flow<Unit> = errorFlow()

    init {
        viewModelScope.launch {
            repository.getCoin(coinId)
                .onSuccess(::onGetCoinSuccess)
                .onFailure(::onGetCoinFailure)
        }
    }

    private fun onGetCoinSuccess(coin: Coin) {
        state.value = State(
            coinName = coin.name,
            error = false
        )
    }

    private fun onGetCoinFailure(throwable: Throwable) {
        state.value = State(
            coinName = "",
            error = true
        )
    }

    private fun coinNameFlow(): Flow<String> {
        return state.map { it.coinName }.distinctUntilChanged()
    }

    private fun errorFlow(): Flow<Unit> {
        return state.filter { it.error }
            .map { Unit }
            .distinctUntilChanged()
    }

    private class State(
        val coinName: String = "",
        val error: Boolean = false
    )

}