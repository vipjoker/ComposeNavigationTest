package com.example.navigationtest.screens.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationtest.EventConsumer
import com.example.navigationtest.screens.EditItemRoute
import com.example.navigationtest.screens.LocalNavController
import com.example.navigationtest.screens.routeClass

@Composable
fun EditItemScreen(index: Int) {

    val viewModel: EditItemViewModel =
        hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
            factory.create(index)
        }

    val navController = LocalNavController.current
    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routeClass() == EditItemRoute::class) {
            navController.popBackStack()
        }
    }

    val currentState by viewModel.stateFlow.collectAsState()
    EditItemContent(state = currentState, onEditButtonClicked = { newValue ->
        viewModel.update(newValue)
    })


}

@Composable
fun EditItemContent(state: EditItemViewModel.ScreenState, onEditButtonClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            EditItemViewModel.ScreenState.Loading -> {

                    CircularProgressIndicator()
            }

            is EditItemViewModel.ScreenState.Success -> {


                var text by rememberSaveable { mutableStateOf(state.item) }
                OutlinedTextField(value = text, onValueChange = {text = it},
                    placeholder = { Text("Update value") },
                    enabled = !state.isEditInProgress

                )
                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    enabled = text.isNotBlank() && !state.isEditInProgress,
                    onClick = { onEditButtonClicked(text) }) {
                    Text(text = "Update")

                }

                Box(modifier = Modifier.size(32.dp)){

                    if(state.isEditInProgress){
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    }
                }


            }
        }
    }


}