package com.kud.hanzan.data.source.store

import com.kud.hanzan.domain.model.map.StoreCombData
import kotlinx.coroutines.flow.Flow

interface StoreDataSource {
    fun postStore(storeId: String, storeName: String) : Flow<Long>
    fun getStore(storeId: String) : Flow<StoreCombData>
}