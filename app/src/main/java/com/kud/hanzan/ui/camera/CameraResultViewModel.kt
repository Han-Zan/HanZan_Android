package com.kud.hanzan.ui.camera

import androidx.databinding.ObservableField
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

    var isDrinkMode = true
    var isEmptyDrinkList : ObservableField<Boolean> = ObservableField<Boolean>()
    var isEmptyFoodList : ObservableField<Boolean> = ObservableField<Boolean>()

    // progress 리턴
    var progress : ObservableField<Int> = ObservableField<Int>()

    private fun setProgress() {
        isEmptyDrinkList.set(_drinkLiveData.value?.isEmpty())
        isEmptyFoodList.set(_foodLiveData.value?.isEmpty())
        if (isEmptyDrinkList.get() == true && isEmptyFoodList.get() == true) {
            progress.set(0)
        } else if (isEmptyDrinkList.get() == false && isEmptyFoodList.get() == false) {
            progress.set(100)
        } else progress.set(50)
    }

    fun deleteDrinkItem(position: Int){
        _drinkLiveData.value?.removeAt(position)
        setProgress()
    }

    fun deleteFoodItem(position: Int){
        _foodLiveData.value?.removeAt(position)
        setProgress()
    }

    // set drinkLiveData
    fun setDrinkData(array: Array<String>){
        _drinkLiveData.value = array.distinct().toMutableList()
        setProgress()
    }

    fun addDrinkData(drinkName: String) {
        _drinkLiveData.value?.add(drinkName)
        setProgress()
    }

    // set foodLiveData
    fun setFoodData(array: Array<String>){
        _foodLiveData.value = array.distinct().toMutableList()
        setProgress()
    }

    fun addFoodData(foodName: String) {
        _foodLiveData.value?.add(foodName)
        setProgress()
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