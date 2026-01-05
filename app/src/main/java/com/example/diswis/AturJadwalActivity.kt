package com.example.diswis

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class AturJadwalActivity : AppCompatActivity() {

    private var basePrice: Int = 0
    private var adultCount: Int = 1
    private var childCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_atur_jadwal)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, v.paddingBottom)
            insets
        }

        // Get data from intent
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Paket Wisata"
        val priceString = intent.getStringExtra("EXTRA_PRICE") ?: "0k"
        val imageResId = intent.getIntExtra("EXTRA_IMAGE", R.drawable.candi_prambanan)

        // Parse price (assuming "80k" format -> 80000)
        basePrice = parsePrice(priceString)

        // Setup UI
        findViewById<ImageView>(R.id.btn_back).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tv_package_name_detail).text = title
        findViewById<TextView>(R.id.tv_price_detail).text = priceString
        findViewById<ImageView>(R.id.img_selected_package).setImageResource(imageResId)

        setupCounters()
        updateTotalPrice()
        
        findViewById<Button>(R.id.btn_pesan_sekarang).setOnClickListener {
            // Toast.makeText(this, "Pesanan berhasil dibuat!", Toast.LENGTH_SHORT).show()
            val totalPrice = findViewById<TextView>(R.id.tv_total_price).text.toString()
            
            val intent = Intent(this, PembayaranActivity::class.java)
            intent.putExtra("EXTRA_TITLE", title)
            intent.putExtra("EXTRA_TOTAL_PRICE", totalPrice)
            intent.putExtra("EXTRA_IMAGE", imageResId)
            startActivity(intent)
        }
    }

    private fun parsePrice(priceDiff: String): Int {
        return try {
            priceDiff.replace("k", "000").replace("K", "000").replace("Rp", "").replace(".", "").replace(" ", "").toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun setupCounters() {
        val tvAdult = findViewById<TextView>(R.id.tv_count_adult)
        val tvChild = findViewById<TextView>(R.id.tv_count_child)

        findViewById<ImageView>(R.id.btn_minus_adult).setOnClickListener {
            if (adultCount > 1) {
                adultCount--
                tvAdult.text = adultCount.toString()
                updateTotalPrice()
            }
        }

        findViewById<ImageView>(R.id.btn_plus_adult).setOnClickListener {
            adultCount++
            tvAdult.text = adultCount.toString()
            updateTotalPrice()
        }

        findViewById<ImageView>(R.id.btn_minus_child).setOnClickListener {
            if (childCount > 0) {
                childCount--
                tvChild.text = childCount.toString()
                updateTotalPrice()
            }
        }

        findViewById<ImageView>(R.id.btn_plus_child).setOnClickListener {
            childCount++
            tvChild.text = childCount.toString()
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        val total = (adultCount * basePrice) + (childCount * basePrice)
        
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        findViewById<TextView>(R.id.tv_total_price).text = format.format(total).replace("Rp", "Rp ")
    }
}
