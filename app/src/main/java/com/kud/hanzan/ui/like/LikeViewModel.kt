package com.kud.hanzan.ui.like

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

    private val userIdx = spfManager.getUserIdx()

    init {
        getComb(userIdx)
        getAlcohol(userIdx)
    }

    private fun getComb(userId: Long){
        viewModelScope.launch {
            getCombUseCase(userId)
                .catch { _combData.value = listOf(
                    Combination("일반소주", null, "닭발", null,   0, 0f, "누구도 부정할 수 없는\n궁극의 그 조합!"),
                    Combination("카스", null, "치킨", null, 0, 0f, "치맥 안 먹어본 사람 있어?")
                ).also {
                    totalCombData = it
                } }
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

    fun search(keyword: String){
        searchKeyword = keyword
        _alcoholData.value = totalAlcoholData.filter { alcohol -> alcohol.name.contains(keyword) }
        _combData.value = totalCombData.filter { comb -> comb.drinkname.contains(keyword) }
    }

    fun searchClose(){
        searchKeyword = null
        _alcoholData.value = if (type == 0) totalAlcoholData else totalAlcoholData.filter { alcohol -> alcohol.category == type }
        _combData.value = totalCombData
    }
}