package com.example.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.GetDataRepo


class TodoViewModelFactory(private val getDataRepo: GetDataRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return TodoViewModel(getDataRepo)  as T
    }
}