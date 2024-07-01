package com.example.insidejob

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insidejob.databinding.ActivityBundlePassingBinding

class BundlePassingActivity : AppCompatActivity() {
    private val binding: ActivityBundlePassingBinding by lazy {
        ActivityBundlePassingBinding.inflate(layoutInflater)
    }
    private lateinit var spinner2 : Spinner
    private lateinit var selectedItem : String
    private var isUserInteracted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //spinner
        spinner2 = binding.spinner2

        val spinnerList = listOf("Male","Female", "NOTA")

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,spinnerList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = arrayAdapter

        spinner2.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                selectedItem = parent?.getItemAtPosition(position).toString()

                if (isUserInteracted) {
                    Toast.makeText(this@BundlePassingActivity, "selected $selectedItem", Toast.LENGTH_SHORT).show()
                } else {
                    isUserInteracted = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // You can add handling for nothing selected if needed
            }
        }

        binding.button7.setOnClickListener {
            val name = binding.editTextText2.text.toString()
            val age = binding.editTextText3.text.toString()
            val gender = binding.editTextText4.text.toString()
            val country = binding.editTextText5.text.toString()
            val item = selectedItem

            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("Name", name)
            intent.putExtra("Age", age)
            intent.putExtra("Gender", gender)
            intent.putExtra("Country", country)
            intent.putExtra("Item",item)
            startActivity(intent)
        }
    }

    private fun enableEdgeToEdge(){

    }
}