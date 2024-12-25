package com.example.navigationtest.screens.items

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationtest.screens.EditItemRoute
import com.example.navigationtest.screens.LocalNavController

@Composable
fun ItemsScreen(){
    val viewModel:ItemsViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()
    val navController = LocalNavController.current
    ItemsContent(getScreenState = {screenState.value}, onItemsClicked = {
        navController.navigate(EditItemRoute(it))
    })
}

@Composable
fun ItemsContent(getScreenState: ()-> ItemsViewModel.ScreenState,
                 onItemsClicked: (Int)->Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)){

        when(val screenState = getScreenState()){
            ItemsViewModel.ScreenState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is ItemsViewModel.ScreenState.Success ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(screenState.items){index,item->
                    Text(text = item,

                    modifier = Modifier.fillMaxSize().padding(12.dp).clickable { onItemsClicked(index) })
                }
            }
        }
    }
}

//@Preview(showSystemUi = true)
@Composable
fun ItemsScreenPreview(){
    ItemsScreen ()
}