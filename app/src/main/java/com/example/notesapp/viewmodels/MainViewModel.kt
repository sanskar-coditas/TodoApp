package com.example.notesapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.notesapp.dataclasses.DataAll
import com.example.notesapp.repository.MainActivityRepository

class MainViewModel(private val mainActivityRepository: MainActivityRepository):ViewModel() {
    fun insertData (dataAll: DataAll)
    {
        return mainActivityRepository.insertingData(dataAll)
    }
    fun deleteData(context: Context,noteID:String)
    {
        return mainActivityRepository.deletingData(context,noteID)
    }

}