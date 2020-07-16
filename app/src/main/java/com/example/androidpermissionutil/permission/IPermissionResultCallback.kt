package com.example.androidpermissionutil.permission

/**
 * 权限申请的回调
 */
interface IPermissionResultCallback {
    /**
     * 同意了权限
     */
    fun granted(permission: String)

    /**
     * 拒绝了权限
     */
    fun cancel(permission: String)

    /**
     * 拒绝了权限且不再提示，碰到这种情况APP无法再唤起权限申请弹窗，需要跳转到APP权限设置页
     */
    fun denied(permission: String)

}