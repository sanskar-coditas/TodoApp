package com.example.notesapp.repository

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.example.notesapp.LoggerTodo
import com.example.notesapp.constants.Constants
import com.example.notesapp.dataclasses.TaskOfNote
import com.example.notesapp.dataclasses.User
import com.example.notesapp.viewmodels.MainViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger

class TaskInOpRepo{
    private lateinit var database : DatabaseReference
    lateinit var mcallback : SomeCallbackInterface
    lateinit var mcallbackDelete : SomeCallbackDelete
    interface SomeCallbackInterface{

        fun onSuccess()
        fun onFailure()

    }
    fun initOnClickInterface(callback: SomeCallbackInterface) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallback = callback

    }

    fun insertingData(taskOfNote: TaskOfNote) {
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        val user = User(
            taskOfNote.titleofnote,
            taskOfNote.discription,
            taskOfNote.idForNote,
            taskOfNote.doneNot
        )

        if (!taskOfNote.noteType.equals(Constants.EDIT)) {

            if (!TextUtils.isEmpty(taskOfNote.titleofnote) && !TextUtils.isEmpty(taskOfNote.discription)) {
                database.child(taskOfNote.idForNote.toString()).setValue(user)
                    .addOnSuccessListener {


                        LoggerTodo.logInfo("callbackdata mcallback.onSuccess()")
                        //mcallback = callback
                        //LoggerTodo.logError("callback result")
                        mcallback.onSuccess()



                    }.addOnFailureListener {
                    LoggerTodo.logError("onFailure message")

                            mcallback.onFailure()


                    // Toast.makeText(taskOfNote.context, Constants.FAILED_SAVE_MSG, Toast.LENGTH_SHORT).show()
                }

            }

        } else {
            val note = mapOf(
                Constants.TITLE_OF_TASK to taskOfNote.titleofnote,
                Constants.DISCRIPTION_OF_TASK to taskOfNote.discription,
                Constants.ID_OF_TASK to taskOfNote.noteidp
            )

            taskOfNote.noteidp?.let { it1 ->
                database.child(it1).updateChildren(note).addOnSuccessListener {
                    mcallback.onSuccess()

                }.addOnFailureListener {
                    mcallback.onFailure()
                    //Toast.makeText(taskOfNote.context, Constants.FAILED_UPDATE_MSG, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }




    interface SomeCallbackDelete{
        fun onSuccess()
        fun onFailure()
    }
    fun initOnClickDelete(callbackDelete: SomeCallbackDelete) {

        mcallbackDelete = callbackDelete
        LoggerTodo.logError("callback result")
    }

    fun deletingData(idOfNote: String){
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)

            database.child(idOfNote).removeValue().addOnSuccessListener {

                mcallbackDelete.onSuccess()

                LoggerTodo.logInfo("Task Deleted")
            }.addOnFailureListener {

                mcallbackDelete.onFailure()

                LoggerTodo.logInfo("Task Deletion Failed")


        }

    }


}