package com.example.diswis

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaketWisataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_paket_wisata)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, v.paddingBottom)
            insets
        }
        // Setup Back Button
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        // Setup RecyclerView
        val rvPaketWisata = findViewById<RecyclerView>(R.id.rv_paket_wisata)
        rvPaketWisata.layoutManager = LinearLayoutManager(this)

        val packages = listOf(
            PaketWisata(
                "Embung Tambak Boyo & Prambanan",
                "Suasana embung yang menarik dan anda bisa menyaksikan candi yang menjadi saksi bisu",
                "1 Hari",
                "80k",
                R.drawable.candi_prambanan, // Using existing reliable resource
                listOf("Snack", "Makan siang", "Pemandu")
            ),
            PaketWisata(
                "Embung Tambak Boyo & Prambanan",
                "Suasana embung yang menarik dan anda bisa menyaksikan candi yang menjadi saksi bisu",
                "1 Hari",
                "80k",
                R.drawable.candi_prambanan,
                listOf("Snack", "Makan siang", "Pemandu")
            ),
             PaketWisata(
                "Paket Hemat Jogja",
                "Keliling malioboro dan keraton bersama pemandu lokal profesional",
                "1 Hari",
                "150k",
                R.drawable.jogja, // Assuming this exists from previous Destination usage
                listOf("Snack", "Makan siang", "Pemandu")
            )
        )

        val adapter = PaketWisataAdapter(packages)
        rvPaketWisata.adapter = adapter

        // Setup Bottom Navigation Logic (Simple Redirection)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<ImageView>(R.id.nav_home).setOnClickListener {
            val intent = Intent(this, ActivityHome::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        
        findViewById<ImageView>(R.id.nav_user).setOnClickListener {
             val intent = Intent(this, ProfileActivity::class.java)
             startActivity(intent)
        }
    }
}
