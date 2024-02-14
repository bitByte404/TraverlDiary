package com.application.traveldiary.manager

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.application.traveldiary.adapter.UriTypeAdapter
import com.application.traveldiary.models.Picture
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class FileManager private constructor(){
    private var uriGson:Gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
        .create()
    companion object{
        private var instance: FileManager? = null
        private var fileDirHead = ""
        private var fileDirPic = ""

        //经典单例模式
        fun getInstance(fileDir:String): FileManager {
            if(instance == null) {
                synchronized(this){
                    if (instance == null){
                        instance = FileManager()
                    }
                }
            }
            Companion.fileDirHead = "$fileDir/albumHead"
            Companion.fileDirPic = "$fileDir/albumPic"
            return instance!!
        }
    }
    //检查所需的文件夹是否存在 然后创建
    fun checkFileDir(){
        checkFileDir(fileDirHead)
        checkFileDir(fileDirPic)
    }

    //保存Picture类
    fun savePicture(picture: Picture){
        val jsonString = uriGson.toJson(picture)
        FileOutputStream(File(fileDirHead,generateUniqueFileName(picture.takeTime))).bufferedWriter().use { out ->
            out.write(jsonString)
        }
    }

    //从文件夹加载Picture类
    fun loadPicturesFromFile(): List<Picture> {
        //用来比较日期
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        //所有的Picture
        val picArr = arrayListOf<Picture>()
        val fileDir: File = File(fileDirHead) // 文件夹
        fileDir.listFiles()?.forEach { file ->
            val picture = readPictureFromFile(file)
            picArr.add(picture)
        }
        //如果为空就过
        if (picArr.isEmpty()) return mutableListOf()

        //调整图片顺序
        val list = picArr.sortedByDescending {
            val date = dateFormat.parse(it.takeTime)
            date
        }
        return list
    }


    //保存照片文件
    fun savePicFile(uri: Uri, takeTime: String,context: Context):Uri {
        val src = getRealPathFromURI(context,uri)
        val dst = File(fileDirPic, "${generateUniqueFileName(takeTime)}.jpg")
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
        return dst.toUri()
    }

    //从文件读取单个的Picture类
    private fun readPictureFromFile(file: File): Picture {
        val jsonString = FileInputStream(file).bufferedReader().use { it.readText() }
        return uriGson.fromJson(jsonString, Picture::class.java)
    }

    //产生一个独立的文件名
    private fun generateUniqueFileName(takeTime:String): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val randomNum = (Math.random() * 1000).toInt()
        return "${dateFormat.parse(takeTime)}_$randomNum"
    }
    //检查并创建文件夹
    private fun checkFileDir(fileDir:String){
        val file = File(fileDir)
        if (!file.exists()) file.mkdir()
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
}