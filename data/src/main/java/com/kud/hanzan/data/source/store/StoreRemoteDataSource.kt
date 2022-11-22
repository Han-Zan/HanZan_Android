package com.kud.hanzan.data.source.store

import com.kud.hanzan.data.entity.store.StoreKakaoData
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.map.StoreCombData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoreRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : StoreDataSource {
    override fun postStore(
        storeId: String, storeName: String
    ): Flow<Long> = flow {
        emit(hanzanService.postStore(StoreKakaoData(null, storeId, storeName)))
    }.flowOn(Dispatchers.IO)

    override fun getStore(
        storeId: String
    ): Flow<StoreCombData> = flow {
        emit(hanzanService.getStore(storeId))
    }.flowOn(Dispatchers.IO)

    override fun putStoreImage(
        imgLink: String, kakaoIdx: String
    ): Flow<String> = flow {
        emit(hanzanService.putStoreImage(imgLink, kakaoIdx))
    }.flowOn(Dispatchers.IO)
}