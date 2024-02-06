package com.application.traverldiary.manager

import com.application.traverldiary.models.Picture
import java.text.SimpleDateFormat
import java.util.Date

class AlbumManager private constructor() {
    var instance: AlbumManager? = null
    var fileDir = ""

    fun getInstance(fileDir:String):AlbumManager {
        if(instance == null) {
            synchronized(this){
                if (instance == null){
                    instance = AlbumManager()
                }
            }
        }
        this.fileDir = fileDir
        return instance!!
    }

    fun addPicture(){       //传入照片资源
        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日") // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        val location = "${fileDir}/album/${simpleDateFormat.format(date)}${System.currentTimeMillis()}.jpg"
//        Picture(,date,location) TODO()

    }

    fun addPictures(list: List<Any>){

    }
}