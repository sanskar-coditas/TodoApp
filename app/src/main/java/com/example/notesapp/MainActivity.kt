package com.example.notesapp

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.get
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
import kotlinx.android.synthetic.main.single_todo_item.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var database: DatabaseReference
    lateinit var mainViewModel: MainViewModel
    private val taskInOpRepo = TaskInOpRepo()
    lateinit var spPriorityVal : String


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
            val notePriority = intent.getStringExtra(Constants.PRIORITY_OF_NOTE)
            enterButton.text = Constants.UPDATE_BUTTON_TEXT
            edtTitleOfNote.setText(noteTitle)
            edtNoteDiscripton.setText(noteDesc)



            if (noteType.equals(Constants.EDIT)) {
                binding.btnDelete.visibility = View.VISIBLE
                // added to generate the conflcits


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

        binding.spPriority.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, VIEW: View?, position: Int, id: Long) {
               spPriorityVal = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.enterButton.setOnClickListener {

            val titleOfNote = binding.edtTitleOfNote.text.toString()
            val discretionNote = binding.edtNoteDiscripton.text.toString()
            val idForNote = UUID.randomUUID().toString()
            val doneNot = Constants.NOT_DONE_TEXT


            val allDataAll =
                TaskOfNote(titleOfNote, discretionNote, idForNote, doneNot, noteType, noteIdp, spPriorityVal)

            Log.d("sanskarpawar", allDataAll.toString())
            mainViewModel.insertData(allDataAll)


        }
        binding.showList.setOnClickListener {
            onShare()
        }



    }


    private fun onShare()
    {
        val shareIntent = ShareCompat.IntentBuilder(this)
                    //IntentBuilder is a helper for constructing ACTION_SEND and ACTION_SEND_MULTIPLE sharing intents and starting activities to share content.
                  //The ComponentName and package name of the calling activity will be included.
            .setText(getString(R.string.share_text, binding.edtTitleOfNote.text.toString(), binding.edtNoteDiscripton.text.toString()))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }


    // This is the line to generate the conflict2
    //this one is as well
    //this one is
    // also this one with other conflicts2










}
