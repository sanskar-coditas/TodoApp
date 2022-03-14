package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.constants.Constants
import com.example.notesapp.R
import com.example.notesapp.dataclasses.User
import com.google.firebase.database.*


class MyAdapter(private val userList: ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{

    private lateinit var database: DatabaseReference
    private lateinit var databasedone: DatabaseReference

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.single_todo_item,
            parent, false
        )

        return MyViewHolder(itemview,mListener)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.titleOfNote.text = currentItem.edtTitleOfNote

        val title = userList[position].edtTitleOfNote
        val done = userList[position].doneOrNot
        val idForNote = userList[position].idForNote
        val description = userList[position].edtNoteDescription
        database = FirebaseDatabase.getInstance().getReference(Constants.ROOT_NODE_TODO)
        databasedone = FirebaseDatabase.getInstance().getReference(Constants.COMPLETED)


        holder.doneTask.setOnClickListener {


            if (idForNote != null) {
                if (done != null) {
                    if (title != null) {
                        if (description != null) {
                            val User = User(title, description, idForNote, Constants.DONE_TEXT)
                            database.child(idForNote).child(Constants.TASK_DONE_OR_NOT)
                                .setValue(Constants.DONE_TEXT)
                            databasedone.child(idForNote).setValue(User)

                        }
                    }
                }
            }

        }

        if (done == "yes") {

            holder.cardViewItem.setBackgroundResource(R.drawable.corner)
            holder.doneTask.setImageResource(R.drawable.donetask)

        }

        holder.deleteBtn.setOnClickListener {
            idForNote?.let { it1 ->
                userList.clear()
                database.child(it1).removeValue();

            }
        }
    }


    override fun getItemCount(): Int {
        return userList.size

    }


    class MyViewHolder(itemview: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemview) {
        val titleOfNote: TextView = itemview.findViewById(R.id.txtNoteTitle)
        val doneTask: ImageView = itemview.findViewById(R.id.imgDone)
        val deleteBtn: Button = itemview.findViewById(R.id.btnDelete)
        val cardViewItem: CardView = itemview.findViewById(R.id.carditem)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)


            }


        }

    }
}