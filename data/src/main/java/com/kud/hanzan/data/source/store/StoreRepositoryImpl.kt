package com.kud.hanzan.data.source.store

import com.kud.hanzan.domain.model.map.StoreCombData
import com.kud.hanzan.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeRemoteDataSource: StoreRemoteDataSource
) : StoreRepository {
    override fun postStore(
        storeId: String, storeName: String)
    : Flow<Long> = storeRemoteDataSource.postStore(storeId, storeName)

    override fun getStoreData(
        storeId: String
    ): Flow<StoreCombData> = storeRemoteDataSource.getStore(storeId)
}