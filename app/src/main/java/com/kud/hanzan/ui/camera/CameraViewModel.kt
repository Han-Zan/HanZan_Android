package com.kud.hanzan.ui.camera

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
): ViewModel() {
    val itemNotNull = ObservableField<Boolean>(false)
    fun setNotNull(){
        itemNotNull.set(true)
    }

    fun setNull(){
        itemNotNull.set(false)
    }
}