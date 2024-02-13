package com.application.traveldiary.manager

import android.content.Context
import android.database.Cursor
import android.location.Geocoder
import android.location.Location
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import com.application.traveldiary.models.Picture
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
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
        createPicture(uri,context)



    }

    private fun createPicture(uri: Uri, context: Context):Picture{
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val exifInterface = inputStream?.let { ExifInterface(it) }

        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateTimeString = exifInterface?.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
        val format = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
        val date = dateTimeString?.let { format.parse(it) }
        val picDate =
            if (date==null){
                dateFormat.format(Date())
            }else{
                dateFormat.format(date)
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

    //加载图片
    fun loadPictures(context: Context): MutableList<Any> {
        val folder = File(fileDir)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        if (!folder.isDirectory) {
            throw IllegalArgumentException("Path must be a directory.")
        }

        val fileDateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val stringDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        val files = folder.listFiles { file -> file.extension in listOf("jpg", "png", "jpeg") }
            ?.sortedWith(compareByDescending {
                val dateStr = it.nameWithoutExtension.substring(0..7)
                fileDateFormat.parse(dateStr)
            })
            ?: emptyList()

        val resultList = mutableListOf<Any>()
        var lastDate: String? = null

        for (file in files) {


            val dateStr = file.nameWithoutExtension.substring(0..7)
            if (dateStr != lastDate) {
                val date = fileDateFormat.parse(dateStr)
                resultList.add(stringDateFormat.format(date))
                lastDate = dateStr
            }
            resultList.add(file)
        }

        return resultList
    }



    //复制照片文件
    private fun copyFile(src: File) {
        val dst = File(fileDir, generateUniqueFileName())
        val inStream = FileInputStream(src)
        val outStream = FileOutputStream(dst)
        val bufferedInStream = BufferedInputStream(inStream)
        val bufferedOutStream = BufferedOutputStream(outStream)
        val buf = ByteArray(1024)
        var len: Int
        while (bufferedInStream.read(buf).also { len = it } > 0) {
            bufferedOutStream.write(buf, 0, len)
        }
        bufferedInStream.close()
        bufferedOutStream.close()
        inStream.close()
        outStream.close()
    }


    //从Uri得到文件路径
    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            return cursor?.getString(column_index ?: 0) ?: ""
        } finally {
            cursor?.close()
        }
    }

    //产生一个独立的文件名
    private fun generateUniqueFileName(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HH时mm分ss秒_SSS", Locale.getDefault())
        val dateStr = dateFormat.format(Date())
        val randomNum = (Math.random() * 1000).toInt()
        return "${dateStr}_$randomNum.jpg"
    }


}