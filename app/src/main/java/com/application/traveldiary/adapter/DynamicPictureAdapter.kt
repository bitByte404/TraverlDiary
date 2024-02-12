package com.application.traveldiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.application.BmobApp
import com.application.traveldiary.databinding.LayoutPiictureDynamicBinding
import com.bumptech.glide.Glide

/**
 * 动态图片的Adapter
 */
class DynamicPictureAdapter(val isAdd: Boolean = false) :
    RecyclerView.Adapter<DynamicPictureAdapter.MyViewHolder>() {

        private var mDataset = arrayListOf<String>()

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
        if (isAdd == false) {
            return mDataset.size
        } else {
            return mDataset.size + 1
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if ((position == mDataset.size && isAdd) || (mDataset.isEmpty() && isAdd)) {
            // 最后一个位置，显示预设的ImageView
            holder.bind("https://imgs.wuliner.cn/imgs/202402122101730.png")
        } else {
            holder.bind(mDataset[position])
        }
    }

    fun setData(datas: ArrayList<String>) {
        mDataset = datas
    }

    fun update(newData: String) {
        mDataset.add(newData)
        if (mDataset.size != 0) {
            notifyItemInserted(mDataset.size-1)
        }
    }
}