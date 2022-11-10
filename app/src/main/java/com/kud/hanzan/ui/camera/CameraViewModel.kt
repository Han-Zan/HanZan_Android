package com.kud.hanzan.ui.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.usecase.camera.PostCameraDrinkUseCase
import com.kud.hanzan.domain.usecase.camera.PostCameraFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val postCameraDrinkUseCase: PostCameraDrinkUseCase,
    private val postCameraFoodUseCase: PostCameraFoodUseCase
): ViewModel() {
    private var _cameraDrinkData = MutableLiveData<List<String>>()
    val cameraDrinkData : LiveData<List<String>>
        get() = _cameraDrinkData

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
}