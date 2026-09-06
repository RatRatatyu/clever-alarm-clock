package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.cleveralarmclock.R


@Composable
fun CameraViewComponent(
    modifier: Modifier = Modifier,
    latestPhoto: Bitmap?,
    onTakePhoto: (Bitmap?) -> Unit,
    onClearPhoto: () -> Unit,
    isLoading: Boolean
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isInspectionMode = LocalInspectionMode.current

    val cameraController = if (isInspectionMode) null else remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    LaunchedEffect(cameraController, lifecycleOwner) {
        cameraController?.bindToLifecycle(lifecycleOwner)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(all = 20.dp)
                .weight(3f)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            if (latestPhoto == null) {
                if (isInspectionMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Camera Preview Placeholder")
                    }
                } else {
                    AndroidView(
                        factory = { ctx ->
                            PreviewView(ctx).apply {
                                this.controller = cameraController
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Image(
                    bitmap = latestPhoto.asImageBitmap(),
                    contentDescription = stringResource(R.string.the_photo_you_just_took),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            if (isLoading){
                CircularProgressIndicator()
            }
        }

        Box(
            modifier = Modifier
                .padding(20.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (latestPhoto == null) {
                IconButtonHelper(
                    onClick = { cameraController?.let { takePhoto(it, context, onTakePhoto) } },
                    icon = R.drawable.outline_photo_camera,
                    contentDescription = stringResource(R.string.take_a_photo)
                )
            } else {
                IconButtonHelper(
                    onClick = onClearPhoto,
                    icon = R.drawable.outline_replay,
                    contentDescription = stringResource(R.string.retake_a_photo)
                )
            }
        }
    }
}

private fun takePhoto(
    cameraController: LifecycleCameraController,
    context: android.content.Context,
    onTakePhoto: (Bitmap?) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {

            override fun onCaptureSuccess(image: ImageProxy) {
                val bitmap = image.toCorrectlyRotatedBitmap()
                onTakePhoto(bitmap)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CAMERA", "Capture error", exception)
                onTakePhoto(null)
            }
        }
    )
}

@Composable
fun IconButtonHelper(
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.size(42.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}
fun ImageProxy.toCorrectlyRotatedBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)

    val originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    val matrix = Matrix().apply {
        postRotate(imageInfo.rotationDegrees.toFloat())
    }

    return Bitmap.createBitmap(
        originalBitmap,
        0,
        0,
        originalBitmap.width,
        originalBitmap.height,
        matrix,
        true
    )
}