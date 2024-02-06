package com.application.traverldiary.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class PermissionManager private constructor(){
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    var READ_EXTERNAL_STORAGE_GET = false

    companion object {
        private var instance: PermissionManager? = null

        fun getInstance():PermissionManager {
            if(instance == null) {
                synchronized(this){
                    if (instance == null){
                        instance = PermissionManager()
                    }
                }
            }
            return instance!!
        }
    }



    fun checkAndRequestPermissions(context: Activity):Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            Log.v("wq","申请权限")
            // 权限还没有授予，需要在这里写申请权限的代码
            requestPermissions(context,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)

        } else {
            READ_EXTERNAL_STORAGE_GET = true
        }
        Log.v("wq","${READ_EXTERNAL_STORAGE_GET}")
        return READ_EXTERNAL_STORAGE_GET
    }

}