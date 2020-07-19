package com.example.androidpermissionutil.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat

/**
 * 权限管理类
 */
class PermissionManager {
    companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
        fun requestPermission(
            context: Context,
            permissions: Array<String?>?,
            iPermissionResultCallback: IPermissionResultCallback?
        ) {
            if (Looper.myLooper() == Looper.getMainLooper()){
                requestPermissionInternal(context, permissions, iPermissionResultCallback)
            }else{
                mainHandler.post {
                    requestPermissionInternal(context, permissions, iPermissionResultCallback)
                }
            }
        }
        fun requestPermissionInternal(
            context: Context,
            permissions: Array<String?>?,
            iPermissionResultCallback: IPermissionResultCallback?
        ){
            PermissionActivity.requestPermissionAction(
                context,
                permissions,
                iPermissionResultCallback
            )
        }

        fun hasPermissions(
            context: Context,
            vararg perms: String
        ): Boolean {
            // Always return true for SDK < M, let the system deal with the permissions
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            for (perm in perms) {
                if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }
    }


}