package com.example.navigationtest.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepository @Inject constructor(@ApplicationContext private val context: Context){

    private val itemsFlow = MutableStateFlow(List(size = 5){"Item ${it + 1}"})

    suspend fun add(title:String){
        delay(2000)
        itemsFlow.update { it + title }
    }

    fun getItems(): Flow<List<String>>{
        return itemsFlow.onStart { delay(3000) }
    }

    suspend fun getItemByIndex (index: Int): String {
        delay(1000)
        return itemsFlow.value[index]
    }

    suspend fun update(index:Int, newItem:String){
        delay(2000)
        itemsFlow.update {oldest-> oldest.toMutableList().apply {  set(index,newItem)} }
    }
}