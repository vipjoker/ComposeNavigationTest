package com.example.navigationtest.screens.add

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationtest.EventConsumer
import com.example.navigationtest.R
import com.example.navigationtest.screens.AddItemRoute
import com.example.navigationtest.screens.LocalNavController
import com.example.navigationtest.screens.routeClass

@Preview(showSystemUi = true)
@Composable
fun AddItemScreen() {

    val viewModel: AddItemViewModel = hiltViewModel()
    val screenState by viewModel.stateFlow.collectAsState()

    AddItemContent(screenState = screenState, onAddButtonClicked = viewModel::add)

    val navController = LocalNavController.current
    EventConsumer(viewModel.exitChannel) {
        if(navController.currentBackStackEntry?.routeClass()== AddItemRoute::class){
            navController.popBackStack()
        }
    }
}

@Composable
fun AddItemContent(screenState: AddItemViewModel.ScreenState, onAddButtonClicked:(String)->Unit){


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var inputText by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = inputText,
            onValueChange = {value ->
            inputText = value
            },
            placeholder = { Text(stringResource(R.string.enter_new_item)) },
            enabled = screenState.isTextInputEnabled


        )


        Spacer(Modifier.height(10.dp))
        OutlinedButton(onClick = {onAddButtonClicked(inputText)}, enabled = screenState.isAddButtonEnabled(inputText)) {
            Text(stringResource(R.string.add))
        }

        Box(modifier = Modifier.size(32.dp)){
            if(screenState.isProgressActive){
                CircularProgressIndicator(Modifier.fillMaxSize())
            }
        }

    }

}