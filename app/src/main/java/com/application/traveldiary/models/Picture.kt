package com.application.traveldiary.models

import cn.bmob.v3.BmobObject
import java.util.Date

class Picture(
    val picture: String, //TODO 修改为链接地址
    val takeTime: Date,
    val location: String,
    val title: String = "",
) : BmobObject() {
}