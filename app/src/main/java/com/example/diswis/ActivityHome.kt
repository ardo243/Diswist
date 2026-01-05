package com.example.diswis

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // Apply top inset as padding to the profile/greeting container instead of the root
            val profileCard = findViewById<android.view.View>(R.id.profile_card)
            val icNotification = findViewById<android.view.View>(R.id.ic_notification)
            
            // Adjust the top margin or padding of the elements inside the header
            val lpProfile = profileCard.layoutParams as ConstraintLayout.LayoutParams
            lpProfile.topMargin = 32 + systemBars.top // Original 32dp + status bar height
            profileCard.layoutParams = lpProfile
            
            val lpNotification = icNotification.layoutParams as ConstraintLayout.LayoutParams
            lpNotification.topMargin = systemBars.top // Align with profile card top margin adjustment
            icNotification.layoutParams = lpNotification

            // Apply bottom inset as padding to the bottom navigation bar
            val bottomNav = findViewById<android.view.View>(R.id.bottomNav)
            bottomNav.setPadding(0, 0, 0, systemBars.bottom)
            
            // Adjust bottomNav height to accommodate padding if necessary
            val lpBottomNav = bottomNav.layoutParams
            lpBottomNav.height = 60.toInt().toPx(this) + systemBars.bottom
            bottomNav.layoutParams = lpBottomNav

            insets
        }

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
            findViewById<android.view.View>(R.id.profile_image).setOnClickListener {
                ProfileFragment()
            }

// 2. Tombol User di Bottom Navigation
            val navUser = findViewById<android.widget.ImageView>(R.id.nav_user)
            navUser.setOnClickListener {
                ProfileFragment()
            }
        }

        findViewById<android.view.View>(R.id.nav_fav).setOnClickListener {
            val intent = Intent(this, com.example.diswis.destinasiharusnya.WishlistActivity::class.java)
            startActivity(intent)
        }
    }
    
    // Helper function to convert dp to px
    private fun Int.toPx(context: android.content.Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}