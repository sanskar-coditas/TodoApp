package com.example.notesapp.repository

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.example.notesapp.LoggerTodo
import com.example.notesapp.constants.Constants
import com.example.notesapp.dataclasses.DataAll
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivityRepository {
    private lateinit var database : DatabaseReference


fun insertingData(dataAll: DataAll) {


    //val timetext = ServerValue.TIMESTAMP.toString()
    database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)

    val User = User(dataAll.titleofnote, dataAll.discription, dataAll.idForNote, dataAll.doneNot)
    if (!dataAll.noteType.equals(Constants.EDIT)) {

        if (!TextUtils.isEmpty(dataAll.titleofnote) && !TextUtils.isEmpty(dataAll.discription)) {
            database.child(dataAll.idForNote.toString()).setValue(User).addOnSuccessListener {


                Toast.makeText(dataAll.context, Constants.TASK_SAVED_MSG, Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(dataAll.context, Constants.FAILED_SAVE_MSG, Toast.LENGTH_SHORT).show()

            }

        }

    }
    else {
        val note = mapOf(
            Constants.TITLE_OF_TASK to dataAll.titleofnote,
            Constants.DISCRIPTION_OF_TASK to dataAll.discription,
            Constants.ID_OF_TASK to dataAll.noteidp

        )

        dataAll.noteidp?.let { it1 ->
            database.child(it1).updateChildren(note).addOnSuccessListener {
                Toast.makeText(dataAll.context, Constants.TASK_UPDATE_MSG, Toast.LENGTH_SHORT).show()


            }.addOnFailureListener {
                Toast.makeText(dataAll.context, Constants.FAILED_UPDATE_MSG, Toast.LENGTH_SHORT).show()
            }
        }

    }


}
    fun deletingData(context: Context,idOfNote: String){
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
                idOfNote?.let { it1 ->
                    database.child(it1).removeValue().addOnSuccessListener {

                        Toast.makeText(context, Constants.TASK_DELETED_MSG, Toast.LENGTH_SHORT).show()

                        LoggerTodo.logInfo("Task Deleted")
                    }.addOnFailureListener {
                        Toast.makeText(context, Constants.FAILED_DELETED_MSG, Toast.LENGTH_SHORT).show()

                        LoggerTodo.logInfo("Task Deletion Failed")

                    }
                }



    }


}