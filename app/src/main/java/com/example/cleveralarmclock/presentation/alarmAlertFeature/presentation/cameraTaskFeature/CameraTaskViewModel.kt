package com.example.cleveralarmclock.presentation.alarmAlertFeature.presentation.cameraTaskFeature

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleveralarmclock.core.data.classifier.TFLiteClassifier
import com.example.cleveralarmclock.core.domain.usecase.ring.StopAlarmPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CameraTaskUiState(
    val lastTakenPhoto: Bitmap? = null,
    val isLoading: Boolean = false,
    val target: String = "",
)

@HiltViewModel
class CameraTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val stopAlarmUseCase: StopAlarmPlayerUseCase,
    private val classifier: TFLiteClassifier
): ViewModel() {

    private val alarmId: Int = checkNotNull(savedStateHandle["alarmId"])

    private val _uiState = MutableStateFlow(CameraTaskUiState())
    val uiState: StateFlow<CameraTaskUiState> = _uiState



    fun onTakePhoto(photo: Bitmap?){
        photo?.let {
            val result = classifier.classify(photo)
            _uiState.update { it.copy(
                lastTakenPhoto = photo,
                target = result // using to test the model result for now
            ) }
        }

        //TODO(sent photo to classification AI model)
    }

    fun onClearPhoto(){
        _uiState.update { it.copy(
            lastTakenPhoto = null
        ) }
    }
    fun stopMusic(){
        viewModelScope.launch {
            stopAlarmUseCase(alarmId)
        }
    }
}