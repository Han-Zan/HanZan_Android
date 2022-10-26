package com.kud.hanzan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.HomeCombination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private var _rankCombData = MutableStateFlow<List<HomeCombination>>(emptyList())
    val rankCombData : StateFlow<List<HomeCombination>>
        get() = _rankCombData

    init {
        getData()
    }

    fun getData(){
        viewModelScope.launch {
            _rankCombData.value = listOf(
                HomeCombination("일반 소주", null, "닭발", null, 1, true),
                HomeCombination("카스", null, "치킨", null, 2, false),
                HomeCombination("국순당막걸리", null, "파전", null, 3, true),
                )
        }
    }
}