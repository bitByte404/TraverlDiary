package com.application.traverldiary

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.application.traverldiary.R
import com.application.traverldiary.manager.PermissionManager


class MainActivity : AppCompatActivity() {
    lateinit var mPermissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPermissionManager = PermissionManager.getInstance()


        requestReadPermission()

    }


    fun requestReadPermission() {
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show()

            // 申请 相机 麦克风权限
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 100
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            mPermissionManager.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // 如果请求被取消，那么结果数组为空
                mPermissionManager.READ_EXTERNAL_STORAGE_GET = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                return
            }
        }
    }

}