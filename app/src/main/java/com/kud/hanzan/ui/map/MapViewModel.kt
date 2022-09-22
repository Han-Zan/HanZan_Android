package com.kud.hanzan.ui.map


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.R
import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.usecase.GetKeywordPlaceUseCase
import com.kud.hanzan.domain.usecase.GetRoadAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val keywordUseCase: GetKeywordPlaceUseCase,
    private val roadAddressUseCase: GetRoadAddressUseCase
) : ViewModel() {
    private var _placeInfo = MutableStateFlow<PlaceUiState>(PlaceUiState.Success(emptyList()))
    val placeInfo : StateFlow<PlaceUiState>
        get() = _placeInfo

    private var _roadAddress = MutableStateFlow<String>("현재 위치")
    val roadAddress : StateFlow<String>
        get() = _roadAddress

    private var _cacheRoadAddress : String = _roadAddress.value

    fun getKeyWordPlace(keyword: String) {
        viewModelScope.launch {
            keywordUseCase(keyword)
                // 오류 처리 로직
                .catch { _placeInfo.value = PlaceUiState.Error(it) }
                .collectLatest{
                    _placeInfo.value = PlaceUiState.Success(it)
                }
        }
    }

    fun setRoadAddress(longitude: String, latitude: String){
        viewModelScope.launch {
            roadAddressUseCase(longitude, latitude)
                .catch { _roadAddress.value = _cacheRoadAddress }
                .collectLatest{
                    it?.let { _roadAddress.value = it
                        _cacheRoadAddress = it
                    }
                }
        }
    }

    fun setCurrentPos() {
        _roadAddress.value = "현재 위치"
    }
}

sealed class PlaceUiState {
    data class Success(val placeList: List<Place>): PlaceUiState()
    data class Error(val exception: Throwable): PlaceUiState()
}