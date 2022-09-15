package com.kud.hanzan.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.usecase.GetKeywordPlaceUseCase
import com.kud.hanzan.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCase: GetKeywordPlaceUseCase
) : BaseViewModel() {
    fun getKeyWordPlace(keyword: String) : Flow<Place> = useCase(this@MapViewModel, keyword)
}