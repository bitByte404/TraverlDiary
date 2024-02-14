package com.application.traveldiary.manager

import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import com.application.traveldiary.models.Picture
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AlbumManager private constructor() {
    private val fileManager = FileManager.getInstance(fileDir)
    private val locationTimeManager = LocationTimeManager.getInstance()
    companion object {
        private var instance: AlbumManager? = null
        //保存的照片路径
        private var fileDir = ""
        //经典单例模式
        fun getInstance(fileDir:String): AlbumManager {
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
    }


    //添加照片
    fun addPicture(uri: Uri, context: Context){
        fileManager.savePicture(createPicture(uri,context))
    }


    //加载图片
    fun loadPictures(context: Context): MutableList<Any> {
        //判断文件夹是否存在
        fileManager.checkFileDir()

        //照片列表
        val list = fileManager.loadPicturesFromFile()
        //整理为规范的列表 穿插入时间地点信息
        val resultList = standardizeList(list)

        //返回最终规范的list
        return resultList
    }


    //创建一个Picture类
    private fun createPicture(uri: Uri, context: Context):Picture{
        //获取ExifInterface以获取位置和拍摄时间信息
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val exifInterface = inputStream?.let { ExifInterface(it) }

        //照片拍摄时间
        val picDate = locationTimeManager.getPhotoTakeTime(exifInterface)
        //获取照片拍摄地点
        val picAddress = ""
        //获取保存在应用内部的照片Uri
        val mUri = fileManager.savePicFile(uri,picDate)

        return Picture(mUri,picDate,picAddress)
    }

    //标准化list
    private fun standardizeList(list:List<Picture>):MutableList<Any>{
        //穿插进去时间地点
        val resultList = mutableListOf<Any>()
        var lastDate = list[0].takeTime
        val tempArr = mutableListOf<Picture>()
        val locationStr = StringBuilder()
        list.forEach{
            Log.v("wq","${it.takeTime}  ${it.uri}")
            if (it.location != ""){
                locationStr.append(",${it.location}")
            }
            if (it.takeTime != lastDate){
                resultList.add("${lastDate}${locationStr}")
                resultList.addAll(tempArr)
                lastDate = it.takeTime
                locationStr.clear()
                tempArr.clear()
            }
            tempArr.add(it)
        }
        if (tempArr.isNotEmpty()) {
            resultList.add("${tempArr[0].takeTime}${locationStr}")
            resultList.addAll(tempArr)
        }
        return resultList
    }
}