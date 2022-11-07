package com.kud.hanzan.ui.combination

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

    init {
        isLoading.set(true)
        getDrinkList()
    }

    private var totalDrinkData = listOf<Drink>()

    private val userIdx = HanZanApplication.spfManager.getUserIdx()

    private fun getDrinkList(){
        viewModelScope.launch {
            getDrinkListUseCase()
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
        _drinkList.value = if (type == 0) totalDrinkData else totalDrinkData.filter { drink -> drink.category == type }
    }
}