package com.kud.hanzan.ui.camera

import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.RecommendItem
import com.kud.hanzan.domain.usecase.camera.GetRecommendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraCombViewModel @Inject constructor(
    private val getRecommendUseCase: GetRecommendUseCase
) : ViewModel(){
    private var _combData = MutableStateFlow<List<RecommendItem>>(emptyList())
    val combData : StateFlow<List<RecommendItem>>
        get() = _combData

    var isEnabled : ObservableField<Boolean> = ObservableField<Boolean>()
    var combination: RecommendItem? = null

    private var userIdx : Long = HanZanApplication.spfManager.getUserIdx()

    var isLoading : ObservableField<Boolean> = ObservableField<Boolean>()

    init {
        isEnabled.set(false)
        isLoading.set(true)
    }

    fun setEnabled(combination: RecommendItem, position: Int){
        isEnabled.set(position != -1)
        this.combination = _combData.value?.get(position)
    }

    fun getRecommend(drinkList: List<String>, foodList: List<String>){
        viewModelScope.launch {
            getRecommendUseCase(drinkList, foodList, userIdx)
                .catch { Log.e("okhttp Error", it.message.toString()) }
                .collect {
                    _combData.value = it
                    isLoading.set(false)
                }
        }
    }
}