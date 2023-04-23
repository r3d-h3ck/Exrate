package com.example.exrate.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CoinResponse(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("current_price")
    val currentPrice: Double,
    @SerialName("market_cap")
    val marketCap: Long,
    @SerialName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
)