package com.example.insidejob

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insidejob.databinding.ActivityMainBinding
import com.example.insidejob.databinding.NoteupdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity() , NoteAdapter.OnItemClickListener {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var itemClickListener: NoteAdapter.OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the status bar color
        window.statusBarColor = Color.parseColor("#03A9F4")

        itemClickListener = this@MainActivity // Assigning the correct context


        //initialize database refrence
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        recyclerView = binding.noterecycler
        recyclerView.layoutManager = LinearLayoutManager(this)


        //Retrive data
        val noteRefrence = databaseReference.child("notes")
        noteRefrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notelist = mutableListOf<NoteItem>()
                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(NoteItem::class.java)
                    note?.let {
                        // Ensure that noteId is correctly retrieved from Firebase and assigned
                        it.noteId = noteSnapshot.key ?: "" // Assigning the key as the noteId
                        notelist.add(it)
                    }
                }
                notelist.reverse()
                noteAdapter = NoteAdapter(notelist, itemClickListener )
                recyclerView.adapter = noteAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })


  /*      val currentuser = auth.currentUser
        currentuser?.let{ user ->
            val noteRefrence = databaseReference.child("user").child(user.uid).child("notes")
            noteRefrence.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notelist = mutableListOf<NoteItem>()
                    for (noteSnapshot in snapshot.children){
                        val note = noteSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            notelist.add(it)
                        }
                        val adapter  = NoteAdapter(notelist)
                        binding.noterecycler.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }  */

        binding.createnote.setOnClickListener {
            intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        //customMenu
        val customMenu = CustomMenu()
        binding.menu1.setOnClickListener {
            customMenu.showMenu(this,it)
        }
    }

    //Delete data
    override fun onDeleteClick(noteId: String) {
 /*       val currentuser = auth.currentUser
        currentuser?. let { user ->
            val  noteReference = databaseReference.child("user").child(user.uid).child("notes")
                noteReference.child(noteId).removeValue()
        }  */
        
            val noteReference = databaseReference.child("notes").child(noteId)
            noteReference.removeValue()
    }

    //Update data
    override fun onUpdateClick(noteId: String, currentTitle : String, currentDes : String) {

        val dialogBinding = NoteupdateBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Notes")
            .setIcon(R.drawable.editicon)
            .create()

        // Set the custom background drawable
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogback)

        dialogBinding.updatetitle.setText(currentTitle)
        dialogBinding.updatedes.setText(currentDes)

        dialogBinding.saveupbtn.setOnClickListener {
            val newTitle = dialogBinding.updatetitle.text.toString()
            val newDes = dialogBinding.updatedes.text.toString()
            updateNoteDatabase(noteId, newTitle, newDes)
            dialog.dismiss()
        }

        dialogBinding.cancelbtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }
    private fun updateNoteDatabase(noteId: String, newTitle: String, newDes: String) {

        val noteReference = databaseReference.child("notes")
        val updateNote = NoteItem(newTitle,newDes,noteId)
        noteReference.child(noteId).setValue(updateNote)
            .addOnCompleteListener{  task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "failed to update note", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
