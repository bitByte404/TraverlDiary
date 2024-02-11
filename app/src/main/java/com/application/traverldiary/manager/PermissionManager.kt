package com.application.traverldiary.manager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


class PermissionManager private constructor(){
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    var READ_EXTERNAL_STORAGE_GET = false

    companion object {
        private var instance: PermissionManager? = null

        fun getInstance(): PermissionManager {
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





}