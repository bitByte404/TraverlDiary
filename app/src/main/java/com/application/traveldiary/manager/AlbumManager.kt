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
    private val fileManager = FileManager.getInstance(fileDir,)
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

        val picDate = getPhotoTakeTime(exifInterface)

        val latLong = FloatArray(2)
        val hasLatLong = exifInterface?.getLatLong(latLong)
        var picAddress:String = ""
//        if (hasLatLong == true) {
//            //创建一个新的Geocoder实例
//            val geocoder = Geocoder(context, Locale.getDefault())
//            // 创建一个新的Location实例
//            val location: Location = Location("") // 你的位置
//            location.latitude = latLong[0].toDouble()
//            location.latitude = latLong[1].toDouble()
//            // 获取地址列表
//            Log.v("wq","${location.latitude}  ${location.longitude}")
//            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//            // 获取第一个地址
//            val address = addresses?.get(0)
//            if (address != null) {
//                picAddress = address.getAddressLine(0)
//            }
//        }
        val mUri = fileManager.savePicFile(uri,picDate)

        return Picture(mUri,picDate,picAddress)
    }

    //获取照片位置
    fun getPhotoLocationFromUri(context: Context, uri: Uri,exifInterface: ExifInterface?) {
        val latitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
        val longitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
        if (latitude != null && longitude != null) {
            convertExifToDecimal(latitude)
            convertExifToDecimal(longitude)
        } else {
            // 照片没有位置信息，进行适当的处理
        }
    }
    //经纬度 转为标准的数字格式
    private fun convertExifToDecimal(exifCoord: String): Double {
        val split = exifCoord.split(",")

        val degrees = split[0].split("/").let { it[0].toDouble() / it[1].toDouble() }
        val minutes = split[1].split("/").let { it[0].toDouble() / it[1].toDouble() }
        val seconds = split[2].split("/").let { it[0].toDouble() / it[1].toDouble() }

        return degrees + minutes / 60.0 + seconds / 3600.0
    }

    //获取照片拍摄时间
    private fun getPhotoTakeTime(exifInterface: ExifInterface?):String{
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val dateTimeString = exifInterface?.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
        val format = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
        val takeDate = dateTimeString?.let { format.parse(it) }
        return if (takeDate==null){
                dateFormat.format(Date())
            }else{
                dateFormat.format(takeDate)
            }
    }

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