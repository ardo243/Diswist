package com.example.diswis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TopDestinationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_destinations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvDestinations = view.findViewById<RecyclerView>(R.id.rv_top_destinations)
        rvDestinations.layoutManager = LinearLayoutManager(context)
        rvDestinations.isNestedScrollingEnabled = false // Important for nested scrolling in ActivityHome

        // Initialize data
        val destinations = listOf(
            Destination("Candi Prambanan", "Rp 50.000", R.drawable.candi_prambanan),
            Destination("Candi Prambanan", "Rp 50.000", R.drawable.candi_prambanan),
            Destination("Tempat Lain", "Rp 75.000", R.drawable.jogja)
        )

        val adapter = DestinationAdapter(destinations)
        rvDestinations.adapter = adapter
    }
}
