package com.example.insidejob

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insidejob.databinding.ActivityUiuxactivityBinding
import render.animations.Attention
import render.animations.Bounce
import render.animations.Render

class UIUXactivity : AppCompatActivity() {
    private lateinit var button: Button
    lateinit var binding: ActivityUiuxactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUiuxactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initializing the animation
        var fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        var bottomdown = AnimationUtils.loadAnimation(this, R.anim.bottom_down)

        //setting the bottom down animation on the top layout
        binding.toplinear.animation = bottomdown

        //handler for other  layouts
        var handler = Handler(Looper.getMainLooper())
        var runnable = Runnable {
            //fade in  for other layouts

            binding.cardView.animation = fadein
            binding.texttview.animation = fadein
            binding.cardView2.animation = fadein
            binding.change.animation = fadein

        }

        handler.postDelayed(runnable, 1000)


        button = findViewById(R.id.button)
        button.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

        binding.textVieww.setOnClickListener {
            //set animation
            // Create Render Class
            val render = Render(this)

            // Set Animation
            render.setAnimation(Attention().Flash(binding.textVieww))
            render.start()

        }

    }

    private fun enableEdgeToEdge(){

    }
}