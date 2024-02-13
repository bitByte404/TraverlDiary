package com.application.traveldiary.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.databinding.LayoutAlbumHolderBinding
import uk.co.senab.photoview.PhotoView
import java.io.File

class DateAlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mList = listOf<Any>()
    private val DATE = 0
    private val PIC = 1
    var callback:((PhotoView)->Unit)? = null

    fun updateData(newData:List<Any>){
        mList = newData
        notifyDataSetChanged()  //数据改变通知视图改变
    }

    //包含日期和照片 所以分两个Holder
    class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView = itemView.findViewById(R.id.date) as TextView
    }
    class PhotoHolder(itemView: View,callback:((PhotoView)->Unit)?) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView.findViewById(R.id.image) as ImageView
        init {
            itemView.setOnClickListener {
                // 创建一个新的PhotoView
                val photoView = PhotoView(itemView.context)
                photoView.setImageBitmap(imageView.drawToBitmap())
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                photoView.layoutParams = layoutParams
                Log.v("wq","click")
                Log.v("wq","${callback == null}")
                // 设置ContentView为PhotoView
                callback!!(photoView)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATE){
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_holder,parent,false);
            DateHolder(view)
        }else{
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_album_holder,parent,false);
            PhotoHolder(view,callback)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateHolder){
            holder.textView.text = (mList[position] as String)
        }else if (holder is PhotoHolder){
            holder.imageView.setImageBitmap(fileToBitmap(mList[position] as File))
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
    private fun fileToBitmap(file: File):Bitmap {
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}