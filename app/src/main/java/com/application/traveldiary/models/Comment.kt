package com.application.traveldiary.models

import cn.bmob.v3.BmobObject
import java.util.Date

class Comment(
    val commentID: String,
    val dynamicID: String,
    val postUser: User,
    var content: String,
    var likes: Int,
    var postTime: Date,
    val IP: String
) : BmobObject() {
}