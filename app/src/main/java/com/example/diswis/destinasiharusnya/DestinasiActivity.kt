package com.example.diswis.destinasiharusnya

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diswis.R
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout

class DestinasiActivity : AppCompatActivity() {
    private lateinit var adapter: DestinationAdapter
    private lateinit var destinationList: List<Destination>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_destinasi_harusnya)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // Adjust header elements
            val btnBack = findViewById<android.view.View>(R.id.btnBack)
            val tvTitle = findViewById<android.view.View>(R.id.tvTitle)
            val headerBg = findViewById<android.view.View>(R.id.headerBg)

            val lpBack = btnBack.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            lpBack.topMargin = 40.toInt().toPx(this) + systemBars.top
            btnBack.layoutParams = lpBack

            // tvTitle is constrained to btnBack, so it should follow. 
            // But let's check if we need to adjust the height of headerBg
            val lpHeader = headerBg.layoutParams
            lpHeader.height = 100.toInt().toPx(this) + systemBars.top
            headerBg.layoutParams = lpHeader

            // Adjust bottomNav
            val bottomNav = findViewById<android.view.View>(R.id.bottomNav)
            bottomNav.setPadding(0, 0, 0, systemBars.bottom)
            val lpBottom = bottomNav.layoutParams
            lpBottom.height = 60.toInt().toPx(this) + systemBars.bottom
            bottomNav.layoutParams = lpBottom

            insets
        }
        
        WishlistManager.init(this)
        
        // Handle Back Stack Changes for Visibility
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                findViewById<View>(R.id.mainContent).visibility = View.VISIBLE
                findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
            }
        }
        
        // Data Preparation
        destinationList = DestinationData.list

        // RecyclerView Setup
        val rvDestinations = findViewById<RecyclerView>(R.id.rvDestinations)
        adapter = DestinationAdapter(destinationList) { destination ->
            openDetail(destination.name, destination.location, destination.price, destination.imageRes)
        }
        rvDestinations.layoutManager = LinearLayoutManager(this)
        rvDestinations.adapter = adapter

        // Search Logic
        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        
        // Wishlist button
        findViewById<View>(R.id.btnWishlist).setOnClickListener {
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.btnHome).setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Handle Incoming Intent
        val targetName = intent.getStringExtra("TARGET_DESTINATION")
        if (targetName != null) {
            val targetLoc = intent.getStringExtra("TARGET_LOCATION") ?: "-"
            val targetPrice = intent.getStringExtra("TARGET_PRICE") ?: "-"
            val targetImg = intent.getIntExtra("TARGET_IMAGE", R.drawable.candi_prambanan)
            openDetail(targetName, targetLoc, targetPrice, targetImg)
        }
    }

    private fun openDetail(name: String, location: String, price: String, imageRes: Int) {
        val upperName = name.uppercase()
        when (upperName) {
            "CANDI PRAMBANAN" -> showFragment(DetailCandiPrambananFragment())
            "MONUMEN JOGJA KEMBALI" -> showFragment(DetailMuseumJogjaFragment())
            "MUSEUM GUNUNG MERAPI" -> showFragment(DetailMuseumMerapiFragment())
            "HEHA SKY VIEW" -> showFragment(DetailHehaSkyViewFragment())
            "OBELIX HILLS" -> showFragment(DetailObelixHillsFragment())
            else -> {
                val fragment = DetailGenericFragment.newInstance(name, location, price, imageRes)
                showFragment(fragment)
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        val mainContent = findViewById<View>(R.id.mainContent)
        val container = findViewById<View>(R.id.fragmentContainer)
        
        container.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()

        // Delay hiding main content slightly to ensure the animation starts smoothly
        mainContent.postDelayed({
            if (supportFragmentManager.backStackEntryCount > 0) {
                mainContent.visibility = View.GONE
            }
        }, 50) 
    }
    private fun Int.toPx(context: android.content.Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
