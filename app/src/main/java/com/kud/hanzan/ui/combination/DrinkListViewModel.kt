package com.kud.hanzan.ui.combination

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.usecase.combination.GetDrinkListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinkListUseCase: GetDrinkListUseCase
): ViewModel() {
    private var _drinkList = MutableStateFlow<List<Drink>>(emptyList())
    val drinkList : StateFlow<List<Drink>>
        get() = _drinkList

    var isLoading : ObservableField<Boolean> = ObservableField<Boolean>()

    init {
        isLoading.set(true)
        getDrinkList()
    }

    private fun getDrinkList(){
        viewModelScope.launch {
            getDrinkListUseCase()
                .catch {
                    _drinkList.value = emptyList()
                    isLoading.set(false)
                }
                .collect{
                    _drinkList.value = it
                    isLoading.set(false)
                }
        }
    }
}