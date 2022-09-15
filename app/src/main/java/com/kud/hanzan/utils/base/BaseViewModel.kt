package com.kud.hanzan.utils.base

import androidx.lifecycle.ViewModel
import com.kud.hanzan.domain.utils.error.ErrorType
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import com.kud.hanzan.domain.utils.error.ScreenState
import com.kud.hanzan.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel(), RemoteErrorEmitter{
    val mutableScreenState = SingleLiveEvent<ScreenState>()
    val mutableErrorMessage = SingleLiveEvent<String>()
    val mutableErrorType = SingleLiveEvent<ErrorType>()

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(msg)
    }

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(errorType)
    }
}