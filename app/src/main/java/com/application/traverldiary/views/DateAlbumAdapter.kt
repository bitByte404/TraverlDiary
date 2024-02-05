package com.application.traverldiary.views

import android.media.Image
import android.provider.ContactsContract.Contacts.Photo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.R
import com.application.traverldiary.models.Picture
import java.util.Date
import java.util.Objects

class DateAlbumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mList = listOf<Any>()
    val DATE = 0
    val PIC = 0

    class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView = itemView as TextView

    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DATE){
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_date_holder,parent,false);
            return DateHolder(view)
        }else{
            val view  = LayoutInflater.from(parent.context).inflate(R.layout.layout_album_holder,parent,false);
            return PhotoHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateHolder){
            holder.textView.text = mList[position] as String
        }else if (holder is PhotoHolder){
            holder.imageView.setImageResource(mList[position] as Int)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position] is String){
            DATE
        }else{
            PIC
        }
    }


}