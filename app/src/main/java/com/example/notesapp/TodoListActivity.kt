package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
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
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlin.collections.ArrayList

class TodoListActivity : AppCompatActivity() {


    private lateinit var userArrayList: ArrayList<User>
    private lateinit var userArrayListDone: ArrayList<User>
    private lateinit var binding: ActivityTodoListBinding
    lateinit var todoViewModel: TodoViewModel
    private val getDataRepo = GetDataRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_todo_list)

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs)

        binding.todoList.layoutManager = LinearLayoutManager(this)
        //binding.todoListForDone.layoutManager = LinearLayoutManager(this)

        binding.todoList.setHasFixedSize(true)
       // binding.todoListForDone.setHasFixedSize(true)

        userArrayList = arrayListOf()
        userArrayListDone = arrayListOf()


        binding.addfloatingBtn.setOnClickListener {

            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val adapter = MyAdapter(userArrayList)
        binding.todoList.adapter = adapter

        todoViewModel = ViewModelProvider(this, TodoViewModelFactory(getDataRepo)
        ).get(TodoViewModel::class.java)

        todoViewModel.gettingData(userArrayList)

        todoViewModel.gettingDataCompleted(userArrayListDone)

        getUserData()
        getUserDataCompleted()

    }

    override fun onDestroy() {
        super.onDestroy()
        userArrayList.clear()
        userArrayListDone.clear()
    }


    private fun getUserData()
    {

        todoViewModel.callbackGettingData(object : GetDataRepo.CallbackInterfaceGet {
            override fun onAdapter(userArrayListAfter: ArrayList<User>, countTask: Int) {

                val adapter = MyAdapter(userArrayListAfter)
                binding.todoList.adapter = adapter

                binding.itemCount.text = getString(R.string.remaining_Task, countTask)

                adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val idForNote = userArrayList[position].idForNote
                        val edtTitleOfNote = userArrayList[position].edtTitleOfNote
                        val edtNoteDiscripton = userArrayList[position].edtNoteDescription

                        val intent = Intent(this@TodoListActivity, MainActivity::class.java)

                        intent.putExtra(Constants.NOTE_TYPE, Constants.EDIT)
                        intent.putExtra(Constants.TITLE_OF_TASK, edtTitleOfNote)
                        intent.putExtra(Constants.DESCRIPTION_OF_TASK, edtNoteDiscripton)
                        intent.putExtra(Constants.ID_OF_TASK, idForNote)

                        Log.d("idofnote", "$idForNote")
                        Log.d("noteTitle", "$edtTitleOfNote")

                        startActivity(intent)

                    }

                })

            }

        })

    }

    private fun getUserDataCompleted()
    {
        todoViewModel.callbackGettingDataCompleted(object: GetDataRepo.CallbackInterfaceGetCompleted{
            override fun onAdapter(userArrayListAfter: java.util.ArrayList<User>) {

                val adapter = MyAdapterForDone(userArrayListAfter)
                binding.todoListForDone.adapter = adapter
            }

        })

    }
}
































/*  adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener){

                        }

                           val delete = findViewById<Button>(R.id.btnDelete)
                           delete.setOnClickListener {

                               //database.child(todoId.toString()).removeValue().addOnCompleteListener(new onCom)
                               Toast.makeText(this@TodoListActivity,"you Clicked on $todoId ",Toast.LENGTH_LONG).show()

                           }*/