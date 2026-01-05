package com.example.diswis.destinasiharusnya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diswis.R
import java.util.*

class DestinationAdapter(
    private var destinationList: List<Destination>,
    private val onClick: (Destination) -> Unit
) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    private var filteredList: List<Destination> = destinationList

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDestination: ImageView = view.findViewById(R.id.ivDestination)
        val tvName: TextView = view.findViewById(R.id.tvDestinationName)
        val tvLocation: TextView = view.findViewById(R.id.tvDestinationLocation)
        val tvPrice: TextView = view.findViewById(R.id.tvDestinationPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destination = filteredList[position]
        try {
            holder.ivDestination.setImageResource(destination.imageRes)
        } catch (e: Exception) {
            holder.ivDestination.setImageResource(R.drawable.candi_prambanan)
        }
        holder.tvName.text = destination.name
        holder.tvLocation.text = destination.location
        holder.tvPrice.text = destination.price
        
        holder.itemView.setOnClickListener { onClick(destination) }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase(Locale.getDefault())
        filteredList = if (lowerCaseQuery.isEmpty()) {
            destinationList
        } else {
            destinationList.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                it.location.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        notifyDataSetChanged()
    }
}
