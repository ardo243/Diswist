package com.example.destinasiharusnya

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishlistActivity : AppCompatActivity() {
    private lateinit var adapter: DestinationAdapter
    private lateinit var rvWishlist: RecyclerView
    private lateinit var emptyState: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wishlist)
        
        WishlistManager.init(this)
        
        // Optional: clear if it keeps crashing (uncomment if necessary)
        // WishlistManager.clearWishlist()
        
        rvWishlist = findViewById(R.id.rvWishlist)
        emptyState = findViewById(R.id.emptyState)
        
        // Back button
        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
        
        // Home button
        findViewById<View>(R.id.btnHome).setOnClickListener {
            finish()
        }
        
        // Explore button (Empty State)
        findViewById<View>(R.id.btnExplore).setOnClickListener {
            finish()
        }
        
        loadWishlist()
    }
    
    override fun onResume() {
        super.onResume()
        loadWishlist()
    }
    
    private fun loadWishlist() {
        val wishlist = WishlistManager.getWishlist()
        
        if (wishlist.isEmpty()) {
            rvWishlist.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            rvWishlist.visibility = View.VISIBLE
            emptyState.visibility = View.GONE
            
            adapter = DestinationAdapter(wishlist) { destination ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("TARGET_DESTINATION", destination.name)
                intent.putExtra("TARGET_LOCATION", destination.location)
                intent.putExtra("TARGET_PRICE", destination.price)
                intent.putExtra("TARGET_IMAGE", destination.imageRes)
                startActivity(intent)
            }
            rvWishlist.layoutManager = LinearLayoutManager(this)
            rvWishlist.adapter = adapter
        }
    }
}
