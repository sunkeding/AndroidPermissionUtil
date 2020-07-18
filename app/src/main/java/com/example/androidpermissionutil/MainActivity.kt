package com.example.androidpermissionutil

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.util.Log
import com.example.androidpermissionutil.permission.IPermissionResultCallback
import com.example.androidpermissionutil.permission.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
//        testImei()
    }

    private fun testImei() {
        // android10拿不到deviceId，一般会用UUID代替，
        val telephonyManager: TelephonyManager =
            this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var imei = telephonyManager.getDeviceId() //获取imei的方法修改
        Log.d(TAG,"imei:"+imei)
        Log.d(TAG,"UUID:"+ UUID.randomUUID().toString())
        // Android10拿不到序列号了
        Log.d(TAG,"Build.getSerial():"+ Build.getSerial())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun initEvent() {
        btn_camera.setOnClickListener {
            PermissionManager.requestPermission(this, arrayOf(Manifest.permission.CAMERA),
                object : IPermissionResultCallback {
                    override fun granted(permission: String) {
                        Log.d(TAG, permission + "权限申请成功")
                        var intent =  Intent()
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);   //拍照界面的隐式意图
                        startActivityForResult(intent,200);

                    }

                    override fun cancel(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝")
                    }

                    override fun denied(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝且不再提示")
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
                    override fun granted(permission: String) {
                        Log.d(TAG, permission + "权限申请成功")
                    }

                    override fun cancel(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝")
                    }

                    override fun denied(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝且不再提示")
                    }

                })

        }
        btn_read_phone.setOnClickListener {
            val hasReadPhonePermission =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            Log.d(TAG, "hasReadPhonePermission:" + hasReadPhonePermission)
            PermissionManager.requestPermission(this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                object : IPermissionResultCallback {
                    override fun granted(permission: String) {
                        Log.d(TAG, permission + "权限申请成功")
                        testImei()
                    }

                    override fun cancel(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝")
                    }

                    override fun denied(permission: String) {
                        Log.d(TAG, permission + "权限被拒绝且不再提示")
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("提示")
                            .setMessage("需要去设置页设置权限")
                            .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    Log.d(TAG, permission + "确定")
                                    SettingPage.start(1000,this@MainActivity)
                                }

                            })
                            .setNegativeButton("取消", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    Log.d(TAG, permission + "取消")

                                }

                            })
                            .create()
                            .show()
                    }

                })
        }
    }
}