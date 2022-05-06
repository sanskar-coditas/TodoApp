package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.constants.Constants
import com.example.notesapp.R
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MyAdapterForDone(private val userList: ArrayList<User>) : RecyclerView.Adapter<MyAdapterForDone.MyViewHolder>()
{

    private lateinit var database: DatabaseReference
    private lateinit var databaseDone: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.single_todo_list_viewpager,
            parent, false
        )

        return MyViewHolder(itemview)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.titleOfNote.text = currentitem.edtTitleOfNote
        val idForNote = userList[position].idForNote
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        databaseDone = FirebaseDatabase.getInstance().getReference(Constants.COMPLETED)

        holder.lin.setBackgroundResource(R.drawable.corner)
        holder.doneTask.setImageResource(R.drawable.donetask)

        holder.deleteBtn.setOnClickListener {
            idForNote?.let { it1 ->
                userList.clear()
                databaseDone.child(it1).removeValue();

            }
        }

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val titleOfNote: TextView = itemview.findViewById(R.id.txtNoteTitle)
        val doneTask: ImageView = itemview.findViewById(R.id.imgDone)
        val cardViewItem: CardView = itemview.findViewById(R.id.carditem)
        val deleteBtn: Button = itemview.findViewById(R.id.btnDelete)
        val lin : LinearLayout=itemview.findViewById(R.id.layoutForTask)

    }
}