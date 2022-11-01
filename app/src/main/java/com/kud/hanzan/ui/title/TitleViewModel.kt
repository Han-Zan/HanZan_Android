package com.kud.hanzan.ui.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.UserResponseDto
import com.kud.hanzan.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val repository : LoginRepository
) : ViewModel() {
    private var _resLiveData = MutableLiveData<UserResponseDto?>()
    val resLiveData: LiveData<UserResponseDto?>
        get() = _resLiveData

    fun checkUserAccount(userId: Long){
        viewModelScope.launch {
            val res = repository.checkUserAccount(userId)
            _resLiveData.value = res
        }
    }
}