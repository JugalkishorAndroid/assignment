package com.example.nasapictureapp.features.details.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapictureapp.databinding.RowDetailsBinding
import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.util.extension.loadImageFromUrl

class ViewRecyclerAdapter(private val dataValue: List<ImageResponse>) : RecyclerView.Adapter<ViewRecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(val binding: RowDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: ImageResponse) {
            binding.imageView.loadImageFromUrl(imageItem.url)
            binding.data = imageItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = RowDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(dataValue[position])
    }

    override fun getItemCount(): Int {
        return dataValue.size
    }
}