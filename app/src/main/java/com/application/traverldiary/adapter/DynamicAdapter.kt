package com.application.traverldiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.application.traverldiary.application.MyApplication
import com.application.traverldiary.databinding.LayoutDynamicBinding
import com.application.traverldiary.models.Dynamic
import com.application.traverldiary.utils.DateUtils
import com.bumptech.glide.Glide

class DynamicAdapter : RecyclerView.Adapter<DynamicAdapter.MyViewHolder>() {

    //保存动态信息
    private var mDynamics = emptyList<Dynamic>()

    class MyViewHolder(val binding: LayoutDynamicBinding) : RecyclerView.ViewHolder(binding.root) {
        val context = MyApplication.getContext()
        fun bind(dynamic: Dynamic) {

            //设置头像
            Glide.with(context)
                .load(dynamic.postUser.head)
                .circleCrop()
                .into(binding.avatar)
            //设置评论数
            binding.chatCount.text = dynamic.comments.size.toString()
            //设置喜欢数
            binding.loveCount.text = dynamic.likes.toString()
            //设置用户名
            binding.userName.text = dynamic.postUser.name
            //设置标题
            binding.title.text = dynamic.title
            //设置评论的条数
            binding.allComment.text = "查看全部${dynamic.comments.size}条评论"
            //设置手机型号
            binding.phone.text = dynamic.phone
            //设置日期
            binding.publishTime.text = DateUtils.getStringFromDate(dynamic.postTime)
            //设置评论
            //设置评论为垂直
            binding.recyclerviewComments.layoutManager = LinearLayoutManager(
                MyApplication.getContext())

            //设置评论Adapter
            val commentsAdapter = DynamicCommentsAdapter()
            commentsAdapter.setData(dynamic.comments)
            binding.recyclerviewComments.adapter = commentsAdapter

            //配置动态的图片
            val pictureAdapter = DynamicPictureAdapter(dynamic.getPictureUrls())
            binding.recyclerviewPictures.layoutManager =
                GridLayoutManager(context, 3)
            binding.recyclerviewPictures.adapter = pictureAdapter
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

    //设置动态的数据
    fun setData(dynamics: List<Dynamic>) {
        mDynamics = dynamics
    }
}