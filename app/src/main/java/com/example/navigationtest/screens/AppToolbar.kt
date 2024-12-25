package com.example.navigationtest.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed class NavigateUpAction{
    data object Hidden: NavigateUpAction()
    data class  Visible(val onClick:()->Unit):NavigateUpAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(@StringRes titleRes:Int,
               navigateUpActoin:NavigateUpAction) {

    CenterAlignedTopAppBar(title = { Text(stringResource(titleRes)) },
        navigationIcon = {
            if(navigateUpActoin is NavigateUpAction.Visible){
                IconButton(onClick = navigateUpActoin.onClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }


        )
}