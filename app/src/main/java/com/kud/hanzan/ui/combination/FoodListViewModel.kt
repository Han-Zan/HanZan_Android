package com.kud.hanzan.ui.combination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: CombinationRepository
): ViewModel() {
    private var _foodLiveData = MutableLiveData<List<Food>>()
    val foodLiveData: LiveData<List<Food>>
        get() = _foodLiveData

    private var _listLiveData = MutableLiveData<List<Food>>()
    private val listLiveData: LiveData<List<Food>>
        get() = _listLiveData

    fun getAllFood(style: Int){
        viewModelScope.launch {
            val res = repository.getAllFood()
            _listLiveData.value = res
            chooseFoodType(style)
        }
    }

    fun chooseFoodType(style: Int) {
        _foodLiveData.value = listLiveData.value?.filter { it.foodType == style }
    }
}