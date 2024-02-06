package com.application.traverldiary.manager

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AlbumManager private constructor() {
    companion object {
        private var instance: AlbumManager? = null
        //保存的照片路径
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

    fun addPicture(src: Uri, context: Context){
        val sourceFile = File(getRealPathFromURI(context, src))
        copyFile(sourceFile)
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