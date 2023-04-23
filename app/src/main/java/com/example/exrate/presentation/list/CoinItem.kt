package com.example.exrate.presentation.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.exrate.R
import com.example.exrate.databinding.ItemCoinBinding
import com.example.exrate.domain.Coin
import com.example.exrate.presentation.recycler.Adapter
import com.example.exrate.presentation.recycler.Item
import kotlin.math.abs

class CoinItem(
    private val coin: Coin,
    private val clickListener: View.OnClickListener
) : Item {

    override fun getLayoutRes(): Int {
        return R.layout.item_coin
    }

    override fun onCreateViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): Adapter.ViewHolder {
        val binding = ItemCoinBinding.inflate(layoutInflater, parent, false)
        return Adapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(binding: ViewBinding) {
        if (binding !is ItemCoinBinding) return
        with(coin) {
            val indexValue = "${id + 1}"
            binding.index.text = indexValue
            binding.name.text = name
            val priceValue = "$$currentPrice"
            binding.price.text = priceValue
            with(binding.priceChange) {
                val priceChangeValue = "%${String.format("%.1f", abs(priceChange))}"
                text = priceChangeValue
                val priceChangeColor = if (priceChange > 0) Color.GREEN else Color.RED
                setTextColor(priceChangeColor)
            }
            val marketCapValue = "$${String.format("%,d", marketCap)}"
            binding.marketCap.text = marketCapValue
        }
        binding.root.setOnClickListener(clickListener)
    }

}