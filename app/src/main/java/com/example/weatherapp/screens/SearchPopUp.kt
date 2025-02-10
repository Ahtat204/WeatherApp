package com.example.weatherapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(city: MutableState<String>) {
    val showDialog = remember { mutableStateOf(false) }
    IconButton(onClick = { showDialog.value = true }) {
        Icon(Icons.Default.Search, contentDescription = "Search")
    }
    if (showDialog.value) {
        CitySearchPopup(
            cityState = city,
            showDialog = showDialog
            ,onSearch = {}
        )
    }
}


@Composable
fun CitySearchPopup(
    cityState: MutableState<String>,
    showDialog: MutableState<Boolean>,
onSearch: (String) -> Unit //
) {
    var searchInput by remember { mutableStateOf(cityState.value) }
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text("Search City") },
        text = {
            Column {
              // Search query state

                // OutlinedTextField to input city name
                OutlinedTextField(
                    value = searchInput, // Bind the searchQuery to the text field
                    onValueChange = { searchInput = it }, // Update the search query as the user types
                    label = { Text("Enter city name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Button to submit the search query
                Button(
                    onClick = {
                        cityState.value = searchInput // Update the city state when submitted
                        showDialog.value = false// Close the popup
                        onSearch(searchInput)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Search")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Close button
                Button(
                    onClick = { showDialog.value = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        },
        confirmButton = {}
    )
}