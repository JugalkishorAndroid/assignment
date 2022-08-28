package com.example.nasapictureapp.features.picture.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapictureapp.databinding.RowGridBinding
import com.example.nasapictureapp.features.picture.data.response.ImageResponse
import com.example.nasapictureapp.util.OnClickListener
import com.example.nasapictureapp.util.extension.loadImageFromUrl

class GridImageAdapter(var listener: OnClickListener) :
    RecyclerView.Adapter<GridImageAdapter.GridImageViewHolder>() {

    class GridImageViewHolder(val binding: RowGridBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageItem: ImageResponse) {
            binding.image.loadImageFromUrl(imageItem.url)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ImageResponse>() {
        override fun areItemsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ImageResponse, newItem: ImageResponse): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewHolder {
        val binding = RowGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

        holder.binding.image.setOnClickListener {
            listener.onClickItem(it, differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}