package com.example.diswis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DestinationAdapter(private val destinations: List<Destination>) :
    RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDestination: ImageView = view.findViewById(R.id.img_destination)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvPrice: TextView = view.findViewById(R.id.tv_price)
        val btnDetail: Button = view.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = destinations[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = item.price
        holder.imgDestination.setImageResource(item.imageResId)

        holder.btnDetail.setOnClickListener {
            // Placeholder for click
        }
    }

    override fun getItemCount() = destinations.size
}
