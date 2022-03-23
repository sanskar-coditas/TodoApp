package com.example.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notesapp.LoggerTodo
import com.example.notesapp.dataclasses.User
import com.example.notesapp.repository.GetDataRepo
import java.util.ArrayList

class TodoViewModel(private val getDataRepo: GetDataRepo) : ViewModel() {
    fun gettingData(userArrayList: ArrayList<User>) {
        return getDataRepo.getData(userArrayList)
    }


    fun callbackGettingData(mCallBackData: GetDataRepo.CallbackInterfaceGet) {

        LoggerTodo.logInfo("callbackdata TODOmainviewmodel ")
        return getDataRepo.initOnClickInterface(mCallBackData)

    }

    fun gettingDataCompleted(userArrayListDone: ArrayList<User>) {
        return getDataRepo.getDataCompleted(userArrayListDone)
    }


    fun callbackGettingDataCompleted(mCallBackDataCompleted: GetDataRepo.CallbackInterfaceGetCompleted) {

        LoggerTodo.logInfo("callbackdata TODOmainviewmodel ")
        return getDataRepo.initOnClickInterfaceCompleted(mCallBackDataCompleted)

    }


}