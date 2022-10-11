package com.kud.hanzan.ui.like

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Combination
import com.kud.hanzan.domain.usecase.preferred.GetPreferredCombUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val getCombUseCase : GetPreferredCombUseCase
) : ViewModel() {
    private var _combData = MutableStateFlow<List<Combination>>(emptyList())
    val combData : StateFlow<List<Combination>>
        get() = _combData

    init {
        getComb(1)
    }

    fun getComb(userId: Long){
        viewModelScope.launch {
            getCombUseCase(userId)
                .catch { _combData.value = emptyList() }
                .collect{
                    _combData.value = it
                }
        }
    }
}