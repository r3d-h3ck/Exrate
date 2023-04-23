package com.example.exrate.data

import retrofit2.http.GET

interface CoingeckoService {
    @GET("coins/markets?vs_currency=usd&per_page=50")
    suspend fun markets(): List<CoinResponse>
}