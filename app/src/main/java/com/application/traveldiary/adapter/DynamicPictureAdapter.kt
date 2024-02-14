package com.application.traveldiary.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.application.BmobApp
import com.application.traveldiary.databinding.LayoutPiictureDynamicBinding
import com.bumptech.glide.Glide

/**
 * 动态图片的Adapter
 */
class DynamicPictureAdapter() :
    RecyclerView.Adapter<DynamicPictureAdapter.MyViewHolder>() {

        private var mDataset = arrayListOf<Uri>()

    // item回调事件
    interface onImageItemClickListener {
        fun onItemClick(imageUri: Uri)
    }

    private var listener: onImageItemClickListener? = null

    fun setOnImageItemClickListener(listener: onImageItemClickListener) {
        this.listener = listener
    }


    inner class MyViewHolder(val binding: LayoutPiictureDynamicBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureUrl: Uri) {
            Glide.with(BmobApp.getContext())
                .load(pictureUrl)
                .centerCrop()
                .into(binding.imageView)

            // 设置点击监听器
            itemView.setOnClickListener {
                this@DynamicPictureAdapter.listener?.onItemClick(pictureUrl)
            }
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

    fun setData(datas: ArrayList<Uri>) {
        mDataset = datas
        notifyDataSetChanged()
    }

    fun update(newData: Uri) {
        mDataset.add(newData)
        if (mDataset.size != 0) {
            notifyItemInserted(mDataset.size-1)
        } else {
            notifyItemInserted(0)
        }
    }
}