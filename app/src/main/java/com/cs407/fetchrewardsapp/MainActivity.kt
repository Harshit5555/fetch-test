// MainActivity.kt
// I built this file using Jetpack Compose to define the UI in code instead of XML.
// I chose Compose because it makes UI development more declarative and concise,
// and it integrates nicely with modern Android practices such as ViewModel and coroutines.

package com.cs407.fetchrewardsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs407.fetchrewardsapp.model.Item
import com.cs407.fetchrewardsapp.ui.theme.FetchRewardsAppTheme
import com.cs407.fetchrewardsapp.viewmodel.MainViewModel

// I defined a custom color 'fetchOrange' to reflect the Fetch Rewards branding.
val fetchOrange = Color(0xFFFF8300)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instead of using setContentView with an XML layout, I use setContent to load my composables.
        setContent {
            FetchRewardsApp()
        }
    }
}

// I annotate this composable with @OptIn to acknowledge that some Material 3 APIs are experimental.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FetchRewardsApp(viewModel: MainViewModel = viewModel()) {
    // I use my ViewModel to get the list of items. The StateFlow is collected as state so the UI updates when data changes.
    val items by viewModel.itemList.collectAsState(initial = emptyList())

    // I wrap the UI in my custom theme to ensure a consistent look and feel.
    FetchRewardsAppTheme {
        // I use Scaffold as the main structure to provide a top app bar and content area.
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        // I reference a string resource here instead of hardcoding text for better maintainability.
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = fetchOrange, // Using my custom color for branding.
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            // I use Surface to act as the background container for the content.
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // I check if the items list is empty. If it is, I show a loading spinner.
                if (items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = fetchOrange)
                    }
                } else {
                    // Once data is loaded, I display it in a LazyColumn, which is efficient for lists.
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items) { item ->
                            // For each item, I use my RewardItemCard composable to display it.
                            RewardItemCard(item)
                        }
                    }
                }
            }
        }
    }

    // I use LaunchedEffect to start fetching data asynchronously when this composable first appears.
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }
}

// This composable is responsible for displaying a single reward item in a card format.
@Composable
fun RewardItemCard(item: Item) {
    // I use a Card to create a container with rounded corners and elevation.
    Card(
        shape = RoundedCornerShape(12.dp), // Rounded edges for a modern design.
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Inside the card, I use a Column to arrange the texts vertically.
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant) // Background color from my theme.
                .padding(16.dp) // Padding inside the card.
        ) {
            // I display the list ID with accent styling using my fetchOrange color.
            Text(
                text = "List ID: ${item.listId}",
                style = MaterialTheme.typography.titleMedium,
                color = fetchOrange
            )
            Spacer(modifier = Modifier.height(4.dp)) // Small space between texts.
            // I display the item name. If it's null, I show "No Name".
            Text(
                text = item.name ?: "No Name",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
