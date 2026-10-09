package com.example.listycity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    onDeleteCity: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }
    var editedCityName by remember { mutableStateOf("") }
    var editedProvinceName by remember { mutableStateOf("") }
    var isDeletingCities by remember { mutableStateOf(false) }
    var deleteSymbol by remember { mutableStateOf("Delete") }
    var deleteButtonColor by remember { mutableStateOf(Color(0xFFAD5745)) }
    var showDeletePopup by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                    if (showAddCityFields) {
                        selectedCity = null
                        editedCityName = ""
                        editedProvinceName = ""
                    }
                }
            ) {
                Text("+")
            }

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                containerColor = deleteButtonColor,
                onClick = {
                    isDeletingCities = !isDeletingCities

                    showAddCityFields = false
                    selectedCity = null
                    showDeletePopup = false

                    editedCityName = ""
                    editedProvinceName = ""

                    if (isDeletingCities) {
                        deleteButtonColor = Color(0xFFB7D162)
                        deleteSymbol = "Cancel"
                    } else {
                        deleteButtonColor = Color(0xFFAD5745)
                        deleteSymbol = "Delete"
                    }
                }
            ) {
                Text(
                    text = deleteSymbol,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        if (showAddCityFields && !isDeletingCities) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("ADD CITY")
                }
            }
        }
        if (selectedCity != null && !isDeletingCities) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = editedCityName,
                    onValueChange = { editedCityName = it },
                    label = { Text("Updated City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = editedProvinceName,
                    onValueChange = { editedProvinceName = it },
                    label = { Text("Updated Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        val cityToUpdate = selectedCity
                        if (
                            cityToUpdate != null &&
                            editedCityName.isNotBlank() &&
                            editedProvinceName.isNotBlank()
                        ) {
                            onUpdateCity(
                                cityToUpdate,
                                City(
                                    documentID = cityToUpdate.documentID,
                                    name = editedCityName,
                                    province = editedProvinceName
                                )
                            )

                            selectedCity = null
                            editedCityName = ""
                            editedProvinceName = ""
                        }
                    }
                ) {
                    Text("UPDATE CITY")
                }
            }
        }
        if (showDeletePopup && selectedCity != null) {
            BasicAlertDialog(
                onDismissRequest = {
                    showDeletePopup = false
                    selectedCity = null
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(25.dp)
                ) {
                    Text(
                        text = "Delete City and Province",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Are you sure you want to delete ${selectedCity?.name}, ${selectedCity?.province}?")

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(
                            onClick = {
                                showDeletePopup = false
                                selectedCity = null
                            }
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(
                            onClick = {
                                /**
                                 * The following code used to pass a nullable type to non-nullable
                                 * parameter was made by Kotlin Documentation at
                                 * https://kotlinlang.org/docs/null-safety.html#safe-casts
                                 */
                                selectedCity?.let { onDeleteCity(it) }
                                showDeletePopup = false
                                selectedCity = null
                            }
                        ) {
                            Text(
                                text = "Confirm",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    onClick = {
                        showAddCityFields = false
                        newCityName = ""
                        newProvinceName = ""
                        selectedCity = city

                        if (isDeletingCities) {
                            showDeletePopup = true

                        } else {
                            editedCityName = city.name
                            editedProvinceName = city.province
                        }
                    }
                )
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCityTheme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> },
            onDeleteCity = {}
        )
    }
}