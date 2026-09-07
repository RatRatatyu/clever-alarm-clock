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
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TFLiteClassifier @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    var model: CompiledModel? = null
    var labels: List<String> = emptyList()

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
        val currentInterpreter = model ?: return "Model is not loaded"

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()


        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        val inputBuffers = currentInterpreter.createInputBuffers()
        val outputBuffers = currentInterpreter.createOutputBuffers()

        inputBuffers[0].writeFloat(tensorImage.tensorBuffer.floatArray)

        currentInterpreter.run(inputBuffers, outputBuffers)

        val probabilities = outputBuffers[0].readFloat()
        Log.i("ALARM_DEBUG", "$labels")
        Log.i("ALARM_DEBUG", probabilities.contentToString())
        val maxIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1

        return if (maxIndex != -1 && maxIndex < labels.size) {
            labels[maxIndex]
        } else {
            "Unknown object"
        }
    }
}