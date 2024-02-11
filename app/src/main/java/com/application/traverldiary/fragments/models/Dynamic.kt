package com.application.traverldiary.fragments.models

import java.util.Date

class Dynamic(
    val dynamicId: String,
    val title: String,
    val content: String,
    val picture: Picture,
    val likes: Int,
    val postUser: User,
    val postTime: Date
) {
}