package com.kud.hanzan.ui.combination

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.usecase.combination.GetDrinkListUseCase
import com.kud.hanzan.domain.usecase.preferred.DeletePreferredDrinkUseCase
import com.kud.hanzan.domain.usecase.preferred.PostPreferredDrinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinkListUseCase: GetDrinkListUseCase,
    private val postDrinkUseCase: PostPreferredDrinkUseCase,
    private val deleteDrinkUseCase: DeletePreferredDrinkUseCase
): ViewModel() {
    private var _drinkList = MutableStateFlow<List<Drink>>(emptyList())
    val drinkList : StateFlow<List<Drink>>
        get() = _drinkList

    var isLoading : ObservableField<Boolean> = ObservableField<Boolean>()

    private val userIdx = HanZanApplication.spfManager.getUserIdx()

    init {
        isLoading.set(true)
        getDrinkList(userIdx)
    }

    private var totalDrinkData = listOf<Drink>()

    private var category = 0
    private var keyword: String? = null

    private fun getDrinkList(userIdx: Long){
        viewModelScope.launch {
            getDrinkListUseCase(userIdx)
                .catch {
                    _drinkList.value = emptyList()
                    isLoading.set(false)
                }
                .collect{
                    _drinkList.value = it
                    totalDrinkData = it
                    isLoading.set(false)
                }
        }
    }

    fun deleteDrink(drinkId: Long){
        viewModelScope.launch {
            deleteDrinkUseCase(userIdx, drinkId)
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

    fun setDrinkListType(type: Int){
        category = type
        if (type == 0) {
            if (keyword == null)
                _drinkList.value = totalDrinkData
            else keyword?.let {
                _drinkList.value = totalDrinkData.filter { d -> d.name.contains(it) }
            }
        } else {
            if (keyword == null)
                _drinkList.value = totalDrinkData.filter { d -> d.category == type }
            else keyword?.let{
                _drinkList.value = totalDrinkData.filter { d ->
                    d.category == type && d.name.contains(it)
                }
            }
        }
    }

    fun search(keyword: String){
        this.keyword = keyword
        _drinkList.value = totalDrinkData.filter { drink -> drink.name.contains(keyword) }
    }

    fun searchClose(){
        keyword = null
        _drinkList.value = if (category == 0) totalDrinkData else totalDrinkData.filter { alcohol -> alcohol.category == category }
    }
}