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
    fun loadPictures(context: Context): MutableList<Any> {
        //判断文件夹是否存在
        fileManager.checkFileDir()

        //照片列表
        val list = fileManager.loadPicturesFromFile()
        if (list.isEmpty()) return mutableListOf()

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
        val mUri = fileManager.savePicFile(uri,picDate,context)
        //缩略图
        val bitmap = getBitmapFormUri(context.contentResolver,mUri)
        return Picture(bitmap!!,mUri,picDate,picAddress)
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

    private fun getBitmapFormUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        return bitmap.scale(150,150)
//        return bitmap?.let { compressImage(it) }
    }

    private fun compressImage(image: Bitmap): Bitmap {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var options = 100
        while (baos.toByteArray().size / 1024 > 100 && options > 1) {
            baos.reset()
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            options -= 11
        }
        val isBm = ByteArrayInputStream(baos.toByteArray())
        return BitmapFactory.decodeStream(isBm, null, null)!!
    }

}