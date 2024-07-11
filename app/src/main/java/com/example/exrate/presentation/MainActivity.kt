package com.example.exrate.presentation

import androidx.appcompat.app.AppCompatActivity
import com.example.exrate.R
import com.example.exrate.presentation.details.DetailsFragment
import com.example.exrate.presentation.navigation.Navigator
import com.example.exrate.presentation.navigation.Screen

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {
    override fun navigateTo(screen: Screen) {
        if (screen is Screen.Details) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.root, DetailsFragment.newInstance(screen.coinId))
                .addToBackStack(null)
                .commit()
        }
    }
}