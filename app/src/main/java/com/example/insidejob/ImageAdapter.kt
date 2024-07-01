package com.example.insidejob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insidejob.databinding.ImageitemviewBinding
import com.example.insidejob.databinding.NotesitemBinding

class ImageAdapter(val urls : List<String>)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder> ()
{
    inner class ImageViewHolder( val binding : ImageitemviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageitemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        Glide.with(holder.itemView.context).load(url).into(holder.binding.ivimage)
    }
}