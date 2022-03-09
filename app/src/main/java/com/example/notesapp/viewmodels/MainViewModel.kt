package com.example.notesapp.viewmodels


import androidx.lifecycle.ViewModel
import com.example.notesapp.LoggerTodo
import com.example.notesapp.dataclasses.TaskOfNote

import com.example.notesapp.repository.TaskInOpRepo

class MainViewModel(private val taskInOpRepo: TaskInOpRepo):ViewModel() {

    fun callbackData(mCallBackData:TaskInOpRepo.SomeCallbackInterface){
        LoggerTodo.logInfo("callbackdata mainviewmodel ")
       return taskInOpRepo.initOnClickInterface(mCallBackData)
    }

    fun callbackDataDelete(mCallBackDataDelete:TaskInOpRepo.SomeCallbackDelete){

        return taskInOpRepo.initOnClickDelete(mCallBackDataDelete)
    }
    fun insertData (taskOfNote: TaskOfNote)
    {
        return taskInOpRepo.insertingData(taskOfNote)
    }
    fun deleteData(noteID:String)
    {
        return taskInOpRepo.deletingData(noteID)
    }


}