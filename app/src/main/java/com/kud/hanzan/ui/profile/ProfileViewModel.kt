package com.kud.hanzan.ui.profile

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.model.UserResponseDto
import com.kud.hanzan.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val transferUtility: TransferUtility,
    @ApplicationContext private val context: Context,
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

    fun uploadImage(userId: Long, file: File){
        TransferNetworkLossHandler.getInstance(context)

        val uploadObserver: TransferObserver = transferUtility.upload(
            "hanjanbucket/profile",
            userId.toString(),
            file
        ) // (bucket api, file이름, file객체)
        uploadObserver.setTransferListener(object: TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                    changeUserProfile(userId, "https://s3.ap-northeast-2.amazonaws.com/hanjanbucket/profile/$userId")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val done = (bytesCurrent / bytesTotal * 100.0).toInt()
                Log.d(TAG, "UPLOAD - - ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception?) {
                Log.e(TAG, ex?.message.toString())
                Log.e(TAG, "업로드 실패")
            }

        })
    }
}