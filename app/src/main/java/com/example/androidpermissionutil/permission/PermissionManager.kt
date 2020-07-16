package com.example.androidpermissionutil.permission

import android.content.Context

/**
 * 权限管理类
 */
class PermissionManager {
    companion object {
        fun requestPermission(
            context: Context,
            permissions: Array<String?>?,
            iPermissionResultCallback: IPermissionResultCallback?
        ) {
            PermissionActivity.requestPermissionAction(context, permissions, iPermissionResultCallback)
        }
    }

}