package com.example.diswis

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pembayaran)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, v.paddingBottom)
            insets
        }

        // Get Data
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Paket Wisata"
        val price = intent.getStringExtra("EXTRA_TOTAL_PRICE") ?: "Rp 0"
        val imageResId = intent.getIntExtra("EXTRA_IMAGE", R.drawable.candi_prambanan)

        // Setup UI
        findViewById<ImageView>(R.id.btn_back).setOnClickListener { finish() }
        findViewById<ImageView>(R.id.img_summary).setImageResource(imageResId)
        findViewById<TextView>(R.id.tv_title_summary).text = title
        findViewById<TextView>(R.id.tv_price_summary).text = price
        findViewById<TextView>(R.id.tv_total_final).text = price

        // Payment Method Logic (Mutual Exclusion)
        val rgEwallet = findViewById<RadioGroup>(R.id.rg_ewallet)
        val rbQris = findViewById<RadioButton>(R.id.rb_qris)

        // When E-Wallet group is checked, uncheck QRIS
        rgEwallet.setOnCheckedChangeListener { _, _ ->
            if (rgEwallet.checkedRadioButtonId != -1) {
                rbQris.isChecked = false
            }
        }

        // When QRIS is checked, clear E-Wallet group
        rbQris.setOnClickListener {
            rgEwallet.clearCheck()
            rbQris.isChecked = true
        }

        findViewById<Button>(R.id.btn_pay_now).setOnClickListener {
            val isEwalletSelected = rgEwallet.checkedRadioButtonId != -1
            val isQrisSelected = rbQris.isChecked

            if (isEwalletSelected || isQrisSelected) {
                Toast.makeText(this, "Pembayaran Berhasil!", Toast.LENGTH_LONG).show()
                // Navigate back to Home or History
                // val intent = Intent(this, HomeActivity::class.java)
                // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                // startActivity(intent)
            } else {
                Toast.makeText(this, "Mohon pilih metode pembayaran", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
