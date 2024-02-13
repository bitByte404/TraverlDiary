package com.application.traveldiary.manager

import android.net.Uri
import android.util.Log
import com.application.traveldiary.adapter.UriTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class FileManager {
    companion object{
        fun getUriGson(): Gson {
            val gson = GsonBuilder()
                .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
                .create()
            Log.v("wq","get")
            return gson
        }
    }
}