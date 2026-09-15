package com.example.cleveralarmclock.core.data.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.ai.edge.litert.Accelerator
import com.google.ai.edge.litert.CompiledModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TFLiteClassifier @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private var model: CompiledModel? = null
    private var labels: List<String> = emptyList()

    init {
        try {
            model = CompiledModel.create(
                context.assets,
                "model.tflite",
                CompiledModel.Options(Accelerator.CPU)
            )
            labels = FileUtil.loadLabels(context, "labels.txt")
            Log.i("ALARM_DEBUG", "Model and labels loaded!")
        } catch (e: Exception) {
            Log.e("ALARM_DEBUG", "Error: ${e.localizedMessage}")
        }
    }

    fun classify(bitmap: Bitmap): String {
        val currentModel = model ?: return "Model is not loaded"

        val width = bitmap.width
        val height = bitmap.height
        val squareSize = if (height > width) width else height

        // We don't use NormalizeOp because a Rescaling layer already exists in the model.
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(squareSize, squareSize))
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        val inputBuffers = currentModel.createInputBuffers()
        val outputBuffers = currentModel.createOutputBuffers()

        inputBuffers[0].writeFloat(tensorImage.tensorBuffer.floatArray)

        currentModel.run(inputBuffers, outputBuffers)

        val probabilities = outputBuffers[0].readFloat()

        Log.i("ALARM_DEBUG", "results")
        labels.forEachIndexed { index, label ->
            if (index < probabilities.size) {
                val percent = (probabilities[index] * 100).toInt()
                Log.i("ALARM_DEBUG", "$label: $percent%")
            }
        }

        Log.i("ALARM_DEBUG", "Labels: $labels")
        Log.i("ALARM_DEBUG", "Probability: ${probabilities.contentToString()}")

        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1
        Log.i("ALARM_DEBUG", "index $maxIndex")

        return if (maxIndex != -1 && maxIndex < labels.size) {
            labels[maxIndex]
        } else {
            "Unknown object"
        }
    }
}