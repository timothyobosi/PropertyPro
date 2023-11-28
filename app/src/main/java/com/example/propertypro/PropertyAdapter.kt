package com.example.propertypro

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertypro.databinding.ItemPropertyBinding

class PropertyAdapter(private val properties: List<Propertys>) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = properties[position]
        // Bind property data to UI components in the ViewHolder
        holder.binding.textViewTitle.text = property.title
        holder.binding.textViewDescription.text = property.description

        // Set an OnClickListener on the item view
        holder.itemView.setOnClickListener {
            // Pass property data to PropertyDetailsActivity
            val intent = Intent(holder.itemView.context, PropertyDetailsActivity::class.java)
            intent.putExtra("property", property)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return properties.size
    }
}
