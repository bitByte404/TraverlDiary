package com.application.traverldiary.manager

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AlbumManager private constructor() {

    companion object {
        private var instance: AlbumManager? = null
        private var fileDir = ""

        fun getInstance(fileDir:String):AlbumManager {
            if(instance == null) {
                synchronized(this){
                    if (instance == null){
                        instance = AlbumManager()
                    }
                }
            }
            this.fileDir = "$fileDir/album/"
            return instance!!
        }
    }

    fun addPicture(){       //传入照片资源
        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日") // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        val location = "${fileDir}${simpleDateFormat.format(date)}${System.currentTimeMillis()}.jpg"
//        Picture(,date,location) TODO()

    }

    fun addPictures(list: List<Any>){

    }

    fun loadPictures(context: Context): MutableList<Any> {
        val folderPath = fileDir
        val folder = File(folderPath)
        if (!folder.exists()){
            folder.mkdir()
        }

        if (!folder.isDirectory) {
            Log.v("wq","${folder}")
            throw IllegalArgumentException("Path must be a directory.")
        }

        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val strFormat = SimpleDateFormat("yyyy/MM/dd",Locale.getDefault())

        val files = folder.listFiles { file -> file.extension in listOf("jpg", "png", "jpeg") }
            ?.sortedWith(compareBy {
                val dateStr = it.nameWithoutExtension
                dateFormat.parse(dateStr)
            })
            ?: emptyList()

        val resultList = mutableListOf<Any>()
        var lastDate: String? = null

        for (file in files) {
            val dateStr = file.nameWithoutExtension.substring(0,8)
            if (dateStr != lastDate) {
                val date = dateFormat.parse(dateStr)
                val strDate = strFormat.format(date!!)
                resultList.add(strDate)
                lastDate = strDate
            }
            resultList.add(file)
        }
        return resultList
    }

    fun generateUniqueFileName(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HH时mm分ss秒_SSS", Locale.getDefault())
        val dateStr = dateFormat.format(Date())
        val randomNum = (Math.random() * 1000).toInt()
        return "${dateStr}_$randomNum.jpg"
    }

    @Throws(IOException::class)
    fun copyFile(src: File, dst: File) {
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

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
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

}