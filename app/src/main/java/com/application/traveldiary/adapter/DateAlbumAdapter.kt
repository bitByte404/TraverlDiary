package com.application.traveldiary.adapter

import android.graphics.BitmapFactory
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
import java.io.File

class DateAlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mList = listOf<Any>()
    private val DATE = 0
    private val PIC = 1


    fun updateData(newData:List<Any>){
        mList = newData
        notifyDataSetChanged()  //数据改变通知视图改变
    }

    //包含日期和照片 所以分两个Holder
    class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView = itemView.findViewById(R.id.date) as TextView
    }
    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView.findViewById(R.id.image) as ImageView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATE){
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_holder,parent,false);
            DateHolder(view)
        }else{
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_album_holder,parent,false);
            PhotoHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateHolder){
            Log.v("wq","${mList[position]}")
            holder.textView.text = (mList[position] as String)
        }else if (holder is PhotoHolder){
            setImageViewFromFile(holder.imageView,mList[position] as File)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    //得到itemview的类型
    override fun getItemViewType(position: Int): Int {
        return if (mList[position] is String){
            DATE
        }else{
            PIC
        }
    }


    //区分日期和照片 使日期占满一行
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val manager: RecyclerView.LayoutManager? = recyclerView.layoutManager;
        if(manager is GridLayoutManager) {
           val gridLayoutManager = manager as GridLayoutManager
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == DATE) gridLayoutManager.spanCount else 1
                }
            }
        }

    }

    //读取文件 设置到imageview上
    private fun setImageViewFromFile(imageView: ImageView, file: File) {
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView.setImageBitmap(bitmap)
        };
    }
}