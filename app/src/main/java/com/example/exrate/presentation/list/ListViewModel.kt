package com.example.exrate.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exrate.domain.Coin
import com.example.exrate.domain.CoinRepository
import com.example.exrate.presentation.recycler.Item
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: CoinRepository
) : ViewModel() {

    private val state = MutableStateFlow(State())
    private val events = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val items: Flow<List<Item>> = itemsFlow()
    val loading: Flow<Boolean> = loadingFlow()
    val error: Flow<Boolean> = errorFlow()
    val openDetails: Flow<Event.OpenDetails> = events.filterIsInstance()

    init {
        viewModelScope.launch {
            repository.getCoinList()
                .onSuccess(::onGetCoinListSuccess)
                .onFailure(::onGetCoinListFailure)
        }
    }

    private fun onGetCoinListSuccess(coinList: List<Coin>) {
        val items = buildList {
            val headerItem = HeaderItem()
            add(headerItem)
            val coinToItem = { coin: Coin ->
                CoinItem(
                    coin = coin,
                    clickListener = {
                        val event = Event.OpenDetails(coin.id)
                        events.tryEmit(event)
                    }
                )
            }
            val coinItemList = coinList.map(coinToItem)
            addAll(coinItemList)
        }
        state.value = State(
            items = items,
            loading = false,
            error = false
        )
    }

    private fun onGetCoinListFailure(throwable: Throwable) {
        state.value = State(
            items = listOf(),
            loading = false,
            error = true
        )
    }

    private fun itemsFlow(): Flow<List<Item>> {
        return state.map { it.items }.distinctUntilChanged()
    }

    private fun loadingFlow(): Flow<Boolean> {
        return state.map { it.loading }.distinctUntilChanged()
    }

    private fun errorFlow(): Flow<Boolean> {
        return state.map { it.error }.distinctUntilChanged()
    }

    private class State(
        val items: List<Item> = listOf(),
        val loading: Boolean = true,
        val error: Boolean = false
    )

    sealed class Event {
        class OpenDetails(val coinId: Int) : Event()
    }

}