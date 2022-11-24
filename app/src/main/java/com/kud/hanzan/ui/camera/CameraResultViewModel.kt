package com.kud.hanzan.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.repository.CameraRepository
import com.kud.hanzan.domain.usecase.camera.PostCameraDrinkUseCase
import com.kud.hanzan.domain.usecase.camera.PostCameraFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraResultViewModel @Inject constructor(
    private val repository: CameraRepository
): ViewModel() {
    private var _drinkLiveData = MutableLiveData<MutableList<String>>()
    val drinkLiveData : LiveData<MutableList<String>>
        get() = _drinkLiveData

    private var _foodLiveData = MutableLiveData<MutableList<String>>()
    val foodLiveData : LiveData<MutableList<String>>
        get() = _foodLiveData

    private var _drinkListLiveData = MutableLiveData<List<Drink>>()
    val drinkListLiveData : LiveData<List<Drink>>
        get() = _drinkListLiveData

    private var _foodListLiveData = MutableLiveData<List<Food>>()
    val foodListLiveData : LiveData<List<Food>>
        get() = _foodListLiveData

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

    // set drinkLiveData
    fun setDrinkData(array: Array<String>){
        _drinkLiveData.value = array.distinct().toMutableList()
    }

    // set foodLiveData
    fun setFoodData(array: Array<String>){
        _foodLiveData.value = array.distinct().toMutableList()
    }

    fun getAllDrinkList(userId: Long) {
        viewModelScope.launch {
            val res = repository.getAllDrinkList(userId)
            _drinkListLiveData.value = res
        }
    }

    fun getAllFoodList() {
        viewModelScope.launch {
            val res = repository.getAllFoodList()
            _foodListLiveData.value = res
        }
    }
}