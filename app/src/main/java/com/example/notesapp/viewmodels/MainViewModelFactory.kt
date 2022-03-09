package com.example.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.notesapp.repository.TaskInOpRepo

class MainViewModelFactory(private val taskInOpRepo: TaskInOpRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(taskInOpRepo) as T
    }



}