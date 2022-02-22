package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabasekotlin.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.single_todo_item.*
import kotlin.collections.ArrayList

class TodoListActivity : AppCompatActivity() {

   // private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference
    private lateinit var databasedone : DatabaseReference
    private lateinit var databaseCount : DatabaseReference
    private lateinit var  userRecyclerView: RecyclerView
    private lateinit var  userRecyclerViewDone: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var userArrayListDone: ArrayList<User>
    private lateinit var loopthing : String
        var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)


        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs)

        userRecyclerView =findViewById(R.id.todoList)
        userRecyclerViewDone=findViewById(R.id.todoListForDone)
        val remainItemCount : TextView = findViewById(R.id.itemCount)
        //val abc:Button=findViewById(R.id.btnDelete)

        val fab: View = findViewById(R.id.addfloatingBtn)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerViewDone.layoutManager = LinearLayoutManager(this)

        userRecyclerView.setHasFixedSize(true)
        userRecyclerViewDone.setHasFixedSize(true)



        userArrayList = arrayListOf()
        userArrayListDone =arrayListOf()
        getUserData()
        getUserDataCompleted()


        fab.setOnClickListener {

            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        val adapter = MyAdapter(userArrayList)
        userRecyclerView.adapter = adapter

        /*donetask.setOnClickListener {

        }*/

          //  val recyclerView = findViewById<RecyclerView>(R.id.todoList)
          //  val sizeOfList : TextView = findViewById(R.id.sizeOfList)
          //  sizeOfList.text = userArrayList.size


    }

    private fun getUserData()
    {
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        databaseCount = FirebaseDatabase.getInstance().getReference("count")

       database.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                    userArrayList.clear()
                    count =0
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)
                        if(user!!.doneOrNot=="no")
                        {
                            count++
                        }
                        userArrayList.add(user!!)

                        Log.d("USER", "${user.toString()}" )
                    }

                    this@TodoListActivity.itemCount.text= "Remaining Tasks( $count )"

                    userArrayList.sortBy {

                        it.doneOrNot
                    }
                    val adapter = MyAdapter(userArrayList)
                    userRecyclerView.adapter = adapter

                   // val remainItemCount : TextView = findViewById(R.id.itemCount)
                   // val count = adapter.itemCount
                   // databaseCount.setValue(count)

                   /*databaseCount.addValueEventListener(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot)
                        {
                            val c = snapshot.value
                            remainItemCount.setText(Constants.REMAINING_TASKS +c+")")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })*/


                    adapter.setOnItemClickListener(object :MyAdapter.onItemClickListener
                    {
                        override fun onItemClick(position: Int) {
                            val idForNote = userArrayList[position].idForNote
                            val edtTitleOfNote = userArrayList[position].edtTitleOfNote
                            val edtNoteDiscripton = userArrayList[position].edtNoteDiscripton
                            val intent = Intent(this@TodoListActivity,MainActivity::class.java)
                            intent.putExtra(Constants.NOTE_TYPE, Constants.EDIT)
                            intent.putExtra(Constants.TITLE_OF_TASK, edtTitleOfNote)
                            intent.putExtra(Constants.DISCRIPTION_OF_TASK,edtNoteDiscripton)
                            intent.putExtra(Constants.ID_OF_TASK,idForNote)

                            Log.d("idofnote", "$idForNote" )
                            Log.d("noteTitle", "$edtTitleOfNote" )

                            startActivity(intent)
                            //Toast.makeText(this@TodoListActivity,"you Clicked on $edtTitleOfNote ",Toast.LENGTH_LONG).show()
                        }


                    }){}






                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



      //  userRecyclerView.sta
    }

    private fun getUserDataCompleted()
    {

        databasedone = FirebaseDatabase.getInstance().getReference("Completed")

        databasedone.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userArrayListDone.clear()
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children)
                    {
                        val user = userSnapshot.getValue(User::class.java)

                        userArrayListDone.add(user!!)

                        Log.d("USER", "${user.toString()}" )
                    }
                    val adapter = MyAdapterForDone(userArrayListDone)
                    userRecyclerViewDone.adapter = adapter


                    /*adapter.setOnItemClickListener(object :MyAdapterForDone.onItemClickListener
                        {
                        override fun onItemClick(position: Int) {
                            val idForNote = userArrayListDone[position].idForNote
                            val edtTitleOfNote = userArrayListDone[position].edtTitleOfNote
                            val edtNoteDiscripton = userArrayListDone[position].edtNoteDiscripton
                            val intent = Intent(this@TodoListActivity,MainActivity::class.java)
                            intent.putExtra(Constants.NOTE_TYPE, Constants.EDIT)
                            intent.putExtra(Constants.TITLE_OF_TASK, edtTitleOfNote)
                            intent.putExtra(Constants.DISCRIPTION_OF_TASK,edtNoteDiscripton)
                            intent.putExtra(Constants.ID_OF_TASK,idForNote)

                            Log.d("idofnote", "$idForNote" )
                            Log.d("noteTitle", "$edtTitleOfNote" )

                            startActivity(intent)
                            //Toast.makeText(this@TodoListActivity,"you Clicked on $edtTitleOfNote ",Toast.LENGTH_LONG).show()
                        }


                    })run {}*/


                }


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })



        //  userRecyclerView.sta
    }
}
































/*  adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener){

                        }

                           val delete = findViewById<Button>(R.id.btnDelete)
                           delete.setOnClickListener {

                               //database.child(todoId.toString()).removeValue().addOnCompleteListener(new onCom)
                               Toast.makeText(this@TodoListActivity,"you Clicked on $todoId ",Toast.LENGTH_LONG).show()

                           }*/