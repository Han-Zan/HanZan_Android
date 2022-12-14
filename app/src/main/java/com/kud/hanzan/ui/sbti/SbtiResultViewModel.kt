package com.kud.hanzan.ui.sbti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.model.UserResponseDto
import com.kud.hanzan.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SbtiResultViewModel @Inject constructor(
    private val repository : LoginRepository
) : ViewModel() {
    private var _resLiveData = MutableLiveData<UserResponseDto?>()
    val resLiveData: LiveData<UserResponseDto?>
        get() = _resLiveData

    fun login(userInfo: UserInfo) {
        viewModelScope.launch {
            val res = repository.postUserInfo(userInfo)
            _resLiveData.value = res
        }
    }
}