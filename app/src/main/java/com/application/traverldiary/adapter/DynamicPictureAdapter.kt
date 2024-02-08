package com.application.traverldiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.application.BmobApp
import com.application.traverldiary.databinding.LayoutPiictureDynamicBinding
import com.bumptech.glide.Glide

class DynamicPictureAdapter(private val mDataset: List<String>) :
    RecyclerView.Adapter<DynamicPictureAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: LayoutPiictureDynamicBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureUrl: String) {
            Glide.with(BmobApp.getContext())
                .load(pictureUrl)
                .override(500, 500)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(LayoutPiictureDynamicBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mDataset[position])
    }
}