package com.application.traveldiary.models

import android.net.Uri
import cn.bmob.v3.BmobObject
import java.util.Date

class Dynamic(
    val dynamicId: String,
    val title: String,
    val content: String,
    val pictures: List<String>, //图片不只一张
    val likes: Int,
    val postUser: User,
    val postTime: Date,
    //添加手机型号
    val phone: String = "Huawei P60",
    //添加评论
    var comments : ArrayList<Comment>
) : BmobObject() {
    //将动态的图片Url全部取出
    fun getPictureUrls() : ArrayList<Uri> {
        val urls = arrayListOf<Uri>()
        pictures.forEach {
            urls.add(Uri.parse(it))
        }
        return urls
    }
}