package com.application.traveldiary.manager


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