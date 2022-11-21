package com.kud.hanzan.domain.usecase.store

import com.kud.hanzan.domain.repository.StoreRepository
import javax.inject.Inject

class PostStoreUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    operator fun invoke(storeId: String, storeName: String) = repository.postStore(storeId, storeName)
}