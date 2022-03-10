package com.example.notesapp.repository


import android.util.Log
import com.example.notesapp.LoggerTodo
import com.example.notesapp.adapters.MyAdapter
import com.example.notesapp.constants.Constants
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.*
import java.util.ArrayList

class GetDataRepo {
    private lateinit var database : DatabaseReference
    //lateinit var todoListActivity: TodoListActivity
    lateinit var mcallback : SomeCallbackInterface


    var count:Int=0

    interface SomeCallbackInterface{

        fun onAdapter(userArrayListAfter : ArrayList<User>,countTask:Int)


    }
    fun initOnClickInterface(callback: SomeCallbackInterface) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallback = callback

    }

    fun getData(userArrayList : ArrayList<User>){
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        //databaseCount = FirebaseDatabase.getInstance().getReference("count")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot)
            {

                userArrayList.clear()
                count =0
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)
                        if(user!!.doneOrNot=="no")
                        {
                            count++
                            LoggerTodo.logDebug("if condition is running")
                        }
                        userArrayList.add(user!!)

                        Log.d("USER", "${user.toString()}" )
                        //LoggerTodo.logDebug(count.toString())
                    }

                    //binding.itemCount.text= "Remaining Tasks( $count )"
                    LoggerTodo.logDebug("count of tasks $count.toString()")
                    userArrayList.sortBy {

                        it.doneOrNot
                    }

                   // val adapter = MyAdapter(userArrayList)

                    mcallback.onAdapter(userArrayList,count)

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}