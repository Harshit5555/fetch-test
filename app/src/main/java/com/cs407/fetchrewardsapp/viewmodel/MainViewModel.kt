// MainViewModel.kt
// This ViewModel handles fetching and processing data for the UI.
// It uses Kotlin coroutines and StateFlow to asynchronously load data and expose it as a stream for the UI to observe.
package com.cs407.fetchrewardsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs407.fetchrewardsapp.model.Item
import com.cs407.fetchrewardsapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // _itemList holds the list of items and is mutable within the ViewModel.
    // I expose it as an immutable StateFlow (itemList) to the UI.
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList: StateFlow<List<Item>> = _itemList

    // This function fetches items from the API using Retrofit.
    // It uses viewModelScope.launch to perform the network request asynchronously.
    fun fetchItems() {
        viewModelScope.launch {
            // Make the network call to fetch items.
            val response = RetrofitInstance.api.getItems()
            if (response.isSuccessful) {
                // Filter out items with null or blank names and sort them by listId and name.
                val sortedFilteredList = response.body()
                    ?.filter { !it.name.isNullOrBlank() }
                    ?.sortedWith(compareBy({ it.listId }, { it.name }))

                // Update the StateFlow so that the UI recomposes with the new data.
                _itemList.value = sortedFilteredList ?: emptyList()
            }
        }
    }
}
