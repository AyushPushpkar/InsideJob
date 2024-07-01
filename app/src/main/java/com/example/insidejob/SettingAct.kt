package com.example.insidejob

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingAct : AppCompatActivity() {

    private lateinit var switchMode: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences
    private var nightMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        switchMode = findViewById(R.id.switchMode)
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMode = sharedPreferences.getBoolean("nightMode", false)

        updateNightMode(nightMode)

        switchMode.setOnClickListener {
            nightMode = !nightMode
            updateNightMode(nightMode)
            sharedPreferences.edit().putBoolean("nightMode", nightMode).apply()
        }
    }
    private fun updateNightMode(enable: Boolean) {
        val mode = if (enable) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
        switchMode.isChecked = enable

//        delegate.localNightMode = mode
//        switchMode.isChecked = enable
    }
//
    private fun enableEdgeToEdge() {
        // Your edge-to-edge enabling code
    }
}