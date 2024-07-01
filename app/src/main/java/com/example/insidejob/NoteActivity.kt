package com.example.insidejob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insidejob.databinding.ActivityNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast

class NoteActivity : AppCompatActivity() {

    private val binding: ActivityNoteBinding by lazy {
        ActivityNoteBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialize database refrence
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.save.setOnClickListener {

            val title = binding.notetitle.text.toString()
            val des = binding.description.text.toString()

            if (title.isEmpty() || des.isEmpty()) {
                Toast.makeText(this, "Fill both Field", Toast.LENGTH_SHORT).show()
            }
            else {
                // Generate a unique key for the note
                val noteKey = databaseReference.child("notes").push().key

                Log.d("NoteActivity", "Generated note key: $noteKey")


                // Note item instance
                val noteItem = NoteItem(title, des , noteKey ?: "")
                if (noteKey != null) {
                    // Add note to the database
                    databaseReference.child("notes").child(noteKey).setValue(noteItem)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d("NoteActivity", "Note saved successfully")
                                Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                                intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else {
                                Log.d("NoteActivity", "Failed to save note")
                                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else {
                    Toast.makeText(this, "Failed to generate key for the note", Toast.LENGTH_SHORT).show()
                    Log.d("NoteActivity", "Failed to generate key for the note")
                }
            }

        }

        //to check user
 /*     binding.save.setOnClickListener {

            val title = binding.notetitle.text.toString()
            val des = binding.description.text.toString()

            if (title.isEmpty() || des.isEmpty()) {
                Toast.makeText(this, "Fill both Field", Toast.LENGTH_SHORT).show()
            } else {
                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    //generate a unique key for the note
                    val noteKey =
                        databaseReference.child("user").child(user.uid).child("notes").push().key

                    //note item instance
                    val noteItem = NoteItem(title, des)
                    if (noteKey != null) {
                        //add notes do the user note
                        databaseReference.child("user").child(user.uid).child("notes").child(noteKey).setValue(noteItem)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                                    intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "failed to save note", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }

            }  */
    }

    private fun enableEdgeToEdge(){

    }
}