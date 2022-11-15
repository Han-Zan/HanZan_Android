package com.kud.hanzan.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.model.UserResponseDto
import com.kud.hanzan.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private var _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User>
        get() = _userLiveData

    private var _resChangeNickNameLiveData = MutableLiveData<String>()
    val resChangeNickNameLiveData: LiveData<String>
        get() = _resChangeNickNameLiveData

    private var _resChangeProfileLiveData = MutableLiveData<String>()
    val resChangeProfileLiveData: LiveData<String>
        get() = _resChangeProfileLiveData

    private var _resDeleteLiveData = MutableLiveData<String>()
    val resDeleteLiveData: LiveData<String>
        get() = _resDeleteLiveData

    fun getUser(userId: Long) {
        viewModelScope.launch {
            val res = repository.getUser(userId)
            _userLiveData.value = res
        }
    }

    fun changeUserNickName(userId: Long, userName: String) {
        viewModelScope.launch {
            val res = repository.changeUserNickName(userId, userName)
            _resChangeNickNameLiveData.value = res
        }
    }

    fun changeUserProfile(userId: Long, userImg: String) {
        viewModelScope.launch {
            val res = repository.changeUserProfile(userId, userImg)
            _resChangeProfileLiveData.value = res
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            val res = repository.deleteUser(userId)
            _resDeleteLiveData.value = res
        }
    }
}