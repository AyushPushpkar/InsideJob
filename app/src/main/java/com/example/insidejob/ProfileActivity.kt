package com.example.insidejob

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insidejob.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = binding.textView10
        val age = binding.textView12
        val gender = binding.textView11
        val country = binding.textView13

        val intent = intent
        val Name = intent.getStringExtra("Name")
        val Age = intent.getStringExtra("Age")
        val Gender = intent.getStringExtra("Item")
        val Country = intent.getStringExtra("Country")

        name.text = "Name: $Name"
        age.text = "Age: $Age"
        gender.text = "Gender: $Gender"
        country.text = "Country: $Country"


    }

    private fun enableEdgeToEdge(){

    }
}