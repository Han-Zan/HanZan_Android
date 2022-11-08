package com.kud.hanzan.ui.map

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.s3.transferutility.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val transferUtility: TransferUtility,
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun uploadImage(fileName: String, file: File){
        TransferNetworkLossHandler.getInstance(context)

        val uploadObserver: TransferObserver = transferUtility.upload(
            "hanjanbucket",
            fileName,
            file
        ) // (bucket api, file이름, file객체)
        uploadObserver.setTransferListener(object: TransferListener{
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                    Log.e("storeImage", "업로드 완료")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val done = (bytesCurrent / bytesTotal * 100.0).toInt()
                Log.d("storeImage", "UPLOAD - - ID: $id, percent done = $done")
            }

            override fun onError(id: Int, ex: Exception?) {
                Log.e("storeImage", ex?.message.toString())
                Log.e("storeImage", "업로드 실패")
            }

        })

    }
}