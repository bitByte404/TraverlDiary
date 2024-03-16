package com.application.traveldiary.manager

import android.R.attr.data
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.scale
import com.application.traveldiary.models.Picture
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.Date


class AlbumManager private constructor() {
    private val locationTimeManager = LocationTimeManager.getInstance()
    companion object {
        private var instance: AlbumManager? = null
        private lateinit var fileManager:FileManager
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
            fileManager = FileManager.getInstance(fileDir)
            return instance!!
        }
    }


    //添加照片
    fun addPicture(uri: Uri, context: Context){
        fileManager.savePicture(createPicture(uri,context))
    }


    //加载图片
    fun loadPictures(context: Context): List<Picture> {
        //判断文件夹是否存在
        fileManager.checkFileDir()

        //照片列表
        val list = fileManager.loadPicturesFromFile()
        if (list.isEmpty()) return mutableListOf()

        //整理为规范的列表 穿插入时间地点信息
        val resultList = standardizeList(list)

        //返回最终规范的list
        return list
    }


    //创建一个Picture类
    private fun createPicture(uri: Uri, context: Context):Picture{
        //获取ExifInterface以获取位置和拍摄时间信息
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val exifInterface = inputStream?.let { ExifInterface(it) }

        //照片拍摄时间
        val picDate = locationTimeManager.getPhotoTakeTime(exifInterface)
        //获取照片拍摄地点
        val picAddress = locationTimeManager.getPhotoLocationFromUri(context,uri,exifInterface)
        //获取保存在应用内部的照片Uri
        val mUri = fileManager.savePicFile(uri,picDate,context)
        //缩略图
        return Picture(mUri,picDate,picAddress)
    }

    //标准化list
    private  fun standardizeList(list:List<Picture>):MutableList<Any>{
        //穿插进去时间地点
        val resultList = mutableListOf<Any>()
        var lastDate = list[0].takeTime
        val tempArr = mutableListOf<Picture>()
        val locationSet = mutableSetOf<String>()
        var lastSb:String =""
        val sb = StringBuilder()
        list.forEach{ it ->
            if (it.takeTime != lastDate){
                Log.v("wq","${it.takeTime} ${sb}")
                resultList.add("${lastDate}${sb}")
                resultList.addAll(tempArr)
                lastDate = it.takeTime
                locationSet.clear()
                sb.clear()
                tempArr.clear()
            }
            if (it.location != "" && it.location != null){
                if(locationSet.add(",${it.location}")) {
                    sb.append(",${it.location}")
                }
            }
            tempArr.add(it)
        }
        if (tempArr.isNotEmpty()) {
            resultList.add("${tempArr[0].takeTime}${sb}")
            resultList.addAll(tempArr)
        }

        return resultList
    }


}