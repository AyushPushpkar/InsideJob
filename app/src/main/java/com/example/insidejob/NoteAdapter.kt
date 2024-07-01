package com.example.insidejob

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.insidejob.databinding.NotesitemBinding

class NoteAdapter(private val notes: List<NoteItem> ,private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

        interface OnItemClickListener {
            fun onDeleteClick(noteId : String)
            fun onUpdateClick(noteId: String, title : String , des : String)
        }


    class NoteViewHolder(val binding: NotesitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {

            binding.titletext.text = note.title
            binding.destext.text = note.des

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val binding = NotesitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
        anim(holder.itemView)

        holder.binding.updatebtn.setOnClickListener {
            itemClickListener.onUpdateClick(note.noteId,note.title,note.des)
        }
        holder.binding.Deletebtn.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun anim ( view: View){

        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 700
        view.startAnimation(animation)
    }
}