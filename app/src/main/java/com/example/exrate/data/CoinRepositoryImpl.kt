package com.example.exrate.data

import com.example.exrate.domain.Coin
import com.example.exrate.domain.CoinRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CoinRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val service: CoingeckoService
) : CoinRepository {

    @Volatile
    private var coinList: List<Coin>? = null

    override suspend fun getCoinList(): Result<List<Coin>> {
        return withContext(dispatcher) {
            runCatching {
                val coins = service.markets().mapIndexed(::createCoin)
                coinList = coins
                coins
            }
        }
    }

    override suspend fun getCoin(id: Int): Result<Coin> {
        return withContext(dispatcher) {
            runCatching {
                coinList?.get(id) ?: throw IllegalArgumentException("There is no coin with such id")
            }
        }
    }

    private fun createCoin(index: Int, response: CoinResponse): Coin {
        return with(response) {
            Coin(
                id = index,
                name = symbol,
                currentPrice = currentPrice,
                priceChange = priceChangePercentage24h,
                marketCap = marketCap
            )
        }
    }

}