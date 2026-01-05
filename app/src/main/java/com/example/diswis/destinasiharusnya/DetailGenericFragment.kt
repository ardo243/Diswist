package com.example.diswis.destinasiharusnya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.diswis.R

class DetailGenericFragment : Fragment() {
    private lateinit var btnFavorite: ImageView
    private var destinationName: String = "Unknown Destination"
    private var location: String = "-"
    private var price: String = "-"
    private var imageRes: Int = R.drawable.candi_prambanan

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_IMAGE = "extra_image"

        fun newInstance(name: String, location: String, price: String, imageRes: Int): DetailGenericFragment {
            val fragment = DetailGenericFragment()
            val args = Bundle()
            args.putString(EXTRA_NAME, name)
            args.putString(EXTRA_LOCATION, location)
            args.putString(EXTRA_PRICE, price)
            args.putInt(EXTRA_IMAGE, imageRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            destinationName = it.getString(EXTRA_NAME, "Unknown Destination")
            location = it.getString(EXTRA_LOCATION, "-")
            price = it.getString(EXTRA_PRICE, "-")
            imageRes = it.getInt(EXTRA_IMAGE, R.drawable.candi_prambanan)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_generic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        WishlistManager.init(requireContext())

        // Initialize Views
        btnFavorite = view.findViewById(R.id.btnFavorite)
        val ivImage = view.findViewById<ImageView>(R.id.ivDestinationImage)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvLocation = view.findViewById<TextView>(R.id.tvLocation)
        val tvTicketPrice = view.findViewById<TextView>(R.id.tvTicketPrice)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        
        // Populate Data
        tvTitle.text = destinationName
        tvLocation.text = location
        tvTicketPrice.text = price
        try {
            ivImage.setImageResource(imageRes)
        } catch (e: Exception) {
            ivImage.setImageResource(R.drawable.candi_prambanan)
        }

        // Update favorite icon based on wishlist status
        updateFavoriteIcon()
        
        // Back button logic using FragmentManager
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        // Favorite button logic
        btnFavorite.setOnClickListener {
            toggleFavorite()
        }
    }
    
    private fun toggleFavorite() {
        if (WishlistManager.isInWishlist(destinationName)) {
            WishlistManager.removeFromWishlist(destinationName)
        } else {
            val destination = Destination(
                destinationName,
                location,
                price,
                imageRes
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
