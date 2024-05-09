package com.android.mysearch_application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mysearch_application.data.Document
import com.bumptech.glide.Glide
import com.example.mysearch_application.databinding.ItemRecyclerBinding

class SearchAdapter(private val onClick: (Document) -> Unit)
    : RecyclerView.Adapter<SearchAdapter.Holder>() {
    var data: List<Document> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onClick(currentItem)
        }
    }

    override fun getItemCount(): Int = data.size

    class Holder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Document) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(imgCollection)

                tvSite.text = item.siteName
                tvDate.text = item.dateTime.replace("T", " ").substring(0, 19)

                icHeart.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            }
        }
    }
}
