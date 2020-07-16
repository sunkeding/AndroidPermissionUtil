package com.example.androidpermissionutil.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log

/**
 * 权限申请的中转透明Activity封装，支持同时申请一个和多个动态权限
 */
class PermissionActivity : Activity() {
    var permissions: Array<String>? = null
    val TAG = "PermissionActivity"

    companion object {
        var permissionResultCallbackListener: IPermissionResultCallback? = null
        val requestCode = 100
        private const val PARAM_PREMISSION = "param_permission"
        fun requestPermissionAction(
            context: Context, permissions: Array<String?>?,
            iPermissionResultCallback: IPermissionResultCallback?
        ) {
            permissionResultCallbackListener = iPermissionResultCallback
            val intent =
                Intent(context, PermissionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            val bundle = Bundle()
            bundle.putStringArray(
                PARAM_PREMISSION,
                permissions
            )
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseBundle()
        if (permissions == null || permissionResultCallbackListener == null) {
            finish()
            return
        }
        Log.d(TAG, "permissions:" + permissions?.size)
        ActivityCompat.requestPermissions(
            this,
            permissions!!,
            requestCode
        )

    }

    private fun parseBundle() {
        permissions = intent?.extras?.getStringArray(PARAM_PREMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEachIndexed { index, _ ->
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                permissionResultCallbackListener?.granted(permissions[index])
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permissions[index]
                    )
                ) {
                    permissionResultCallbackListener?.denied(permissions[index])
                } else {
                    permissionResultCallbackListener?.cancel(permissions[index])
                }
            }
        }
        finish()
    }

    // 当前Activity结束的时候，不需要有动画效果
    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }


}