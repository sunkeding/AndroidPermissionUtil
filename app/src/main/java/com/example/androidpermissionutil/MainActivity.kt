package com.example.androidpermissionutil

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.androidpermissionutil.permission.IPermissionResultCallback
import com.example.androidpermissionutil.permission.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
    }

    private fun initEvent() {
        btn_camera.setOnClickListener {
            PermissionManager.requestPermission(this, arrayOf(Manifest.permission.CAMERA),
                object : IPermissionResultCallback {
                    override fun granted(permission:String) {
                        Log.d(TAG, permission+"权限申请成功")
                    }

                    override fun cancel(permission:String) {
                        Log.d(TAG, permission+"权限被拒绝")
                    }

                    override fun denied(permission:String) {
                        Log.d(TAG, permission+"权限被拒绝且不再提示")
                    }

                })
        }
        btn_multi.setOnClickListener {
            PermissionManager.requestPermission(this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                object : IPermissionResultCallback {
                    override fun granted(permission:String) {
                        Log.d(TAG, permission+"权限申请成功")
                    }

                    override fun cancel(permission:String) {
                        Log.d(TAG, permission+"权限被拒绝")
                    }

                    override fun denied(permission:String) {
                        Log.d(TAG, permission+"权限被拒绝且不再提示")
                    }

                })

        }
    }
}