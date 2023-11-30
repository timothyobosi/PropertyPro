package com.example.propertypro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PropertyListAdapter :
    ListAdapter<PropertyItem, PropertyListAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewProperty)

        fun bind(property: PropertyItem) {
            titleTextView.text = property.title
            descriptionTextView.text = property.description
            imageView.setImageResource(property.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PropertyDiffCallback : DiffUtil.ItemCallback<PropertyItem>() {
    override fun areItemsTheSame(oldItem: PropertyItem, newItem: PropertyItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PropertyItem, newItem: PropertyItem): Boolean {
        return oldItem == newItem
    }
}
