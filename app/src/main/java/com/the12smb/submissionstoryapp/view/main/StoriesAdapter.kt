package com.the12smb.submissionstoryapp.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the12smb.submissionstoryapp.data.remote.response.ListStoryItem
import com.the12smb.submissionstoryapp.databinding.ItemCardviewStoryBinding

class StoriesAdapter(private val listStories: List<ListStoryItem>) :
    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCardviewStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivItemPhoto: ImageView = binding.ivItemPhoto
        val tvItemName: TextView = binding.tvItemName
        val tvItemDescription: TextView = binding.tvItemDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardviewStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = listStories[position]
        holder.tvItemName.text = users.name
        holder.tvItemDescription.text = users.description
        Glide.with(holder.itemView.context)
            .load(users.photoUrl)
            .into(holder.ivItemPhoto)

//        holder.itemView.setOnClickListener{
//            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
//            intentDetail.putExtra(DetailActivity.EXTRA_LOGIN, users.login)//intent data login
//            holder.itemView.context.startActivity(intentDetail)
//        }
    }

    override fun getItemCount(): Int {
        return listStories.size
    }

}