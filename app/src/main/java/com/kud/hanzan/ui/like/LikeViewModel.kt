package com.kud.hanzan.ui.like

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LikeViewModel : ViewModel() {
    val isAllSelected = ObservableField<Boolean>()
    init {
        setAllSelected()
    }

    fun setAllSelected(){
        isAllSelected.set(true)
    }

    fun setAllUnSelected(){
        isAllSelected.set(false)
    }
}