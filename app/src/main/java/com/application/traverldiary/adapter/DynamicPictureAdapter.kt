package com.application.traverldiary.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.application.MyApplication
import com.application.traverldiary.databinding.LayoutPiictureDynamicBinding
import com.bumptech.glide.Glide

class DynamicPictureAdapter(private val mDataset: List<Bitmap>) :
    RecyclerView.Adapter<DynamicPictureAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: LayoutPiictureDynamicBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(bitmap: Bitmap) {
            Glide.with(MyApplication.getContext())
                .load(bitmap)
                .into(binding.imageView)
            //binding.imageView.setImageBitmap(bitmap)
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