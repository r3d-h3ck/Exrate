package com.example.exrate.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

interface Item {
    @LayoutRes
    fun getLayoutRes(): Int
    fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): Adapter.ViewHolder
    fun onBindViewHolder(binding: ViewBinding)
}