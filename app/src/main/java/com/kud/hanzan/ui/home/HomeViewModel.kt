package com.kud.hanzan.ui.home

import androidx.lifecycle.ViewModel
import com.kud.hanzan.domain.model.Combination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private var _rankCombData = MutableStateFlow<List<Combination>>(emptyList())
    val rankCombData : StateFlow<List<Combination>>
        get() = _rankCombData
    
}