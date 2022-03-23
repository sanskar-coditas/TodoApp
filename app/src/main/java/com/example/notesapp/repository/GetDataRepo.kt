package com.example.notesapp.repository


import android.util.Log
import com.example.notesapp.LoggerTodo
import com.example.notesapp.adapters.MyAdapterForDone
import com.example.notesapp.constants.Constants
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.*
import java.util.ArrayList

class GetDataRepo {
    private lateinit var database: DatabaseReference
    lateinit var mcallBack: CallbackInterfaceGet
    lateinit var mcallbackDone: CallbackInterfaceGetCompleted


    private lateinit var databasedone: DatabaseReference

    var count: Int = 0

    interface CallbackInterfaceGet {

        fun onAdapter(userArrayListAfter: ArrayList<User>, countTask: Int)

    }

    fun initOnClickInterface(callback: CallbackInterfaceGet) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallBack = callback

    }


    fun getData(userArrayList: ArrayList<User>) {
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userArrayList.clear()
                count = 0
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user!!.doneOrNot == "no") {
                            count++
                            LoggerTodo.logDebug("if condition is running")
                        }
                        userArrayList.add(user!!)

                        Log.d("USER", "${user.toString()}")

                    }


                    LoggerTodo.logDebug("count of tasks $count.toString()")
                    userArrayList.sortBy {

                        it.doneOrNot
                    }

                    mcallBack.onAdapter(userArrayList, count)


                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    interface CallbackInterfaceGetCompleted {

        fun onAdapter(userArrayListAfter: ArrayList<User>)


    }

    fun initOnClickInterfaceCompleted(callbackDone: CallbackInterfaceGetCompleted) {
        LoggerTodo.logInfo("callbackdata initOnClickInterface")
        mcallbackDone = callbackDone

    }


    fun getDataCompleted(userArrayListDone: ArrayList<User>) {
        databasedone = FirebaseDatabase.getInstance().getReference("Completed")

        databasedone.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userArrayListDone.clear()
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        userArrayListDone.add(user!!)

                        Log.d("USER", "${user.toString()}")
                    }


                    mcallbackDone.onAdapter(userArrayListDone)


                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


    }
}