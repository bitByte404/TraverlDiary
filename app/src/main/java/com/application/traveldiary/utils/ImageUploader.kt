package com.application.traveldiary.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.application.traveldiary.application.BmobApp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageUploader {

    // 返回数据对象
    class ResponseImageData(val name: String, val url: String)

    // 上传图片成功 事件回调
    private var mSuccessCallback: ((String) -> Unit)? = null

    // 上传图片失败 事件回调
    private var mFailureCallback: () -> Unit = {}

    // 上传图片开始 事件回调
    private var onStartCallBack: () -> Unit = {}

    // 上传图片结束 事件回调
    private var onEndCallback: () -> Unit = {}

    // 设置成功回调事件
    fun setSuccessCallback(callback: (successUri: String) -> Unit) {
        mSuccessCallback = callback
    }

    //设置失败回调事件
    fun setFailureCallback(callback: () -> Unit) {
        mFailureCallback = callback
    }

    fun setOnStartCallback(callback: () -> Unit) {
        onStartCallBack = callback
    }

    fun setOnEndCallback(callback: () -> Unit) {
        onEndCallback = callback
    }


    // 通过Uri对象上传
    suspend fun uploadByUri(uri: Uri) {
        withContext(Dispatchers.IO) {
            val path = getFilePathFromUri(BmobApp.getContext(), uri)
            if (path == null) {
                mFailureCallback()
            } else {
                uploadByPath(path)
            }
        }
    }


    // 通过文件路径上传
    suspend fun uploadByPath(filePath: String) {
        try {
            withContext(Dispatchers.Main) {
                onStartCallBack()
            }
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                // 获取Token
                val formBody = FormBody.Builder()
                    .add("email", "bitebyte@qq.com")
                    .add("password", "Iwxgeeqs4P")
                    .build()

                val requestToken = Request.Builder()
                    .url("https://www.imgtp.com/api/token")
                    .post(formBody)
                    .build()

                client.newCall(requestToken).execute().use { response ->
                    val token = response.body?.string() // 解析获取到的Token

                    // 上传图片
                    val requestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "test.png",
                            File(filePath).asRequestBody("image/png".toMediaTypeOrNull())
                        )
                        .build()

                    val requestUpload = Request.Builder()
                        .header("Authorization", "Bearer $token") // 设置Token
                        .url("https://www.imgtp.com/api/upload")
                        .post(requestBody)
                        .build()

                    client.newCall(requestUpload).execute().use { response ->
                        if (!response.isSuccessful) {
                            mFailureCallback()
                        }
                        val gson = Gson()
                        val responseData = gson.fromJson(response.body?.string(), ResponseImageData::class.java)
                        mSuccessCallback?.let { it(responseData.url) }
                    }
                }
            }
            onEndCallback()
        } catch (e: Exception) {
            mFailureCallback()
        }

}


    // 从Uri对象获取路径
    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val cursor = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
            cursor.use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        val fileName = cursor.getString(index)
                        val cacheDir = context.cacheDir
                        val file = File(cacheDir, fileName)
                        try {
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val outputStream = FileOutputStream(file)
                            inputStream?.copyTo(outputStream)
                            inputStream?.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return file.absolutePath
                    }
                }
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }
}