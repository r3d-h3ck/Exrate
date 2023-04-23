package com.example.exrate.presentation.navigation

sealed class Screen {
    class Details(val coinId: Int) : Screen()
}