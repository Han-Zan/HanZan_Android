package com.kud.hanzan.ui.like

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.HanZanApplication.Companion.spfManager
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.usecase.preferred.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val getCombUseCase : GetPreferredCombUseCase,
    private val deleteCombUseCase: DeletePreferredCombUseCase,
    private val postCombUseCase: PostPreferredCombUseCase,
    private val getDrinkUseCase: GetPreferredDrinkUseCase,
    private val deleteDrinkUseCase: DeletePreferredDrinkUseCase,
    private val postDrinkUseCase: PostPreferredDrinkUseCase
) : ViewModel() {
    private var _alcoholData = MutableStateFlow<List<Drink>>(emptyList())
    val alcoholData : StateFlow<List<Drink>>
        get() = _alcoholData
    private var totalAlcoholData = listOf<Drink>()

    private var _combData = MutableStateFlow<List<Combination>>(emptyList())
    val combData : StateFlow<List<Combination>>
        get() = _combData
    private var totalCombData = listOf<Combination>()

    private var searchKeyword: String? = null
    private var type = 0

    // Comb Drink Type
    private var combDrinkType = 0
    // 안주는 -1이 전체 선택
    private var combFoodType = -1

    private val userIdx = spfManager.getUserIdx()

    init {
        getComb(userIdx)
        getAlcohol(userIdx)
    }

    private fun getComb(userId: Long){
        viewModelScope.launch {
            getCombUseCase(userId)
                .catch {
                }
                .collect{
                    _combData.value = it
                    totalCombData = it
                }
        }
    }

    private fun getAlcohol(userId: Long){
        viewModelScope.launch {
            getDrinkUseCase(userId)
                .catch { _alcoholData.value = emptyList() }
                .collect{
                    _alcoholData.value = it
                    totalAlcoholData = it
                }
        }
    }

    fun deleteComb(combId: Long){
        viewModelScope.launch {
            deleteCombUseCase(userIdx, combId)
                .catch {  }
                .collect ()
        }
    }

    fun deleteDrink(drinkId: Long){
        viewModelScope.launch {
            deleteDrinkUseCase(userIdx, drinkId)
                .catch {  }
                .collect()
        }
    }

    fun postComb(combId: Long){
        viewModelScope.launch {
            postCombUseCase(userIdx, combId)
                .catch {  }
                .collect()
        }
    }

    fun postDrink(drinkId: Long){
        viewModelScope.launch {
            postDrinkUseCase(userIdx, drinkId)
                .catch {  }
                .collect()
        }
    }


    fun setTypeAlcohol(type: Int){
        this.type = type
        if (type == 0){
            if (searchKeyword == null)
                _alcoholData.value = totalAlcoholData
            else searchKeyword?.let {
                _alcoholData.value = totalAlcoholData.filter { a ->
                    a.name.contains(it)
                }
            }
        }
        else {
            if (searchKeyword == null)
                _alcoholData.value = totalAlcoholData.filter { alcohol ->  alcohol.category == type}
            else searchKeyword?.let {
                _alcoholData.value = totalAlcoholData.filter {
                    alcohol -> alcohol.category == type && alcohol.name.contains(it)
                }
            }
        }
    }

    fun setAlcoholSort(sort: Boolean){
        totalAlcoholData = if (sort)
            totalAlcoholData.sortedBy { likeAlcohol -> -likeAlcohol.id}
        else
            totalAlcoholData.sortedBy { likeAlcohol -> likeAlcohol.name}

        if (type == 0)
            _alcoholData.value = totalAlcoholData
        else
            _alcoholData.value = totalAlcoholData.filter { likeAlcohol -> likeAlcohol.category == type }
    }

    fun setCombDrinkType(drinkType: Int) {
        if (drinkType == 0){
            if (combFoodType != -1){
                searchKeyword?.let {  keyword ->
                    _combData.value = totalCombData.filter { (combFoodType == it.foodCategory) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData.filter { combFoodType == it.foodCategory }
                }
            }
            else{
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData
                }
            }
        } else {
            if (combFoodType != -1){
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (combFoodType == it.foodCategory) && (it.drinkCategory == drinkType) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData.filter { (combFoodType == it.foodCategory) && (it.drinkCategory == drinkType) }
                }
            }
            else{
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (it.drinkCategory == drinkType) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData.filter { it.drinkCategory == drinkType }
                }
            }
        }
        combDrinkType = drinkType
    }

    fun setCombFoodType(foodType: Int){
        if (foodType == -1){
            if (combDrinkType == 0){
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { it.foodname.contains(keyword) || it.drinkname.contains(keyword) }
                } ?: run {
                    _combData.value = totalCombData
                }
            }
            else {
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (combDrinkType == it.drinkCategory) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData.filter { combDrinkType == it.drinkCategory }
                }
            }
        } else {
            if (combDrinkType == 0){
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (it.foodCategory == foodType) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword))}
                } ?: run {
                    _combData.value = totalCombData.filter { it.foodCategory == foodType }
                }
            }
            else {
                searchKeyword?.let { keyword ->
                    _combData.value = totalCombData.filter { (combDrinkType == it.drinkCategory) && (foodType == it.foodCategory) && (it.foodname.contains(keyword) || it.drinkname.contains(keyword)) }
                } ?: run {
                    _combData.value = totalCombData.filter { (combDrinkType == it.drinkCategory) && (foodType == it.foodCategory) }
                }
            }
        }
        combFoodType = foodType
    }

    fun search(keyword: String){
        searchKeyword = keyword
        _alcoholData.value = totalAlcoholData.filter { alcohol -> alcohol.name.contains(keyword) }
        _combData.value = totalCombData.filter { comb -> comb.drinkname.contains(keyword) || comb.foodname.contains(keyword) }
    }

    fun searchClose(){
        searchKeyword = null
        _alcoholData.value = if (type == 0) totalAlcoholData else totalAlcoholData.filter { alcohol -> alcohol.category == type }
        _combData.value = if (combDrinkType == 0) {
            if (combFoodType == -1) totalCombData
            else totalCombData.filter { comb -> comb.foodCategory == combFoodType }
        } else {
            if (combFoodType == -1) totalCombData.filter { comb -> comb.drinkCategory == combDrinkType }
            else totalCombData.filter { comb -> (comb.drinkCategory == combDrinkType) && (comb.foodCategory == combFoodType) }
        }
    }
}