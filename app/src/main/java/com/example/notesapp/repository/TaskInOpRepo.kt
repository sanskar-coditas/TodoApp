package com.example.notesapp.repository

import android.text.TextUtils
import com.example.notesapp.LoggerTodo
import com.example.notesapp.constants.Constants
import com.example.notesapp.dataclasses.TaskOfNote
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TaskInOpRepo {
    private lateinit var database: DatabaseReference
    lateinit var mcallback: CallbackInterfaceInsert
    lateinit var mcallbackDelete: SomeCallbackDelete

    interface CallbackInterfaceInsert {

        fun onSuccess()
        fun onFailure()

    }

    fun initOnClickInterface(callback: CallbackInterfaceInsert) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallback = callback

    }

    fun insertingData(taskOfNote: TaskOfNote) {
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        val user = User(
            taskOfNote.titleOfNote,
            taskOfNote.description,
            taskOfNote.idForNote,
            taskOfNote.doneNot,
            taskOfNote.priorityOfTask

        )

        if (!taskOfNote.noteType.equals(Constants.EDIT)) {

            if (!TextUtils.isEmpty(taskOfNote.titleOfNote) && !TextUtils.isEmpty(taskOfNote.description)) {
                database.child(taskOfNote.idForNote.toString()).setValue(user)
                    .addOnSuccessListener {
                        LoggerTodo.logInfo("callbackdata mcallback.onSuccess()")
                        mcallback.onSuccess()


                    }.addOnFailureListener {
                        LoggerTodo.logError("onFailure message")

                        mcallback.onFailure()

                    }

            }

        } else {
            val note = mapOf(
                Constants.TITLE_OF_TASK to taskOfNote.titleOfNote,
                Constants.DESCRIPTION_OF_TASK to taskOfNote.description,
                Constants.PRIORITY_OF_NOTE to taskOfNote.priorityOfTask,
                Constants.ID_OF_TASK to taskOfNote.noteIdp
            )

            taskOfNote.noteIdp?.let { it1 ->
                database.child(it1).updateChildren(note).addOnSuccessListener {
                    mcallback.onSuccess()

                }.addOnFailureListener {
                    mcallback.onFailure()

                }
            }

        }

    }


    interface SomeCallbackDelete {
        fun onSuccess()
        fun onFailure()
    }

    fun initOnClickDelete(callbackDelete: SomeCallbackDelete) {

        mcallbackDelete = callbackDelete
        LoggerTodo.logError("callback result")
    }

    fun deletingData(idOfNote: String) {
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