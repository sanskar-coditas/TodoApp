package com.example.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notesapp.LoggerTodo
import com.example.notesapp.databinding.ActivityTodoListBinding
import com.example.notesapp.dataclasses.User
import com.example.notesapp.repository.TaskInOpRepo
import com.example.notesapp.repository.TodoActivityRepository
import java.util.ArrayList

class TodoViewModel(private val todoActivityRepository:TodoActivityRepository):ViewModel()
{
    fun gettingData(userArrayList : ArrayList<User>, count: Int, binding: ActivityTodoListBinding){
        return todoActivityRepository.getData(userArrayList,count,binding)
    }
    fun callbackGettingData(mCallBackData:TodoActivityRepository.SomeCallbackInterface){
        LoggerTodo.logInfo("callbackdata TODOmainviewmodel ")
        return todoActivityRepository.initOnClickInterface(mCallBackData)

    }
}