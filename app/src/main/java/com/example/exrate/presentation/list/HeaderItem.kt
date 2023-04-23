package com.example.exrate.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.exrate.R
import com.example.exrate.databinding.ItemHeaderBinding
import com.example.exrate.presentation.recycler.Adapter
import com.example.exrate.presentation.recycler.Item

class HeaderItem : Item {

    override fun getLayoutRes(): Int {
        return R.layout.item_header
    }

    override fun onCreateViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): Adapter.ViewHolder {
        val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
        return Adapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(binding: ViewBinding) {}

}