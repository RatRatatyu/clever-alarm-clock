package com.example.cleveralarmclock

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import com.example.cleveralarmclock.core.service.permission.PermissionHandler
import com.example.cleveralarmclock.core.service.permission.PermissionTypes
import com.example.cleveralarmclock.presentation.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ){isGranted ->
                if(isGranted){

                }else{

                }

            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val hasPermission = permissionHandler.hasPermission(Manifest.permission.POST_NOTIFICATIONS)

                    if (hasPermission == PermissionTypes.DENIED) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            AppNavHost()
        }
    }
}
