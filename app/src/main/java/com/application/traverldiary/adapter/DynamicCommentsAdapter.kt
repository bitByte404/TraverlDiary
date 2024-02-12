package com.application.traverldiary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.databinding.LayoutCommentsDynamicBinding
import com.application.traverldiary.models.Comment


/**
 * 动态评论适配器
 */
class DynamicCommentsAdapter : RecyclerView.Adapter<DynamicCommentsAdapter.MyViewHolder>() {
    //保存评论信息
    private var mComments = emptyList<Comment>()
    class MyViewHolder(val binding: LayoutCommentsDynamicBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            //设置评论的用户名
            binding.username.text = "${comment.postUser.name}: "
            //设置评论的内容
            binding.content.text = comment.content
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(LayoutCommentsDynamicBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return mComments.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mComments[position])
        Log.v("comment", position.toString())
    }

    //设置数据
    fun setData(comments: List<Comment>) {
        mComments = comments
    }

}