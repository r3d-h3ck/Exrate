package com.example.exrate.domain

class Coin(
    val id: Int,
    val name: String,
    val currentPrice: Double,
    val priceChange: Double,
    val marketCap: Long,
)