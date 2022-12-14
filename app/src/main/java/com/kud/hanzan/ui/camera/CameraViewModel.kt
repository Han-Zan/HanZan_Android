package com.kud.hanzan.ui.camera

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import com.kud.hanzan.domain.usecase.camera.PostCameraDrinkUseCase
import com.kud.hanzan.domain.usecase.camera.PostCameraFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val postCameraDrinkUseCase: PostCameraDrinkUseCase,
    private val postCameraFoodUseCase: PostCameraFoodUseCase
): ViewModel() {
    private var _cameraDrinkData = MutableLiveData<List<String>>()
    val cameraDrinkData : LiveData<List<String>>
        get() = _cameraDrinkData

    private var _cameraFoodData = MutableLiveData<List<String>>()
    val cameraFoodData : LiveData<List<String>>
        get() = _cameraFoodData

    fun postCameraDrink(strList: List<String>){
        viewModelScope.launch {
            postCameraDrinkUseCase(strList)
                .catch { _cameraDrinkData.value = emptyList() }
                .collectLatest{
                    _cameraDrinkData.value = it
                    Log.e("cameraViewModel", it.toString())
                }
        }
    }

    fun postCameraFood(strList: List<String>){
        viewModelScope.launch {
            postCameraFoodUseCase(strList)
                .catch { _cameraDrinkData.value = emptyList() }
                .collectLatest{
                    _cameraFoodData.value = it
                    Log.e("cameraViewModel", it.toString())
                }
        }
    }
}