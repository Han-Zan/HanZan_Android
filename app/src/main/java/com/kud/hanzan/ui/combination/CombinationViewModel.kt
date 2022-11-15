package com.kud.hanzan.ui.combination

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kud.hanzan.domain.model.DrinkDetail
import com.kud.hanzan.domain.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CombinationViewModel @Inject constructor(): ViewModel() {
    private var _drinkLiveData = MutableLiveData<DrinkDetail>()
    val drinkLiveData: LiveData<DrinkDetail>
        get() = _drinkLiveData

    private var _foodLiveData = MutableLiveData<Food>()
    val foodLiveData: LiveData<Food>
        get() = _foodLiveData

    fun setDrink(drink: DrinkDetail) {
        _drinkLiveData.value = drink
    }

    fun setFood(food: Food) {
        _foodLiveData.value = food
    }
}