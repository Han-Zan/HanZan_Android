package com.kud.hanzan.ui.map

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.amazonaws.mobileconnectors.s3.transferutility.*
import com.kud.hanzan.domain.model.map.Store
import com.kud.hanzan.domain.model.map.StoreCombData
import com.kud.hanzan.domain.usecase.store.GetStoreUseCase
import com.kud.hanzan.domain.usecase.store.PostStoreUseCase
import com.kud.hanzan.domain.usecase.store.PutStoreImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val transferUtility: TransferUtility,
    @ApplicationContext private val context: Context,
    private val postStoreUseCase: PostStoreUseCase,
    private val getStoreUseCase: GetStoreUseCase,
    private val putStoreImageUseCase: PutStoreImageUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    private var _storeId = MutableLiveData<Long>()
    val storeId: LiveData<Long>
        get() = _storeId

    private var _storeCombData = MutableLiveData<StoreCombData>()
    val storeComb : LiveData<StoreCombData>
        get() = _storeCombData

    var isLoading : ObservableField<Boolean> = ObservableField<Boolean>()

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
        get() = _errorMessage

    init {
        isLoading.set(true)
        state.get<Store>("store")?.let { s->
            viewModelScope.launch {
                postStoreUseCase(s.id, s.name)
                    .catch { it.cause?.let { error ->
                        _errorMessage.value = error.message
                        isLoading.set(false)
                        }
                    }
                    .collectLatest {
                        _storeId.value = it
                        getStoreData(s.id)
                    }
            }
        }
    }
    fun uploadImage(fileName: String, file: File){
        TransferNetworkLossHandler.getInstance(context)

        val uploadObserver: TransferObserver = transferUtility.upload(
            "hanjanbucket/store",
            fileName,
            file
        ) // (bucket api, file이름, file객체)
        uploadObserver.setTransferListener(object: TransferListener{
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                    _storeCombData.value?.kakaoId?.let {
                        isLoading.set(true)
                        putStoreImage("https://s3.ap-northeast-2.amazonaws.com/hanjanbucket/store/$fileName", it)
                    } ?: run {
                        _errorMessage.value = "네트워크 오류"
                    }
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val done = (bytesCurrent / bytesTotal * 100.0).toInt()
                Log.d("storeImage", "UPLOAD - - ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception?) {
                Log.e("storeImage", ex?.message.toString())
                Log.e("storeImage", "업로드 실패")
                _errorMessage.value = ex?.message
            }

        })
    }

    private fun getStoreData(storeId: String){
        viewModelScope.launch {
            getStoreUseCase(storeId)
                .catch {
                    it.cause?.let {
                        _errorMessage.value = "네트워크 오류"
                        isLoading.set(false)
                    }
                }
                .collectLatest {
                    _storeCombData.value = it
                    isLoading.set(false)
                }
        }
    }

    private fun putStoreImage(imgLink: String, kakaoId: String){
        viewModelScope.launch {
            putStoreImageUseCase(imgLink, kakaoId)
                .catch {
                    it.cause?.let {
                        _errorMessage.value = "네트워크 오류"
                        isLoading.set(false)
                    }
                }
                .collectLatest {  }
                .also { getStoreData(kakaoId) }
        }
    }
}