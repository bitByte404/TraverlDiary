package com.application.traverldiary.models

import java.util.Date

class Dynamic(
    val dynamicId: String,
    val title: String,
    val content: String,
    val pictures: List<Picture>, //图片不只一张
    val likes: Int,
    val postUser: User,
    val postTime: Date,
    //添加手机型号
    val phone: String = "Huawei P60",
    //添加评论
    var comments : ArrayList<Comment>
) {
    //将动态的图片Url全部取出
    fun getPictureUrls() : List<String> {
        val urls = arrayListOf<String>()
        pictures.forEach {
            urls.add(it.picture)
        }
        return urls
    }

//    fun getThubnails(width: Int = 500, height: Int = 500) : List<Bitmap> {
//        val thubnail = arrayListOf<Bitmap>()
//        pictures.forEach {
//            thubnail.add(BitmapUtils.getThumbnail(it.picture, width, height))
//        }
//        return thubnail
//    }
}