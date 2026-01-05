package com.example.diswis.destinasiharusnya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.diswis.R

class DetailMuseumJogjaFragment : Fragment() {
    private lateinit var btnFavorite: ImageView
    private val destinationName = "MONUMEN JOGJA KEMBALI"
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_museum_jogja, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        WishlistManager.init(requireContext())
        
        btnFavorite = view.findViewById(R.id.btnFavorite)
        
        // Update favorite icon based on wishlist status
        updateFavoriteIcon()
        
        // Back button logic
        view.findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        // Favorite button logic
        btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        // Send Review logic
        val etAddReview = view.findViewById<android.widget.EditText>(R.id.etAddReview)
        val btnSendReview = view.findViewById<android.view.View>(R.id.btnSendReview)
        val llReviewsContainer = view.findViewById<android.widget.LinearLayout>(R.id.llReviewsContainer)

        btnSendReview.setOnClickListener {
            val reviewText = etAddReview.text.toString().trim()
            if (reviewText.isNotEmpty()) {
                addReview(llReviewsContainer, reviewText)
                etAddReview.text.clear()
            }
        }
    }
    
    private fun addReview(container: android.widget.LinearLayout, text: String) {
        val view = android.view.LayoutInflater.from(requireContext()).inflate(R.layout.item_review, container, false)
        val tvReviewContent = view.findViewById<android.widget.TextView>(R.id.tvReviewContent)
        tvReviewContent.text = text
        container.addView(view)
    }
    
    private fun toggleFavorite() {
        if (WishlistManager.isInWishlist(destinationName)) {
            WishlistManager.removeFromWishlist(destinationName)
        } else {
            val destination = Destination(
                destinationName,
                "Jalan Ring Road Utara, Kec. Ngaglik, Kab. Sleman, D.I.Y",
                "Rp15.000",
                R.drawable.museum_jogja
            )
            WishlistManager.addToWishlist(destination)
        }
        updateFavoriteIcon()
    }
    
    private fun updateFavoriteIcon() {
        if (WishlistManager.isInWishlist(destinationName)) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
