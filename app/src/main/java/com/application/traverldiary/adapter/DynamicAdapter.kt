package com.application.traverldiary.adapter

import BitmapUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.application.MyApplication
import com.application.traverldiary.databinding.ActivityMainBinding
import com.application.traverldiary.databinding.LayoutDynamicBinding
import com.application.traverldiary.models.Dynamic

class DynamicAdapter : RecyclerView.Adapter<DynamicAdapter.MyViewHolder>() {

    //保存动态信息
    private var mDynamics = emptyList<Dynamic>()

    class MyViewHolder(val binding: LayoutDynamicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dynamic: Dynamic) {

            //设置头像
//            BitmapUtils.setBitmapToImageView(
//                binding.avatar,
//                dynamic.postUser.head
//            )
            binding.avatar.setImageBitmap(dynamic.postUser.head)
            //设置评论数
            binding.chatCount.text = dynamic.comments.size.toString()
            //设置喜欢数
            binding.loveCount.text = dynamic.likes.toString()
            //设置评论
            //设置评论为垂直
            binding.recyclerviewComments.layoutManager = LinearLayoutManager(
                MyApplication.getContext(),
                RecyclerView.VERTICAL,
                false
            )
            DynamicCommentsAdapter().apply {
                binding.recyclerviewComments.adapter = this
                setData(dynamic.comments)
            }

            //配置动态的图片
            binding.recyclerviewPictures.apply {
                layoutManager = GridLayoutManager(MyApplication.getContext(), 3)
                adapter = DynamicPictureAdapter(dynamic.getPictureBitmaps())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(LayoutDynamicBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return mDynamics.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mDynamics[position])
    }
}