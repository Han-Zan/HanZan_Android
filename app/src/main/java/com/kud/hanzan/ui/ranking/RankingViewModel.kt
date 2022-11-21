package com.kud.hanzan.ui.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.CombinationDto
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.Food
import com.kud.hanzan.domain.model.TempPreferedCombDto
import com.kud.hanzan.domain.repository.RankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: RankingRepository
): ViewModel() {
    private var _combLiveData = MutableLiveData<CombinationInfo>()
    val combLiveData: LiveData<CombinationInfo>
        get() = _combLiveData

    private var _resLiveData = MutableLiveData<String>()
    val resLiveData: LiveData<String>
        get() = _resLiveData

    private var _totalLiveData = MutableLiveData<List<CombinationInfo>>()
    val totalLiveData: LiveData<List<CombinationInfo>>
        get() = _totalLiveData

    private var _listLiveData = MutableLiveData<List<CombinationInfo>>()
    val listLiveData: LiveData<List<CombinationInfo>>
        get() = _listLiveData

    private var _postPrefLiveData = MutableLiveData<String>()
    val postPrefLiveData: LiveData<String>
        get() = _postPrefLiveData

    private var _deletePrefLiveData = MutableLiveData<String>()
    val deletePrefLiveData: LiveData<String>
        get() = _deletePrefLiveData

    fun getCombination(userId: Long, combId: Long) {
        viewModelScope.launch {
            val res = repository.getCombination(userId, combId)
            _combLiveData.value = res
        }
    }
    fun saveCombination(combinationDto: CombinationDto) {

    }
    fun listAll(userId: Long) {
        viewModelScope.launch {
            val res = repository.listAll(userId)
            _totalLiveData.value = res
        }
    }

    private var drinkStyle = 0
    fun chooseDrinkType(style: Int) {
        drinkStyle = style
        if (style == 0) {
            if (foodStyle == -1) _listLiveData.value = totalLiveData.value
            else _listLiveData.value = totalLiveData.value?.filter { it.foodCategory == foodStyle }
        } else {
            if (foodStyle == -1) _listLiveData.value = totalLiveData.value?.filter { it.drinkCategory == drinkStyle }
            else _listLiveData.value = totalLiveData.value?.filter { (it.drinkCategory == drinkStyle) && (it.foodCategory == foodStyle) }
        }
    }

    private var foodStyle = -1
    fun chooseFoodType(style: Int) {
        foodStyle = style - 1
        if (style == 0) {
            if (drinkStyle == 0) _listLiveData.value = totalLiveData.value
            else _listLiveData.value = totalLiveData.value?.filter { it.drinkCategory == drinkStyle }
        } else {
            if (drinkStyle == 0) _listLiveData.value = totalLiveData.value?.filter { it.foodCategory == foodStyle }
            else _listLiveData.value = totalLiveData.value?.filter { (it.drinkCategory == drinkStyle) && (it.foodCategory == foodStyle) }
        }
    }

    fun postCombLike(userId: Long, combIdx: Long){
        viewModelScope.launch {
            val res = repository.postPreferredComb(TempPreferedCombDto(combIdx, userId))
            _postPrefLiveData.value = res
        }
    }

    fun deleteCombLike(userId: Long, combIdx: Long){
        viewModelScope.launch {
            val res = repository.deletePreferredComb(combIdx, userId)
            _deletePrefLiveData.value = res
        }
    }
}