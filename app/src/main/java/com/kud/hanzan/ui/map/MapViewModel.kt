package com.kud.hanzan.ui.map


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.map.Place
import com.kud.hanzan.domain.model.map.Store
import com.kud.hanzan.domain.usecase.kakao.GetCategoryPlaceUseCase
import com.kud.hanzan.domain.usecase.kakao.GetKeywordPlaceUseCase
import com.kud.hanzan.domain.usecase.kakao.GetRoadAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val keywordUseCase: GetKeywordPlaceUseCase,
    private val roadAddressUseCase: GetRoadAddressUseCase,
    private val categoryPlaceUseCase: GetCategoryPlaceUseCase
) : ViewModel() {
    private var _placeSearchInfo = MutableLiveData<PlaceUiState>(PlaceUiState.Success(emptyList()))
    val placeSearchInfo : LiveData<PlaceUiState>
        get() = _placeSearchInfo

    private var _roadAddress = MutableStateFlow<String>("가게 이름을 입력해주세요.")
    val roadAddress : StateFlow<String>
        get() = _roadAddress

    private var _placeNearInfo = MutableStateFlow<List<Store>>(emptyList())
    val placeNearInfo : StateFlow<List<Store>>
        get() = _placeNearInfo

    private var _cacheRoadAddress : String = _roadAddress.value

    var centerX: Double? = null
    var centerY: Double? = null


    fun getKeyWordPlace(keyword: String) {
        viewModelScope.launch {
            keywordUseCase(keyword)
                // 오류 처리 로직
                .catch { _placeSearchInfo.value = PlaceUiState.Error(it) }
                .collectLatest{
                    if (it.isEmpty()){
                        // Todo : Empty 한 경우 로직 처리
//                        _placeSearchInfo.value = PlaceUiState.Empty("검색 결과가 없습니다.")
                    } else {
                        _placeSearchInfo.value = PlaceUiState.Success(it)
                    }
                }
        }
    }

    fun setRoadAddress(longitude: String, latitude: String){
        centerX = longitude.toDouble()
        centerY = latitude.toDouble()
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

    fun getCategoryPlace(longitude: String, latitude: String, currentX: Double, currentY: Double){
        viewModelScope.launch {
            val storeList = ArrayList<Store>()
            for (i in 1..3){
                categoryPlaceUseCase(longitude, latitude, i, currentX, currentY)
                    .catch {  }
                    .collectLatest {
                        storeList.addAll(it)
                    }
            }
            _placeNearInfo.value = storeList
        }
    }

    fun getCurrentPos() : List<Double>? {
        return if (centerX == null || centerY == null) null
        else listOf(centerX!!, centerY!!)
    }
}

sealed class PlaceUiState {
    data class Success(val placeList: List<Store>): PlaceUiState()
//    data class Empty(val str: String) : PlaceUiState()
    data class Error(val exception: Throwable): PlaceUiState()
}