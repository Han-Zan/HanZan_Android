package com.kud.hanzan.ui.map


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.usecase.GetKeywordPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCase: GetKeywordPlaceUseCase
) : ViewModel() {
    private val _placeInfo = MutableStateFlow<PlaceUiState>(PlaceUiState.Success(emptyList()))
    val placeInfo : StateFlow<PlaceUiState>
        get() = _placeInfo

    fun getKeyWordPlace(keyword: String) {
        viewModelScope.launch {
            useCase(keyword)
                .flowOn(Dispatchers.IO)
                    // 오류 처리 로직
                .catch { _placeInfo.value = PlaceUiState.Error(it) }
                .collect{
                    _placeInfo.value = PlaceUiState.Success(it)
                }
        }
    }
}

sealed class PlaceUiState {
    data class Success(val placeList: List<Place>): PlaceUiState()
    data class Error(val exception: Throwable): PlaceUiState()
}