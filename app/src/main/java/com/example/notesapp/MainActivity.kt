package com.example.notesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.constants.Constants
import com.example.notesapp.databinding.MainActivityBinding
import com.example.notesapp.dataclasses.TaskOfNote
import com.example.notesapp.repository.TaskInOpRepo
import com.example.notesapp.viewmodels.MainViewModel
import com.example.notesapp.viewmodels.MainViewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var database: DatabaseReference
    lateinit var mainViewModel: MainViewModel
    private val taskInOpRepo = TaskInOpRepo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.abs);



        database = FirebaseDatabase.getInstance().getReference(getString(R.string.databaseRefTodo))

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(taskInOpRepo)).get(
            MainViewModel::class.java
        )


        val noteIdp = intent.getStringExtra(getString(R.string.uniqueIdForTask))
        val noteType = intent.getStringExtra(getString(R.string.notetype))

        if (noteType.equals(getString(R.string.edit))) {

            val noteTitle = intent.getStringExtra(Constants.TITLE_OF_TASK)
            val noteDesc = intent.getStringExtra(Constants.DESCRIPTION_OF_TASK)

            enterButton.text = Constants.UPDATE_BUTTON_TEXT
            edtTitleOfNote.setText(noteTitle)
            edtNoteDiscripton.setText(noteDesc)

            if (noteType.equals(Constants.EDIT)) {
                binding.btnDelete.visibility = View.VISIBLE

                binding.btnDelete.setOnClickListener {

                    mainViewModel.deleteData(noteIdp.toString())

                    mainViewModel.callbackDataDelete(object : TaskInOpRepo.SomeCallbackDelete {
                        override fun onSuccess() {
                            Toast.makeText(this@MainActivity, Constants.TASK_DELETED_MSG, Toast.LENGTH_SHORT).show()
                            binding.edtTitleOfNote.text.clear()
                            binding.edtNoteDiscripton.text.clear()
                        }

                        override fun onFailure() {
                            Toast.makeText(this@MainActivity, Constants.FAILED_DELETED_MSG, Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        } else {
            enterButton.text = Constants.ENTER_BUTTON_TEXT
        }



        mainViewModel.callbackData(object : TaskInOpRepo.CallbackInterfaceInsert {

            override fun onSuccess() {
                LoggerTodo.logInfo("callbackdata in inside onSuccess main activity")
                Toast.makeText(this@MainActivity, Constants.TASK_SAVED_MSG, Toast.LENGTH_SHORT)
                    .show()
                binding.edtTitleOfNote.text.clear()
                binding.edtNoteDiscripton.text.clear()
            }

            override fun onFailure() {
                Toast.makeText(this@MainActivity, Constants.FAILED_SAVE_MSG, Toast.LENGTH_SHORT)
                    .show()
            }

        })



        binding.enterButton.setOnClickListener {

            val titleOfNote = binding.edtTitleOfNote.text.toString()
            val discretionNote = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()
            val doneNot = Constants.NOT_DONE_TEXT

            val allDataAll =
                TaskOfNote(titleOfNote, discretionNote, idForNote, doneNot, noteType, noteIdp)
            Log.d("sanskarpawar", allDataAll.toString())
            mainViewModel.insertData(allDataAll)


        }
    }

}
