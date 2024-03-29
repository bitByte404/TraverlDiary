package com.application.traveldiary.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.manager.AlbumManager
import com.application.traveldiary.models.Picture
import com.bumptech.glide.Glide

class DateAlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mList = listOf<Any>()
    var spanCount = 4
    private val DATE = 0
    private val PIC = 1
    private var choosing = false
    var callback:((Picture)->Unit) = {}

    fun updateData(newData: List<Any>) {
        mList = newData
        notifyDataSetChanged()  //数据改变通知视图改变
        System.gc()
    }

    //包含日期和照片 所以分两个Holder
    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.date) as TextView
    }

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image) as ImageView
        val chooseView: ImageView = itemView.findViewById(R.id.circle) as ImageView

        fun bind(spanCount: Int,picture: Picture) {
            setShape(spanCount)
            imageView.setOnClickListener{
                this@DateAlbumAdapter.callback(picture)
            }
        }

        private fun setShape(spanCount:Int){
            val screenWidth = itemView.resources.displayMetrics.widthPixels
            val imageViewWidth = screenWidth / spanCount

            val layoutParams = imageView.layoutParams
            layoutParams.width = imageViewWidth
            layoutParams.height = imageViewWidth
            imageView.layoutParams = layoutParams
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_date_holder, parent, false);
            DateHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_album_holder, parent, false);
            PhotoHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateHolder) {
            holder.textView.text = (mList[position] as String)
        } else if (holder is PhotoHolder) {
            Glide.with(holder.imageView.context)
                .load((mList[position] as Picture).uri)
                .override(256,256)
                .placeholder(R.drawable.icon_image_loading)
                .into(holder.imageView)
            holder.bind(spanCount,mList[position] as Picture)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    //得到itemview的类型
    override fun getItemViewType(position: Int): Int {
        return if (mList[position] is String) {
            DATE
        } else {
            PIC
        }
    }


    //区分日期和照片 使日期占满一行
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val manager: RecyclerView.LayoutManager? = recyclerView.layoutManager;
        if (manager is GridLayoutManager) {
            val gridLayoutManager = manager as GridLayoutManager
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == DATE) gridLayoutManager.spanCount else 1
                }
            }
        }

    }

}