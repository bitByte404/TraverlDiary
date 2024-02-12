package com.application.traveldiary.views.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.application.traveldiary.manager.PermissionManager

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.application.traveldiary.R

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var mPermissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestReadPermission()
        relateBottom()
    }


    private fun requestReadPermission() {
        if (Build.VERSION.SDK_INT in 23..31) {
            val perms = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            for (p in perms) {
                val f = ContextCompat.checkSelfPermission(this, p)
                if (f != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(perms, 0XCF)
                    break
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 32) {
            if (Environment.isExternalStorageManager()) {
                // 已经开启权限
            } else {
                // 未开启权限,弹窗申请权限
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        }

    }

    private fun relateBottom() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
    }
}