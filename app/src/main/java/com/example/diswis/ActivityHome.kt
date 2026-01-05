package com.example.diswis

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fragment is loaded via FragmentContainerView in XML, no manual transaction needed here
        // unless you want to replace it dynamically later.

        // Setup Categories
        findViewById<LinearLayout>(R.id.btn_destinasi).setOnClickListener {
             val intent = Intent(this, com.example.diswis.destinasiharusnya.DestinasiActivity::class.java)
             startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.btn_paket_wisata).setOnClickListener {
            val intent = Intent(this, PaketWisataActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.btn_kuliner).setOnClickListener {
            // val intent = Intent(this, KulinerActivity::class.java)
            // startActivity(intent)
        }

        // Navigation
        findViewById<android.view.View>(R.id.profile_image).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val navUser = findViewById<android.widget.ImageView>(R.id.nav_user)
        navUser.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        findViewById<android.view.View>(R.id.nav_fav).setOnClickListener {
            val intent = Intent(this, com.example.diswis.destinasiharusnya.WishlistActivity::class.java)
            startActivity(intent)
        }
    }
}