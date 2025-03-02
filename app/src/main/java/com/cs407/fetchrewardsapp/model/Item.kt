// Item.kt
// This data class represents a single item as received from the API.
// It includes an id, a listId (to group items), and a name which may be null.
package com.cs407.fetchrewardsapp.model

data class Item(
    val id: Int,      // Unique identifier for the item
    val listId: Int,  // Identifier used to group items together
    val name: String? // Name of the item; it can be null if not provided
)
