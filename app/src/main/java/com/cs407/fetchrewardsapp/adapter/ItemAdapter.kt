// ItemAdapter.kt
// In this file, I create a RecyclerView adapter to display a list of items.
// I use view binding (ItemRowBinding) to easily access views from the layout without using findViewById.

package com.cs407.fetchrewardsapp

import com.cs407.fetchrewardsapp.databinding.ItemRowBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs407.fetchrewardsapp.model.Item

// The adapter takes a list of Item objects and extends RecyclerView.Adapter.
class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // I define an inner ViewHolder class that holds the binding for each item row.
    inner class ItemViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // The bind function assigns data from an Item object to the views.
        fun bind(item: Item) {
            // used string interpolation.
            binding.listId.text = "List ID: ${item.listId}"
            binding.name.text = "Item: ${item.name}"
        }
    }

    // onCreateViewHolder inflates the layout for a single row using the generated binding class.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // onBindViewHolder calls bind() on the ViewHolder to display data at the given position.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // getItemCount returns the total number of items in the list.
    override fun getItemCount() = items.size
}
