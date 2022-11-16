package com.kud.hanzan.ui.combination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Comb
import com.kud.hanzan.domain.model.DrinkDetail
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombinationViewModel @Inject constructor(
    private val repository: CombinationRepository
): ViewModel() {
    private var _drinkLiveData = MutableLiveData<DrinkDetail>()
    val drinkLiveData: LiveData<DrinkDetail>
        get() = _drinkLiveData

    private var _foodLiveData = MutableLiveData<Food>()
    val foodLiveData: LiveData<Food>
        get() = _foodLiveData

    private var _combLiveData = MutableLiveData<Comb>()
    val combLiveData: LiveData<Comb>
        get() = _combLiveData

    fun setDrink(drink: DrinkDetail) {
        _drinkLiveData.value = drink
    }

    fun setFood(food: Food) {
        _foodLiveData.value = food
    }

    fun recommendation(drink: DrinkDetail, food: Food, userId: Long){
        viewModelScope.launch {
            val res = repository.recommandation(drink.name, food.name, userId)
            _combLiveData.value = res
        }
    }
}