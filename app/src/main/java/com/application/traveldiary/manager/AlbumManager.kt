package com.application.traveldiary.manager

import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.location.Location
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import com.application.traveldiary.models.Picture
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AlbumManager private constructor() {
    companion object {
        private var instance: AlbumManager? = null
        //保存的照片路径
        private var fileDir = ""

        fun getInstance(fileDir:String): AlbumManager {
            if(instance == null) {
                synchronized(this){
                    if (instance == null){
                        instance = AlbumManager()
                    }
                }
            }
            Companion.fileDir = "$fileDir/album/"
            return instance!!
        }
    }

    fun addPicture(uri: Uri, context: Context){
        savePicture(createPicture(uri,context))
    }


    //加载图片
    fun loadPictures(context: Context): MutableList<Any> {
        //判断文件夹是否存在
        val folder = File(fileDir)
        if (!folder.exists()) {
            folder.mkdirs()
            return mutableListOf()
        }
        if (!folder.isDirectory) {
            throw IllegalArgumentException("Path must be a directory.")
        }

        //用来比较日期
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        val picArr = arrayListOf<Picture>()
        val fileDir: File = File(fileDir) // 你的文件夹
        fileDir.listFiles()?.forEach { file ->
            val picture = readPictureFromFile(file)
            // 使用picture...
            picArr.add(picture)
        }
        picArr.sortedByDescending {
            val date = dateFormat.parse(it.takeTime)
            date
        }
        //穿插进去时间地点
        val resultList = mutableListOf<Any>()
        var lastDate = picArr[0].takeTime
        val tempArr = mutableListOf<Picture>()
        val locationStr = StringBuilder()
        picArr.forEach{
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
        }


        return resultList
    }



    private fun createPicture(uri: Uri, context: Context):Picture{
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val exifInterface = inputStream?.let { ExifInterface(it) }

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val dateTimeString = exifInterface?.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
        val format = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
        val takeDate = dateTimeString?.let { format.parse(it) }
        val picDate =
            if (takeDate==null){
                dateFormat.format(Date())
            }else{
                dateFormat.format(takeDate)
            }

        val latLong = FloatArray(2)
        val hasLatLong = exifInterface?.getLatLong(latLong)
        var picAddress:String = ""
        if (hasLatLong == true) {
            //创建一个新的Geocoder实例
            val geocoder = Geocoder(context, Locale.getDefault())
            // 创建一个新的Location实例
            val location: Location = Location("") // 你的位置
            location.latitude = latLong[0].toDouble()
            location.latitude = latLong[1].toDouble()
            // 获取地址列表
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            // 获取第一个地址
            val address = addresses?.get(0)
            if (address != null) {
                picAddress = address.getAddressLine(0)
            }
        }
        return Picture(uri,picDate,picAddress)
    }

    private fun savePicture(picture: Picture){
        val jsonString = Gson().toJson(picture)
        FileOutputStream(File(fileDir,generateUniqueFileName(picture))).bufferedWriter().use { out ->
            out.write(jsonString)
        }
    }

    private fun readPictureFromFile(file: File): Picture {
        val gson = Gson()
        val jsonString = FileInputStream(file).bufferedReader().use { it.readText() }
        return gson.fromJson(jsonString, Picture::class.java)
    }

    //产生一个独立的文件名
    private fun generateUniqueFileName(picture: Picture): String {
        val randomNum = (Math.random() * 1000).toInt()
        return "${picture.takeTime}_$randomNum.jpg"
    }






//    //从Uri得到文件路径
//    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
//        var cursor: Cursor? = null
//        try {
//            val proj = arrayOf(MediaStore.Images.Media.DATA)
//            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
//            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            cursor?.moveToFirst()
//            return cursor?.getString(column_index ?: 0) ?: ""
//        } finally {
//            cursor?.close()
//        }
//    }
//
    //复制照片文件
//    private fun copyFile(src: File) {
////        val dst = File(fileDir, generateUniqueFileName())
//        val inStream = FileInputStream(src)
//        val outStream = FileOutputStream(dst)
//        val bufferedInStream = BufferedInputStream(inStream)
//        val bufferedOutStream = BufferedOutputStream(outStream)
//        val buf = ByteArray(1024)
//        var len: Int
//        while (bufferedInStream.read(buf).also { len = it } > 0) {
//            bufferedOutStream.write(buf, 0, len)
//        }
//        bufferedInStream.close()
//        bufferedOutStream.close()
//        inStream.close()
//        outStream.close()
//    }
}