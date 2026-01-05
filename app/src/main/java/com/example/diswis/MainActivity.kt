package com.example.diswis

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnMasuk = findViewById<android.widget.Button>(R.id.btnMasuk)
        val btnDaftar = findViewById<android.widget.Button>(R.id.btnDaftar)
        btnMasuk.setOnClickListener {
            val intent = android.content.Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }
        btnDaftar.setOnClickListener {
            val intent = android.content.Intent(this, ActivityRegestrasi::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}