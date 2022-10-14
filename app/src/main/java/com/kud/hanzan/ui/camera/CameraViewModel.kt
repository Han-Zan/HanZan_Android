package com.kud.hanzan.ui.camera

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
): ViewModel() {
    private var _alcoholLiveData = MutableLiveData<List<String>>()
    val alcoholLiveData : LiveData<List<String>>
        get() = _alcoholLiveData

    private var _foodLiveData = MutableLiveData<List<String>>()
    val foodLiveData : LiveData<List<String>>
        get() = _foodLiveData

    init {
        _alcoholLiveData.value = listOf("고든", "참이슬", "샤르도네", "소비뇽 블랑")
        _foodLiveData.value = listOf("닭발", "치즈", "치킨", "파전", "초콜릿")
    }


    fun getItemNull() : Boolean = alcoholLiveData.value.isNullOrEmpty() || foodLiveData.value.isNullOrEmpty()

}