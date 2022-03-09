package com.example.notesapp.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.notesapp.LoggerTodo
import com.example.notesapp.MainActivity
import com.example.notesapp.TodoListActivity
import com.example.notesapp.adapters.MyAdapter
import com.example.notesapp.constants.Constants
import com.example.notesapp.databinding.ActivityTodoListBinding
import com.example.notesapp.dataclasses.User
import com.example.notesapp.viewmodels.MainViewModel
import com.google.firebase.database.*
import java.sql.Array
import java.util.ArrayList

class TodoActivityRepository {
    private lateinit var database : DatabaseReference
    lateinit var todoListActivity: TodoListActivity
    lateinit var mcallback : SomeCallbackInterface


    var count:Int=0

    interface SomeCallbackInterface{

        fun onAdapter(adapterAttach:MyAdapter)


    }
    fun initOnClickInterface(callback: SomeCallbackInterface) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallback = callback

    }











    fun getData(userArrayList : ArrayList<User>,count: Int,binding:ActivityTodoListBinding){
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        //databaseCount = FirebaseDatabase.getInstance().getReference("count")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot)
            {

                userArrayList.clear()
                this@TodoActivityRepository.count =0
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)
                        if(user!!.doneOrNot=="no")
                        {
                            this@TodoActivityRepository.count++
                            LoggerTodo.logDebug("if condition is running")
                        }
                        userArrayList.add(user!!)

                        Log.d("USER", "${user.toString()}" )
                        LoggerTodo.logDebug(count.toString())
                    }

                    binding.itemCount.text= "Remaining Tasks( $count )"
                    LoggerTodo.logDebug(count.toString())

                    userArrayList.sortBy {

                        it.doneOrNot
                    }
                    val adapter = MyAdapter(userArrayList)
                    binding.todoList.adapter = adapter

                    mcallback.onAdapter(adapter)


                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}