package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.map.StoreCombData
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun postStore(storeId: String, storeName: String) : Flow<Long>
    fun getStoreData(storeId: String) : Flow<StoreCombData>
}