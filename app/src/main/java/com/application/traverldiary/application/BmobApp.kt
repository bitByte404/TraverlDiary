package com.application.traverldiary.application

import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob

class BmobApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance : BmobApp? = null

        fun getContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(this, "9e0b52c0abfe47ead1ac573edccb4989")
    }
}