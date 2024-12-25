package com.example.navigationtest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.navigationtest.model.ItemsRepository
import com.example.navigationtest.screens.AddItemRoute
import com.example.navigationtest.screens.AppToolbar
import com.example.navigationtest.screens.EditItemRoute
import com.example.navigationtest.screens.ItemsRoute
import com.example.navigationtest.screens.LocalNavController
import com.example.navigationtest.screens.NavigateUpAction
import com.example.navigationtest.screens.add.AddItemScreen
import com.example.navigationtest.screens.edit.EditItemScreen
import com.example.navigationtest.screens.items.ItemsScreen
import com.example.navigationtest.screens.routeClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var itemsRepository: ItemsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {

            NavApp()
        }


    }


}

@Composable
fun NavApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val titleRes = when (currentBackStackEntry?.routeClass()) {

        ItemsRoute::class -> R.string.items
        AddItemRoute::class -> R.string.add_item
        EditItemRoute::class -> R.string.edit_item
        else -> R.string.app_name
    }
    Scaffold(
        topBar = {
            AppToolbar(
                navigateUpActoin = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.Hidden
                } else {
                    NavigateUpAction.Visible(onClick = { navController.navigateUp() })
                },
                titleRes = titleRes
            )


        },

        floatingActionButton = {

            if (currentBackStackEntry?.routeClass() == ItemsRoute::class) {
                FloatingActionButton(onClick = { navController.navigate(AddItemRoute) })
                { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") }
            }
        }
    ) { paddingValues ->

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = ItemsRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Yellow)
            ) {
                composable<ItemsRoute> { ItemsScreen() }
                composable<AddItemRoute> { AddItemScreen() }
                composable<EditItemRoute> { entry ->
                    val route: EditItemRoute = entry.toRoute()
                    EditItemScreen(route.index)
                }

            }
        }

    }

}

