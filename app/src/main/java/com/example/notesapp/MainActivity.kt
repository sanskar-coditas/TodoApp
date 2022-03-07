package com.example.notesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.constants.Constants
import com.example.notesapp.databinding.MainActivityBinding
import com.example.notesapp.dataclasses.DataAll
import com.example.notesapp.repository.MainActivityRepository
import com.example.notesapp.viewmodels.MainViewModel
import com.example.notesapp.viewmodels.MainViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    private lateinit var database : DatabaseReference
    lateinit var mainViewModel: MainViewModel


        //val id  String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
     getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs);


        database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(MainActivityRepository())).get(
            MainViewModel::class.java)


        val noteIdp = intent.getStringExtra(getString(R.string.uniqueIdForTask))
        val noteType= intent.getStringExtra(getString(R.string.notetype))


        if(noteType.equals(getString(R.string.edit))){
           //LoggerTodo.LogDebug(noteType.toString())
            val noteTitle=intent.getStringExtra(Constants.TITLE_OF_TASK)
            val noteDesc=intent.getStringExtra(Constants.DISCRIPTION_OF_TASK)

          //  LoggerTodo.LogDebug("LogTest")
           // LoggerTodo.LogError("LogTest")
           // LoggerTodo.LogInfo("LogTest")
           // LoggerTodo.LogWarn("LogTest")
           // LoggerTodo.LogVerbose("LogTest")
           // LoggerTodo.LogAssert("LogTest")

            enterButton.text = Constants.UPDATE_BUTTON_TEXT
            edtTitleOfNote.setText(noteTitle)
            edtNoteDiscripton.setText(noteDesc)

            //Delete functionality
            if(noteType.equals(Constants.EDIT))
            {
                binding.btnDelete.visibility= View.VISIBLE
                binding.btnDelete.setOnClickListener {

                    mainViewModel.deleteData(this,noteIdp.toString())

                    binding.edtTitleOfNote.text.clear()
                    binding.edtNoteDiscripton.text.clear()
                }
            }
        }
        else
        {
            enterButton.text = Constants.ENTER_BUTTON_TEXT
        }

        binding.enterButton.setOnClickListener {
            val titleOfNote = binding.edtTitleOfNote.text.toString()
            val discretionNote = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()
            val doneNot = Constants.NOT_DONE_TEXT

            val allDataAll = DataAll(titleOfNote,discretionNote,idForNote,doneNot,noteType,noteIdp,this)
            Log.d("sanskarpawar",allDataAll.toString())
            mainViewModel.insertData(allDataAll)

            binding.edtTitleOfNote.text.clear()
            binding.edtNoteDiscripton.text.clear()


        }












       /*binding.enterButton.setOnClickListener {

            val titleofnote = binding.edtTitleOfNote.text.toString()
            val discription = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()
            val doneNot = Constants.NOT_DONE_TEXT
            //val timetext = ServerValue.TIMESTAMP.toString()
            database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)

            val User = User(titleofnote,discription,idForNote,doneNot)
            if(!noteType.equals(Constants.EDIT)) {

                if (!TextUtils.isEmpty(titleofnote) && !TextUtils.isEmpty(discription)) {

                    database.child(idForNote).setValue(User).addOnSuccessListener {
                        binding.edtTitleOfNote.text.clear()
                        binding.edtNoteDiscripton.text.clear()
                        //database.child(idForNote).child("timestamp").setValue(ServerValue.TIMESTAMP)
                        Toast.makeText(this, Constants.TASK_SAVED_MSG, Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {

                        Toast.makeText(this, Constants.FAILED_SAVE_MSG, Toast.LENGTH_SHORT).show()


                    }


                } else
                    Toast.makeText(this, Constants.FILL_EMPTY_MSG, Toast.LENGTH_SHORT).show()
            }
            else
            {
                val note= mapOf(
                    Constants.TITLE_OF_TASK to titleofnote,
                    Constants.DISCRIPTION_OF_TASK to discription,
                    Constants.ID_OF_TASK to noteidp

                )

                noteidp?.let { it1 ->
                    database.child(it1).updateChildren(note).addOnSuccessListener {
                        edtTitleOfNote.text.clear()
                        edtNoteDiscripton.text.clear()

                        Toast.makeText(this, Constants.TASK_UPDATE_MSG, Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, Constants.FAILED_UPDATE_MSG, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }*/


    }





}
