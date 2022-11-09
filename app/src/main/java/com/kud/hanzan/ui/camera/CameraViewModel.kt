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
    private var _drinkLiveData = MutableLiveData<MutableList<String>>()
    val drinkLiveData : LiveData<MutableList<String>>
        get() = _drinkLiveData

    private var _foodLiveData = MutableLiveData<MutableList<String>>()
    val foodLiveData : LiveData<MutableList<String>>
        get() = _foodLiveData

    init {
        _drinkLiveData.value = mutableListOf("참이슬", "처음처럼", "진로", "아이셔에 이슬", "비타500에 이슬", "테라", "하이트", "카스")
        _foodLiveData.value = mutableListOf()
    }

    fun deleteDrinkItem(position: Int){
        _drinkLiveData.value?.removeAt(position)
    }

    fun deleteFoodItem(position: Int){
        _foodLiveData.value?.removeAt(position)
    }

    // progress 리턴
    fun getProgress() : Int = if (_drinkLiveData.value?.isNotEmpty() == true && _foodLiveData.value?.isNotEmpty() == true) 100
        else if (_drinkLiveData.value?.isEmpty() == true && _foodLiveData.value?.isEmpty() == true) 0
        else 50

}