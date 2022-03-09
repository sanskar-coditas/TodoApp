package com.example.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repository.TodoActivityRepository

class TodoViewModelFactory(private val todoActivityRepository: TodoActivityRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return TodoViewModel(todoActivityRepository)  as T
    }
}