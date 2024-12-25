package com.example.navigationtest.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigationtest.model.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemsRepository: ItemsRepository):ViewModel(){


    val stateFlow:StateFlow<ScreenState> = itemsRepository.getItems()
        .map { ScreenState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ScreenState.Loading
        )


    sealed class ScreenState{
        data object Loading:ScreenState()
        data class  Success(val items:List<String>):ScreenState()
    }
}