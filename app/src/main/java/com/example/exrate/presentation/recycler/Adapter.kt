package com.example.exrate.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var items: List<Item> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = items.first { it.getLayoutRes() == viewType }
        return item.onCreateViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].onBindViewHolder(holder.binding)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getLayoutRes()
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

}