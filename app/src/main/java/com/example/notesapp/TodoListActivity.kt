package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.adapters.MyAdapter
import com.example.notesapp.adapters.MyAdapterForDone
import com.example.notesapp.constants.Constants
import com.example.notesapp.databinding.ActivityTodoListBinding
import com.example.notesapp.dataclasses.User
import com.example.notesapp.repository.GetDataRepo


import com.example.notesapp.viewmodels.TodoViewModel
import com.example.notesapp.viewmodels.TodoViewModelFactory
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlin.collections.ArrayList

class TodoListActivity : AppCompatActivity() {

   // private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference
    private lateinit var databasedone : DatabaseReference
    private lateinit var databaseCount : DatabaseReference

    private lateinit var userArrayList: ArrayList<User>
    private lateinit var userArrayListDone: ArrayList<User>
    private lateinit var loopthing : String
    private lateinit var binding: ActivityTodoListBinding
     lateinit var todoViewModel: TodoViewModel
    val getDataRepo =GetDataRepo()
        //var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_todo_list)

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs)

        //userRecyclerView =findViewById(R.id.todoList)
        //userRecyclerViewDone=findViewById(R.id.todoListForDone)
        //val remainItemCount : TextView = findViewById(R.id.itemCount)
        //val abc:Button=findViewById(R.id.btnDelete)


        val fab: View = findViewById(R.id.addfloatingBtn)

        binding.todoList.layoutManager = LinearLayoutManager(this)
        binding.todoListForDone.layoutManager = LinearLayoutManager(this)

        binding.todoList.setHasFixedSize(true)
        binding.todoListForDone.setHasFixedSize(true)



        userArrayList = arrayListOf()
        userArrayListDone =arrayListOf()






        binding.addfloatingBtn.setOnClickListener {

            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        val adapter = MyAdapter(userArrayList)
        binding.todoList.adapter = adapter

            todoViewModel = ViewModelProvider(this, TodoViewModelFactory(getDataRepo)).get(TodoViewModel::class.java)
            todoViewModel.gettingData(userArrayList)

            getUserData()
            getUserDataCompleted()
        /*donetask.setOnClickListener {

        }*/

          //  val recyclerView = findViewById<RecyclerView>(R.id.todoList)
          //  val sizeOfList : TextView = findViewById(R.id.sizeOfList)
          //  sizeOfList.text = userArrayList.size



    }

    override fun onDestroy() {
        super.onDestroy()
       userArrayList.clear()
        userArrayListDone.clear()
    }


    private fun getUserData()
    {
             //todoViewModel = ViewModelProvider(this, TodoViewModelFactory(getDataRepo)).get(
                //TodoViewModel::class.java)


        todoViewModel.callbackGettingData(object: GetDataRepo.SomeCallbackInterface{
            override fun onAdapter(userArrayListAfter : ArrayList<User>, countTask:Int) {

                val adapter = MyAdapter(userArrayListAfter)
                binding.todoList.adapter = adapter

                binding.itemCount.text = getString(R.string.remaining_Task, countTask)

                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                    override fun onItemClick(position: Int)
                    {
                        val idForNote = userArrayList[position].idForNote
                        val edtTitleOfNote = userArrayList[position].edtTitleOfNote
                        val edtNoteDiscripton = userArrayList[position].edtNoteDiscripton

                        val intent = Intent(this@TodoListActivity, MainActivity::class.java)

                        intent.putExtra(Constants.NOTE_TYPE, Constants.EDIT)
                        intent.putExtra(Constants.TITLE_OF_TASK, edtTitleOfNote)
                        intent.putExtra(Constants.DISCRIPTION_OF_TASK, edtNoteDiscripton)
                        intent.putExtra(Constants.ID_OF_TASK, idForNote)

                        Log.d("idofnote", "$idForNote")
                        Log.d("noteTitle", "$edtTitleOfNote")

                        startActivity(intent)
                        //Toast.makeText(this@TodoListActivity,"you Clicked on $edtTitleOfNote ",Toast.LENGTH_LONG).show()
                    }


                }){

                }





            }

        })
















       //database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        //databaseCount = FirebaseDatabase.getInstance().getReference("count")
     /*  database.addValueEventListener(object  : ValueEventListener{
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
                        LoggerTodo.logDebug(count.toString())
                    }

                    binding.itemCount.text= "Remaining Tasks( $count )"
                    LoggerTodo.logDebug(count.toString())

                    userArrayList.sortBy {

                        it.doneOrNot
                    }
                    val adapter = MyAdapter(userArrayList)
                    binding.todoList.adapter = adapter


                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener
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


                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })*/



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
                    binding.todoListForDone.adapter = adapter

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