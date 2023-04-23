package com.example.exrate.domain

interface CoinRepository {
    suspend fun getCoinList(): Result<List<Coin>>
    suspend fun getCoin(id: Int): Result<Coin>
}