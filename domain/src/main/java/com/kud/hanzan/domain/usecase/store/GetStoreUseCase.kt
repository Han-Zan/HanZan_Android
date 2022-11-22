package com.kud.hanzan.domain.usecase.store

import com.kud.hanzan.domain.repository.StoreRepository
import javax.inject.Inject

class GetStoreUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    operator fun invoke(storeId : String) = repository.getStoreData(storeId)
}