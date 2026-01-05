package com.example.diswis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaketWisataAdapter(private val packageList: List<PaketWisata>) :
    RecyclerView.Adapter<PaketWisataAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPackage: ImageView = view.findViewById(R.id.img_package)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDuration: TextView = view.findViewById(R.id.tv_duration)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val tvPrice: TextView = view.findViewById(R.id.tv_price)
        val btnPesan: Button = view.findViewById(R.id.btn_pesan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paket_wisata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = packageList[position]
        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description
        holder.tvDuration.text = item.duration
        holder.tvPrice.text = item.price
        holder.imgPackage.setImageResource(item.imageResId)

        // Note: Tags are currently static in XML as per screenshot implication of "Snack, Makan siang, Pemandu" being common.
        // In a real dynamic app, we would inflate chips into the LinearLayout dynamically.
        // For this task, static is fine or we can assume they are consistent.

        holder.btnPesan.setOnClickListener {
            val intent = android.content.Intent(holder.itemView.context, AturJadwalActivity::class.java)
            intent.putExtra("EXTRA_TITLE", item.title)
            intent.putExtra("EXTRA_PRICE", item.price)
            intent.putExtra("EXTRA_IMAGE", item.imageResId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = packageList.size
}
