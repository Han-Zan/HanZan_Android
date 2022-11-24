package com.kud.hanzan.ui.camera

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kud.hanzan.domain.model.CombinationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraCombViewModel @Inject constructor(
) : ViewModel(){
    private var _combData = MutableLiveData<List<CombinationInfo>>()
    val combData : LiveData<List<CombinationInfo>>
        get() = _combData

    var isEnabled : ObservableField<Boolean> = ObservableField<Boolean>()
    var combination: CombinationInfo? = null

    init {
        _combData.value = listOf(
            CombinationInfo(99, 1, null, "소주", 2, null, "닭발", 1, true, 1, 1.0f),
            CombinationInfo(95, 1, null, "맥주", 2, null, "치킨", 1, true, 1, 1.0f),
            CombinationInfo(94, 1, null, "국순당 막걸리", 2, null, "파전", 1, true, 1, 1.0f),
            CombinationInfo(92, 1, null, "화이트 와인", 2, null, "맛살", 1, true, 1, 1.0f),

            )
        isEnabled.set(false)
    }

    fun setEnabled(combination: CombinationInfo, position: Int){
        isEnabled.set(position != -1)
        this.combination = _combData.value?.get(position)
    }
}