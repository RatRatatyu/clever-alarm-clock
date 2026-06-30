package com.example.cleveralarmclock.core.service.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//Camera Notification

class PermissionHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    ){

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}