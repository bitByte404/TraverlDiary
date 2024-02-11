package com.application.traverldiary.models

import java.util.Date

class Comment(
    val commentID: String,
    val dynamicID: String,
    val postUser: User,
    var content: String,
    var likes: Int,
    var postTime: Date,
    val IP: String
) {
}